import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GroupDescriptor {
    private ByteBuffer byteBuffer;
    /**
     * Array of inode table pointers
     */
    private int[] inodeTablePointers;
    /**
     * Stores the inode table size
     */
    private int tableSize;
    /**
     * Stores the Inode Table Pointer
     */
    private int inodeTablePointerOffset;

    /**
     * for clarification, revisit http://www.nongnu.org/ext2-doc/ext2.html#block-group-descriptor-table
     */
    public GroupDescriptor(byte[] bytes, int groupCount) {
        tableSize = 32;
        inodeTablePointerOffset = 8;
        inodeTablePointers = new int[groupCount];
        byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        for (int i = 0; i < groupCount; i++) 
            inodeTablePointers[i] = byteBuffer.getInt((tableSize * i) + inodeTablePointerOffset);
    }

    public int[] getInodeTablePointers() {
        return inodeTablePointers;
    }
}
