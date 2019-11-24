import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

/**
 * Each object in the filesystem is represented by an inode.
 */
public class Inode {
    /**
     * Indicate the start point by which all other fields can be retrieved
     */
    private int startingPoint;

    /**
     * Indicates the permission of the file?
     */
    private int fileMode; // fileMode == permission
    /**
     * Indicates the user id of the user accessing this file?
     */
    private int userId;
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
     * Indicates the group id of the user accessing this file?
     */
    private int groupId;
    /**
     * Hard links allow you to have multiple "file names" that point to the same inode.
     * numHardLinks specifies the number of hard links of the current file
     */
    private int numHardLinks;
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
		byteBuffer = ByteBuffer.wrap(file.read(startingPoint + 112)).order(ByteOrder.LITTLE_ENDIAN);

		fileMode = byteBuffer.getInt(startingPoint + 0 + 2);
		userId = byteBuffer.getInt(startingPoint + 2 + 2);
		lastAccessTime = byteBuffer.getInt(startingPoint + 8 + 4);
		creationTime = byteBuffer.getInt(startingPoint + 12 + 4);
		lastModifiedTime = byteBuffer.getInt(startingPoint + 16 + 4);
		deletedTime = byteBuffer.getInt(startingPoint + 20 + 4);
		groupId = byteBuffer.getInt(startingPoint + 24 + 2);
		numHardLinks = byteBuffer.getInt(startingPoint + 26 + 2);
		indirectPointer = byteBuffer.getInt(startingPoint + 88 + 4);
		doubleIndirectPointer = byteBuffer.getInt(startingPoint + 92 + 4);
		tripleIndirectPointer = byteBuffer.getInt(startingPoint + 96 + 4);
		fileSize = byteBuffer.getInt(startingPoint + 108 + 4);
		
		dataBlockPointers = new int[12];
		for (int i = 0; i < 12; i++) {
			byteBuffer = ByteBuffer.wrap(file.read(startingPoint+40+(i*4), 4));
			dataBlockPointers[i] = byteBuffer.getInt();
		}
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("==================Inode Contents==================\n");
		stringBuilder.append("\nFile Mode: " + fileMode);
		stringBuilder.append("\nUser Id: " + userId);
		stringBuilder.append("\nLast Access Time: " + lastAccessTime);
		stringBuilder.append("\nCreation Time: " + creationTime);
		stringBuilder.append("\nLast Modified Time: " + lastModifiedTime);
		stringBuilder.append("\nDeleted Time: " + deletedTime);
		stringBuilder.append("\nGroup Id: " + groupId);
		stringBuilder.append("\nNumber of Hard Links: " + numHardLinks);
		stringBuilder.append("\nData Block Pointers: ");
		for (int i = 0; i < dataBlockPointers.length; i++) 
			stringBuilder.append(dataBlockPointers[i] + "\n");
		stringBuilder.append("\nIndirect Pointer: " + indirectPointer);
		stringBuilder.append("\nDouble Indirect Pointer: " + doubleIndirectPointer);
		stringBuilder.append("\nTriple Indirect Pointer: " + tripleIndirectPointer);
		stringBuilder.append("\nFile Size: " + fileSize);

		return stringBuilder.toString();
	}

	public int getStartingPoint() {
        return this.startingPoint;
    }

    public void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }

	public int getFileMode() {
		return this.fileMode;
	}

	public void setFileMode(int fileMode) {
		this.fileMode = fileMode;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public int getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(int lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(int creationTime) {
		this.creationTime = creationTime;
	}

	public int getLastModifiedTime() {
		return this.lastModifiedTime;
	}

	public void setLastModifiedTime(int lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public int getDeletedTime() {
		return this.deletedTime;
	}

	public void setDeletedTime(int deletedTime) {
		this.deletedTime = deletedTime;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getNumHardLinks() {
		return this.numHardLinks;
	}

	public void setNumHardLinks(int numHardLinks) {
		this.numHardLinks = numHardLinks;
	}

	public int[] getDataBlockPointers() {
		return this.dataBlockPointers;
	}

	public void setDataBlockPointers(int dataBlockPointers[]) {
		this.dataBlockPointers = dataBlockPointers;
	}

	public int getIndirectPointer() {
		return this.indirectPointer;
	}

	public void setIndirectPointer(int indirectPointer) {
		this.indirectPointer = indirectPointer;
	}

	public int getDoubleIndirectPointer() {
		return this.doubleIndirectPointer;
	}

	public void setDoubleIndirectPointer(int doubleIndirectPointer) {
		this.doubleIndirectPointer = doubleIndirectPointer;
	}

	public int getTripleIndirectPointer() {
		return this.tripleIndirectPointer;
	}

	public void setTripleIndirectPointer(int tripleIndirectPointer) {
		this.tripleIndirectPointer = tripleIndirectPointer;
	}
}