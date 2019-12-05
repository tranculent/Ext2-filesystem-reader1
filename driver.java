import java.util.Scanner;

/**
 * TODO:
 * 1. Implement Scanner
 * 2. 
 */

public class driver {
    public static void main(String[] args) throws Exception {
        Volume vol = new Volume("ext2fs");
        Ext2File f = new Ext2File(vol);
        SuperBlock sp = new SuperBlock(f);
        
        GroupDescriptor grpDescriptor = new GroupDescriptor(f, sp.getInodesCount() / sp.getInodesPerGroup());
        InodeTable inodeTable = new InodeTable(f, sp, grpDescriptor);

        Inode[] inodes = inodeTable.getInodes();

        Directory d = new Directory(inodes, f);
    }
}