import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

	/**
	 * HashMap that will store all the months in <Integer, String> format
	 */
	private HashMap<Integer, String> months = new HashMap<>();
	
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
		// fileSize = byteBuffer.getInt(startingPoint + 108 + 4);

		months.put(0, "Jan");
		months.put(1, "Fev");
		months.put(2, "Mar");
		months.put(3, "Apr");
		months.put(4, "May");
		months.put(5, "Jun");
		months.put(6, "Jul");
		months.put(7, "Aug");
		months.put(8, "Sep");
		months.put(9, "Oct");
		months.put(10, "Nov");
		months.put(11, "Dec");

		/* INFINITE LOOP HERE */

		dataBlockPointers = new int[12];
		for (int i = 0; i < 12; i++) {
			System.out.println("Here??");
			byteBuffer = ByteBuffer.wrap(file.read(startingPoint+40+(i*4), 4)).order(ByteOrder.LITTLE_ENDIAN);
			dataBlockPointers[i] = byteBuffer.getInt();
		}
	}

	@Deprecated
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		/*
		   	drwxr-xr-x  4 root root   1024 Aug 13 20:20 .
			drwxr-xr-x 25 root root   4096 Aug 11 11:15 ..
			drwxr-xr-x  3 acs  staff  1024 Aug 13 20:20 home
			drwx------  2 root root  12288 Aug 11 11:06 lost+found
			-rw-r--r--  1 acs  staff     0 Aug 11 22:17 test
		*/

		/* File Type */
		// d
		if (fileMode == 0x8000)
			System.out.print("-"); // file
		else if (fileMode == 0x4000)
			System.out.print("d"); // directory

		/* User Permissions */ 
		// drwx
		if (fileMode == 0x0100)
			System.out.print("r"); // read
		else 
			System.out.print("-"); // non-read

		if (fileMode == 0x0080)
			System.out.print("w"); // write
		else 
			System.out.print("-"); // non-write

		if (fileMode == 0x0040)
			System.out.print("x"); // execute permission
		else 
			System.out.print("-"); // non-execution permission

		/* Group Permissions */
		// drwxr-x
		if(fileMode == 0x0020)
			System.out.print("r");
		else
			System.out.print("-");
		
		if(fileMode == 0x0010)
			System.out.print("w");
		else
			System.out.print("-");
		
		if(fileMode == 0x0008)
			System.out.print("x");
		else
			System.out.print("-");

		/* Other Permissions */
		// drwxr-xr-x |STOP HERE|
		if(fileMode == 0x0004)
			System.out.print("r");
		else
			System.out.print("-");
		
		if(fileMode == 0x0002)
			System.out.print("w");
		else
			System.out.print("-");
		
		if(fileMode == 0x0001)
			System.out.print("x ");
		else
			System.out.print("- ");

		/* Hard Links */
		System.out.print(numHardLinks + " ");

		/* User ID */
		System.out.print(userId + " ");

		/* Group ID */
		System.out.print(groupId + "    ");

		/* File Size */
		System.out.print(fileSize + " ");

		/* Dates */
		Date lastModifiedInDateFormat = new Date((long)lastModifiedTime * 1000L);
		
		System.out.print(months.get(lastModifiedInDateFormat.getMonth()) + " ");
		System.out.print((lastModifiedInDateFormat.getDay() + " "));
		System.out.print(lastModifiedInDateFormat.getHours() + 1 > 9 ? lastModifiedInDateFormat.getHours() + ":" : "0" + lastModifiedInDateFormat.getHours() + ":"); // 09:
		System.out.print(lastModifiedInDateFormat.getMinutes() + 1 > 9 ?lastModifiedInDateFormat.getMinutes() + " " : "0" + lastModifiedInDateFormat.getMinutes() + " "); // 09:01

		/* Directory */

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