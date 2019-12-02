import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileInfo {
    private String fileName;
    private int inodeNum;
    private int nameLen;
    private short length;
    private int fileType;

    public FileInfo(int offest, Ext2File file) {
        
    }
}