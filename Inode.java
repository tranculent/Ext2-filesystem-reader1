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
    private Date lastAccessTime;
    /** 
     * Indicates the time when the file was created
     */
    private Date creationTime;
    /**
     * Indicates the time when the file was last modified
     */
    private Date lastModifiedTime;
    /** 
     * Indicates when the when the file was deleted
     */
    private Date deletedTime;
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
    private int dataBlockPointers[] = new int[4];
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

    public Inode(int startingPoint) {
        this.startingPoint = startingPoint;
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

	public Date getLastAccessTime() {
		return this.lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastModifiedTime() {
		return this.lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getDeletedTime() {
		return this.deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
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