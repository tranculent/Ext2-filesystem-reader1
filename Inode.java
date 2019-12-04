import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

// TODO:
// add dates so it can be traced when the file was created, deleted and modified ;

/**
 * Each object in the filesystem is represented by an inode.
 */
public class Inode {
    /**
     * Indicate the start point by which all other fields can be retrieved
     */
    private int startingPoint;

    /**
     * Indicates the permission
     */
    private int fileMode; // fileMode == permission
    /**
     * Indicates the user id of the user accessing this file
     */
    private short userId;
    /**
     * Indicates the file size
     */
    private int fileSize;
    /**
     * Indicates the time when the file was last accessed
     */
    private int lastAccessTime;
    /** 
     * Indicates the time when the file was created
     */
    private int creationTime;
    /**
     * Indicates the time when the file was last modified
     */
    private int lastModifiedTime;
    /** 
     * Indicates when the when the file was deleted
     */
    private int deletedTime;
    /**
     * Indicates the group id of the user accessing this file
     */
    private short groupId;
    /**
     * Hard links allow you to have multiple "file names" that point to the same inode.
     * numHardLinks specifies the number of hard links of the current file
     */
    private short numHardLinks;
    /**
     * Contains file's data
     */
    private int dataBlockPointers[];
    /**
     * Pointer to an indirect block which contains pointers to the next set of blocks
     */
    private int indirectPointer;
    /**
     * Poitner to a doubly-linked block which contains pointers to indirect blocks
     */
    private int doubleIndirectPointer;
    /**
     * Pointer to a trebly-indirect block which contains pointers to doubly-indirect blocks
     */
	private int tripleIndirectPointer;
	
	private Ext2File file;
	private ByteBuffer byteBuffer;

    public Inode(int startingPoint, Ext2File file) {
		this.startingPoint = startingPoint;
		this.file = file;
		process();
    }
	
    private void process() {
		byteBuffer 				= ByteBuffer.wrap(file.read(startingPoint, Constants.INODE_TABLE_SIZE)).order(ByteOrder.LITTLE_ENDIAN);
		fileMode 				= byteBuffer.getInt(Constants.FILE_MODE_OFFSET);
		userId 					= byteBuffer.getShort(Constants.USER_ID_OFFSET);
		fileSize				= byteBuffer.getInt(Constants.FILE_SIZE_OFFSET);
		lastAccessTime 			= byteBuffer.getInt(Constants.LAST_ACCESS_TIME_OFFSET);
		creationTime 			= byteBuffer.getInt(Constants.CREATION_TIME_OFFSET);
		lastModifiedTime 		= byteBuffer.getInt(Constants.LAST_MODIFIED_TIME_OFFSET);
		deletedTime 			= byteBuffer.getInt(Constants.DELETED_TIME_OFFSET);
		groupId 				= byteBuffer.getShort(Constants.GROUP_ID_OFFSET);
		numHardLinks 			= byteBuffer.getShort(Constants.NUM_HARD_LINKS_OFFSET);
		indirectPointer 		= byteBuffer.getInt(Constants.INDIRECT_POINTER_OFFSET);
		doubleIndirectPointer 	= byteBuffer.getInt(Constants.DOUBLE_INDIRECT_POINTER_OFFSET);
		tripleIndirectPointer 	= byteBuffer.getInt(Constants.TRIPLE_INDIRECT_POITNER_OFFSET);
		dataBlockPointers 		= new int[12];

		// Populate data block pointers
		for (int i = 0; i < 12; i++) {
			byteBuffer = ByteBuffer.wrap(file.read(startingPoint + Constants.DATA_BLOCKS_POINTER + (i * 4), 4)).order(ByteOrder.LITTLE_ENDIAN);
			dataBlockPointers[i] = byteBuffer.getInt();
		}
	}

	@Deprecated
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		/* File Type */
		// d
		if ((fileMode & 0x8000) == 0x8000)
			stringBuilder.append("-"); // file
		else if ((fileMode & 0x4000) == 0x4000)
			stringBuilder.append("d"); // directory

		/* User Permissions */ 
		// drwx
		if ((fileMode & 0x0100) == 0x0100)
			stringBuilder.append("r"); // read
		else 
			stringBuilder.append("-"); // non-read

		if ((fileMode & 0x0080) == 0x0080)
			stringBuilder.append("w"); // write
		else 
			stringBuilder.append("-"); // non-write

		if ((fileMode & 0x0040) == 0x0040)
			stringBuilder.append("x"); // execute permission
		else 
			stringBuilder.append("-"); // non-execution permission

		/* Group Permissions */
		// drwxr-x
		if((fileMode & 0x0020) == 0x0020)
			stringBuilder.append("r");
		else
			stringBuilder.append("-");
		
		if((fileMode & 0x0010) == 0x0010)
			stringBuilder.append("w");
		else
			stringBuilder.append("-");
		
		if((fileMode & 0x0008) == 0x0008)
			stringBuilder.append("x");
		else
			stringBuilder.append("-");

		/* Other Permissions */
		// drwxr-xr-x |STOP HERE|
		if((fileMode & 0x0004) == 0x0004)
			stringBuilder.append("r");
		else
			stringBuilder.append("-");
		
		if((fileMode & 0x0002) == 0x0002)
			stringBuilder.append("w");
		else
			stringBuilder.append("-");
		
		if((fileMode & 0x0001) == 0x0001)
			stringBuilder.append("x ");
		else
			stringBuilder.append("- ");

		/* Hard Links */
		stringBuilder.append(numHardLinks + " ");

		/* User ID */
		stringBuilder.append(userId + " ");

		/* Group ID */
		stringBuilder.append(groupId + " ");

		/* File Size */
		stringBuilder.append(fileSize + " ");

		/* Dates */
		Date lastModifiedInDateFormat = new Date((long) lastModifiedTime * 1000);

		stringBuilder.append(lastModifiedInDateFormat.toString());
		
		return stringBuilder.toString();
	}
	
	public int getStartingPoint() {
        return this.startingPoint;
    }

	public int getFileMode() {
		return this.fileMode;
	}

	public int getUserId() {
		return this.userId;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public int getLastAccessTime() {
		return this.lastAccessTime;
	}

	public int getCreationTime() {
		return this.creationTime;
	}

	public int getLastModifiedTime() {
		return this.lastModifiedTime;
	}

	public int getDeletedTime() {
		return this.deletedTime;
	}

	public int getGroupId() {
		return this.groupId;
	}
	
	public int getNumHardLinks() {
		return this.numHardLinks;
	}

	public int[] getDataBlockPointers() {
		return this.dataBlockPointers;
	}

	public int getIndirectPointer() {
		return this.indirectPointer;
	}

	public int getDoubleIndirectPointer() {
		return this.doubleIndirectPointer;
	}

	public int getTripleIndirectPointer() {
		return this.tripleIndirectPointer;
	}
}