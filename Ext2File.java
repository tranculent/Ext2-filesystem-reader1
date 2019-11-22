import java.io.IOException;

public class Ext2File {

    private long position; // the current position in the file
    private Volume volume; // the volume

    public Ext2File(Volume volume) {
        this.volume = volume;
        position = 0;
    }

    /**
     * Reads the most length bytes starting at byte offset startByte from start of
     * file. Byte 0 is the first byte in the file. StartByte must be such that, 0 <=
     * startByte < file.size or an exception should be raised. If there are fewer
     * than length bytes remaining these will be read and a smaller number of bytes
     * than requested will be returned.
     */
    public byte[] read(long startByte, long length) {
        byte[] bytes = new byte[(int) length];

        try {
        	volume.getRandomAccessFile().seek(startByte);
        	volume.getRandomAccessFile().read(bytes, 0, (int) length);
        	
        	return bytes;
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads at most length bytes starting at current position in the file. If the
     * current position is set beyond the end of the file, an exception should be
     * raised. If there are fewer than length bytes remaining these will be read and
     * a smaller number of bytes than requested will be returned.
     */
    public byte[] read(long length) {
        byte[] bytes = new byte[(int) length];

        try {
    		volume.getRandomAccessFile().seek(position());
            volume.getRandomAccessFile().read(bytes, 0, (int) length);
            
    		return bytes;
        }
        catch(Exception e) {
        	e.printStackTrace();
        }

        return null;
    }

    /**
     * Move to byte position in file. Setting position to 0: will move to the start
     * of the file. Note, it is legal to seek beyond the end of the file; If writing
     * were supported this is how holes are created.
     */
    public void seek(long position) {
        this.position = position;
    }

    /**
     * Returns current position in file, i.e. the byte offset from the start of the
     * file. The file position will be zero when the file is first opened and will
     * advance by the number of bytes read with every call to one of the read()
     * routines.
     * 
     * @return long the current position in the file
     */
    public long position() {
        return position;
    }

    /**
     * Returns the size of file as specified in filesystem
     * 
     * @throws IOException
     */
    public long size() throws IOException {
        return volume.getRandomAccessFile().length();
    }

    public Volume getVolume() {
        return volume;
    }
}