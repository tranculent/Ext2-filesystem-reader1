import java.lang.System;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Traverses through FileInfo entries
 */
public class Directory {
    private Ext2File file;
    private Inode[] inodes;
   
    private int offset;
    private int fileInfoIndex;

    private FileInfo[] fileInfoArray;

    public Directory(Inode[] inodes, Ext2File file) {
        
        this.file   = file;
        this.inodes = inodes;
        fileInfoIndex = 0;
        int sizeOfFileInfo = 0;

        for (int i = 0; i < inodes.length; i++) 
            for (int j = 0; j < 12; j++) 
                if (inodes[i].getDataBlockPointers()[j] != 0) 
                    sizeOfFileInfo++;

        fileInfoArray = new FileInfo[sizeOfFileInfo];

        for (int i = 0; i < inodes.length; i++) {
            for (int j = 0; j < 12; j++) {
                if (inodes[i].getDataBlockPointers()[j] != 0) {
                    offset = inodes[i].getDataBlockPointers()[j] * 1024;
                    fileInfoArray[fileInfoIndex++] = new FileInfo(offset, file);   
                }
            }
        }
    }

    /**
     * Returns contents of a directory in a form suited to being output in Unix like format
     * @return the unix-like format
     
        drwxr-xr-x  4 root root   1024 Aug 13 20:20 .
        drwxr-xr-x 25 root root   4096 Aug 11 11:15 ..
        drwxr-xr-x  3 acs  staff  1024 Aug 13 20:20 home
        drwx------  2 root root  12288 Aug 11 11:06 lost+found
        -rw-r--r--  1 acs  staff     0 Aug 11 22:17 test
    */

    public FileInfo[] getFileInfo() {
        return fileInfoArray;
    }
}