
public class driver {
    public static void main(String[] args) throws Exception {
        Volume vol = new Volume("ext2fs");
        Ext2File f = new Ext2File(vol);
        SuperBlock sp = new SuperBlock(1024, f);
        
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
        new Helper().dumpHexBytes(f.read(2048));
        f.seek(0);

        InodeTable inodeTable = new InodeTable(f, sp);
        GroupDescriptor groupDescriptors[] = new GroupDescriptor[inodeTable.getBlocksCount()];
        groupDescriptors[0].getInodeTablePointer();
        System.out.println(inodeTable.getInodes()[1]);
        System.out.println(inodeTable.getInodes()[2]);
        System.out.println(inodeTable.getRootInode());
    }
}