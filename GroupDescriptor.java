import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GroupDescriptor {
    private ByteBuffer byteBuffer;
    private int inodeTablePointer;

    /**
     * for clarification, revisit http://www.nongnu.org/ext2-doc/ext2.html#block-group-descriptor-table
     * bytes => ((2048 + 12), 4) => 2048 (first block) + 12 (3rd block in the first block), (4 => the amount of bytes to be read)
     */
    public GroupDescriptor(int start, byte[] bytes) {
        byteBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        inodeTablePointer = byteBuffer.getInt(start);
        System.out.println(inodeTablePointer);
    }

    public int getInodeTablePointer() {
        return inodeTablePointer;
    }
}
