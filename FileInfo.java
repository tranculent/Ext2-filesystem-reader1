import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileInfo {
    private String fileName;
    private int inodeNum;
    private int nameLen;
    private short length;
    private int fileType;

    public FileInfo(int offest, Ext2File file) {
        inodeNum = ByteBuffer.wrap(file.read(offset, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        length   = ByteBuffer.wrap(file.read(offset + 4, 4)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        nameLen  = ByteBuffer.wrap(file.read(offset + 6, 1)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        fileType = ByteBuffer.wrap(file.read(offset + 7, 1)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        fileName = new String(ByteBuffer.wrap(file.read(offset + 8, (nameLen / 4) * 4)).order(ByteOrder.LITTLE_ENDIAN))   
    }
}