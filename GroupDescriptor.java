import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GroupDescriptor {
    private ByteBuffer byteBuffer;
    private int inodeTablePointers[];

    /**
     * for clarification, revisit
     * http://www.nongnu.org/ext2-doc/ext2.html#block-group-descriptor-table
     * 
     * @throws IOException
     */
    public GroupDescriptor(Ext2File file, int blocksCount) throws IOException {
        byteBuffer = ByteBuffer.wrap(file.read(2048, 1024)).order(ByteOrder.LITTLE_ENDIAN);
        inodeTablePointers = new int[blocksCount];

        for (int i = 0; i < blocksCount; i++)
            inodeTablePointers[i] = byteBuffer.getInt((i * 32) + 8);
    }

    public int[] getInodeTablePointers() {
        return inodeTablePointers;
    }
}
