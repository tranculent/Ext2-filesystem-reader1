import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class GroupDescriptor {
    private ByteBuffer byteBuffer;
    private int inodeTablePointers[];
    private int inodeTablePointersLocations[];

    public GroupDescriptor(Ext2File file, int blocksCount) throws IOException {
        byteBuffer = ByteBuffer.wrap(file.read(2048, 1024)).order(ByteOrder.LITTLE_ENDIAN);
        inodeTablePointers = new int[blocksCount];
        inodeTablePointersLocations = new int[blocksCount];

        for (int i = 0; i < blocksCount; i++) {
            inodeTablePointers[i] = byteBuffer.getInt((i * Constants.GROUP_DESCRIPTOR_LENGTH) + Constants.INODE_TABLE_POINTER_OFFSET);
            inodeTablePointersLocations[i] = 2048 + (i * Constants.GROUP_DESCRIPTOR_LENGTH) + Constants.INODE_TABLE_POINTER_OFFSET;
        }
    }

    public int[] getInodeTablePointers() {
        return inodeTablePointers;
    }

    public int[] getInodeTablePointersLocations() {
        return inodeTablePointersLocations;
    }
}
