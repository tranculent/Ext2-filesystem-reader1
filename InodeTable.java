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
    private GroupDescriptor groupDescriptors[];
    private int offset;

    public InodeTable(Ext2File file, SuperBlock superBlock) {
        obtainInfo(file, superBlock);
        populateInodes();
    }

    private void obtainInfo(Ext2File file, SuperBlock superBlock) {
        this.file           = file;
        inodes              = new Inode[superBlock.getInodesCount()];
        inodeSize           = superBlock.getInodeSize();
        inodesPerGroup      = superBlock.getInodesPerGroup();
        blocksCount         = superBlock.getInodesCount() / superBlock.getInodesPerGroup();
        groupDescriptors    = new GroupDescriptor[blocksCount];
    }

    private void populateInodes() {
        // (1024 + (84 * 1024) + 256) => root (inode2)
        
        for (int i = 0; i < blocksCount; i++) {
            byteBuffer = file.read(2048 * (i > 0 ? i : 1) + 8 + 4); // gets the inodetablepointer for every block
            groupDescriptors[i] = new GroupDescriptor(byteBuffer.length-4, byteBuffer);
            offset = ByteBuffer.wrap(byteBuffer).order(ByteOrder.LITTLE_ENDIAN).getInt() * 1024;

            inodes[inodesPerGroup * i] = new Inode(offset, file);
            offset += inodeSize;
        }
    } 

    public Inode getRootInode() {
        return rootInode;
    }

    public Inode[] getInodes() {
        return inodes;
    }

    public GroupDescriptor[] getGroupDescriptors() {
        return groupDescriptors;
    }

    public int getBlocksCount() {
        return blocksCount;
    }
}
