import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Stores information about each file - the inode number, the length, the name length, the file type and the file name.
 */
public class FileInfo {
    private int inodeNum;
    private short length;
    private short nameLen;
    private String fileName;
    private int fileType;

    public FileInfo(int offset, Ext2File file) {
        /* Fix This */
        // nameLen = ByteBuffer.wrap(file.read(offset + 6, 1)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        // fileName = new String(file.read(offset + 8, nameLen));
        /* ==== */
        inodeNum = ByteBuffer.wrap(file.read(offset, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
        length   = ByteBuffer.wrap(file.read(offset + 4, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public void printFileInfo() {
        System.out.println("Inode Number: " + inodeNum);
		System.out.println("length: " + length);
    }
}