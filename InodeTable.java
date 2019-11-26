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

    /**
     * 
     * @param file The file to be examined
     * @param superBlock The Superblock
     */
    public InodeTable(Ext2File file, SuperBlock superBlock) {
        obtainInfo(file, superBlock);
        populateFullGroupInodes();
        populateRemaindingInodes();
    }
    
    /**
     * Obtaines general info from the Superblock and the Ext2File 
     * @param file The file
     * @param superBlock The superblock
     */
    private void obtainInfo(Ext2File file, SuperBlock superBlock) {
        this.file       = file;
        inodes          = new Inode[superBlock.getInodesCount()];
        wholeBlocks     = superBlock.getInodesCount() / superBlock.getInodesPerGroup();
        remainding      = superBlock.getInodesCount() % superBlock.getInodesPerGroup();
        inodesPerGroup  = superBlock.getInodesPerGroup();
        inodeSize       = superBlock.getInodeSize();
        
    }

    /**
     * Populates the inodes in the whole blocks (without remainding ones)
     */
    private void populateFullGroupInodes() {        
        for (int i = 0; i < wholeBlocks; i++) {
            byteBuffer = file.read(2048 + (32 * i) + 8, 4); // read the inode information
            inodeTablePointer = ByteBuffer.wrap(byteBuffer).order(ByteOrder.LITTLE_ENDIAN);
            offset = inodeTablePointer.getInt() * 1024; // move to the next block

            // populating the array
            for (int j = inodesPerGroup * i; j < inodesPerGroup * (i + 1); j++) {
                inodes[j] = new Inode(offset, file);
                offset += inodeSize;
            }
        }
    }

    private void populateRemaindingInodes() {
    
    }
}