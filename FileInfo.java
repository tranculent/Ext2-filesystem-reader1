import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Stores information about each file - the inode number, the length, the name length, the file type and the file name.
 */
public class FileInfo {
    private int inodeNum;
    private short length;
    private byte nameLen;
    private String fileName;
    private int fileType;

    public FileInfo(int offset, Ext2File file) {
        length   = ByteBuffer.wrap(file.read(offset + 4, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
        nameLen = file.read(offset + 6, 1)[0];
        fileName = new String(file.read(offset + 8, nameLen));
        inodeNum = ByteBuffer.wrap(file.read(offset, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public void printFileInfo() {
        System.out.println("Inode Number: " + inodeNum);
        System.out.println("length: " + length);
        System.out.println("File Name: " + fileName);
        System.out.println("Name Length: " + nameLen);
    }

    public short getLength() {
        return length;
    }

    public int getInodeNum() {
        return inodeNum;
    }

    public String getName() {
        return fileName;
    }

    public int getNameLen() {
        return nameLen;
    }
}