import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class InodeTable {
    private Ext2File file;
    public Inode rootInode;
    private Inode inodes[];
    private int blocksCount;
    private int inodesPerGroup;
    private int inodeSize;
    private byte byteBuffer[];
    private int offset;
    private GroupDescriptor groupDescriptor;
    private SuperBlock superBlock;

    public InodeTable(Ext2File file, SuperBlock superBlock, GroupDescriptor groupDescriptor) {
        obtainInfo(file, superBlock, groupDescriptor);
        populateInodes();
    }

    private void obtainInfo(Ext2File file, SuperBlock superBlock, GroupDescriptor groupDescriptor) {
        this.file            = file;
        this.groupDescriptor = groupDescriptor;
        this.superBlock      = superBlock;
        inodes               = new Inode[superBlock.getInodesCount()];
        inodeSize            = superBlock.getInodeSize();
        inodesPerGroup       = superBlock.getInodesPerGroup();
        blocksCount          = superBlock.getInodesCount() / superBlock.getInodesPerGroup();
     }

    private void populateInodes() {
        ByteBuffer tempBuffer;

        for (int i = 0; i < blocksCount; i++) {
            tempBuffer  = ByteBuffer.wrap(file.read(groupDescriptor.getInodeTablePointersLocations()[i], 1024)).order(ByteOrder.LITTLE_ENDIAN);
            offset      = tempBuffer.getInt() * Constants.BLOCK_SIZE;

            // i(0), j -> 1712; i(1), j -> 1712 * 2 = 3424; i(2), j -> 1712 * 3 = 5136;
            for(int j = inodesPerGroup*i; j < inodesPerGroup*(i+1);  j++)
			{
                inodes[j] = new Inode(offset, file);
                offset += inodeSize;
			}
        }
    }

    public Inode[] getInodes() {
        return inodes;
    }

    public int getBlocksCount() {
        return blocksCount;
    }
}
