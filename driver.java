
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
        
        System.out.println();
        System.out.println("File Content: ");
        System.out.println("================================");
        System.out.println("Reading all the contents from the file..\n");
        // System.out.println(new String(f.read(f.size())));
        System.out.println("================================");
        System.out.println("Reading contents from file starting from 5th byte until the end..\n");
        // System.out.println(new String(f.read(5, f.size())));
        System.out.println("================================");
        // new Helper().dumpHexBytes(f.read(1024, 136)); // block size => 1024
        System.out.println(sp);
        System.out.println("================================");

        GroupDescriptor grpDescriptor = new GroupDescriptor(f, sp.getInodesCount() / sp.getInodesPerGroup());
        InodeTable inodeTable = new InodeTable(f, sp, grpDescriptor);
        int inodeTablePointers[] = grpDescriptor.getInodeTablePointers();
        
        for (int i = 0; i < inodeTablePointers.length; i++)
            System.out.println(inodeTablePointers[i]);

        // (1024 + (84 * 1024) + 256) => root (inode2)
        Inode root = new Inode(1024 + (inodeTablePointers[0] * 1024) + 256, f);
        System.out.println(root);
        // new Helper().dumpHexBytes(f.read(1024 * 310, 1024));

        Inode[] inodes = inodeTable.getInodes();

        System.out.println("Starting..");
        System.out.println(inodes[0]);
        for (int i = 0; i < inodes.length; i++) 
            if (inodes[i].getFileSize() == root.getFileSize()) System.out.println("I: " + i + " inode: " + inodes[i]);
    }
}