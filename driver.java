
/**
 * TODO:
 * 1. FIND INODES IN EACH GROUP
 * 2. 
 */

public class driver {
    public static void main(String[] args) throws Exception {
        Volume vol = new Volume("ext2fs");
        Ext2File f = new Ext2File(vol);
        SuperBlock sp = new SuperBlock(f);
        
        System.out.println(sp);
        System.out.println("================================");

        GroupDescriptor grpDescriptor = new GroupDescriptor(f, sp.getInodesCount() / sp.getInodesPerGroup());
        InodeTable inodeTable = new InodeTable(f, sp, grpDescriptor);
        int inodeTablePointers[] = grpDescriptor.getInodeTablePointers();
        
        for (int i = 0; i < inodeTablePointers.length; i++)
            System.out.println("Inode Table Pointer " + i + ": " + inodeTablePointers[i]);

        // new Helper().dumpHexBytes(f.read(1024 * 310, 1024));

        Inode[] inodes = inodeTable.getInodes();
        
        for (int i = 0; i < 12; i++)
            System.out.println(inodes[1].getDataBlockPointers()[i]);

        Directory d = new Directory(inodes, f);
    }
}