public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    /* SuperBlock Fields */
    public static final int INODES_COUNT_OFFSET             = 0;
    public static final int BLOCKS_COUNT_OFFSET             = 4;
    public static final int BLOCKS_PER_GROUP_OFFSET         = 32;
    public static final int INODES_PER_GROUP_OFFSET         = 40;
    public static final int MAGIC_NUMBER_OFFSET             = 56;
    public static final int INODES_SIZE_OFFSET              = 88;
    public static final int VOLUME_NAME_OFFSET              = 120;
    public static final int SUPERBLOCK_OFFSET               = 1024;

    /* Inode Fields */
    public static final int FILE_MODE_OFFSET                = 0;
    public static final int USER_ID_OFFSET                  = 2;
    public static final int FILE_SIZE_OFFSET                = 4;
    public static final int LAST_ACCESS_TIME_OFFSET         = 8;
    public static final int CREATION_TIME_OFFSET            = 12;
    public static final int LAST_MODIFIED_TIME_OFFSET       = 16;
    public static final int DELETED_TIME_OFFSET             = 20;
    public static final int GROUP_ID_OFFSET                 = 24;
    public static final int NUM_HARD_LINKS_OFFSET           = 26;
    public static final int DATA_BLOCKS_POINTER             = 40;
    public static final int INDIRECT_POINTER_OFFSET         = 88;
    public static final int DOUBLE_INDIRECT_POINTER_OFFSET  = 92;
    public static final int TRIPLE_INDIRECT_POITNER_OFFSET  = 96;
    public static final int INODE_TABLE_SIZE                = 128;

    /* Group Descriptor Fields */
    public static final int GROUP_DESCRIPTOR_LENGTH         = 32;
    public static final int INODE_TABLE_POINTER_OFFSET      = 8; 
}