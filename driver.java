import java.util.Arrays;

public class driver {
    public static void main(String[] args) throws Exception {
        Volume vol = new Volume("ext2fs");
        Ext2File f = new Ext2File(vol);
        
        System.out.println();
        System.out.println("File Content: ");
        System.out.println("================================");
        System.out.println("Reading all the contents from the file..\n");
        System.out.println(new String(f.read(f.size())));
        System.out.println("================================");
        System.out.println("Reading contents from file starting from 5th byte until the end..\n");
        System.out.println(new String(f.read(5, f.size())));
        System.out.println("================================");
        new Helper().dumpHexBytes(f.read(f.size()));
    }
}