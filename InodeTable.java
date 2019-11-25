import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class InodeTable {
    private Ext2File file;
    private Inode inodes[];
    private int wholeBlocks;
    private int remainding;
    private int inodesPerGroup;
    private int inodeSize;
    private byte byteBuffer[];
    private ByteBuffer inodeTablePointer;
    private int offset;

    public InodeTable(Ext2File file, SuperBlock superBlock) {
        obtainInfo(file, superBlock);
        populateFullGroupInodes();
        populateRemaindingInodes();
    }

	
    private void obtainInfo(Ext2File file, SuperBlock superBlock) {
        this.file = file;
        inodes          = new Inode[superBlock.getInodesCount()];
        wholeBlocks     = superBlock.getInodesCount() / superBlock.getInodesPerGroup();
        remainding      = superBlock.getInodesCount() % superBlock.getInodesPerGroup();
        inodesPerGroup  = superBlock.getInodesPerGroup();
        inodeSize       = superBlock.getInodeSize();
    }

    private void populateFullGroupInodes() {
        for (int i = 0; i < wholeBlocks; i++) {
            byteBuffer = file.read(2048 + (32 * i) + 8, 4); // diagram reference #1
            inodeTablePointer = ByteBuffer.wrap(byteBuffer).order(ByteOrder.LITTLE_ENDIAN);
            offset = inodeTablePointer.getInt() * 1024; // 

            // populating the array
            for (int j = inodesPerGroup * i; j < inodesPerGroup * (i + 1); j++) {
                inodes[j] = new Inode(offset, file);
            }
        }
    }

    private void populateRemaindingInodes() {
    
    }
}