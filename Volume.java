import java.io.*;

/**
 * To locate inodes and blocks, we need Volume that holds the filesystem, and it is the firs component to be read.
 * 
 */
public class Volume {
    private RandomAccessFile randomAccessFile;
    private String fileName;
    /**
     * Opens the Volume represented by the host Windows/Linux file "filename"
     * @param fileName the name of the file
     */
    public Volume(String fileName) {
        try {
            this.fileName = fileName;
            randomAccessFile = new RandomAccessFile(fileName, "r");
            System.out.println("File has been read successfully!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }

    public String getVolumeName() {
        return fileName;
    }
}
