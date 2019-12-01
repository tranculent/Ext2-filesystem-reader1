import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.IOException;

public class SuperBlock {
	private Ext2File file;
	private String volumeName;
	private int magicNumber;
	private int inodesCount;
	private int inodeSize;
	private int blocksCount;
	private int blocksPerGroup;
	private int inodesPerGroup;
	private ByteBuffer byteBuffer;

	public SuperBlock(Ext2File file) throws IOException {
		this.file = file;
		process();
	}

	private void process() throws IOException {
		// read the bytes from the file and order it as LITTLE_ENDIAN
		byteBuffer 	   = ByteBuffer.wrap(file.read(Constants.SUPERBLOCK_OFFSET, 136)).order(ByteOrder.LITTLE_ENDIAN);
		// System.out.println(file.size());
		magicNumber    = byteBuffer.getInt(Constants.MAGIC_NUMBER_OFFSET); // magic number offset
		inodesCount    = byteBuffer.getInt(Constants.INODES_COUNT_OFFSET);  	// inodes count offset
		inodeSize      = byteBuffer.getInt(Constants.INODES_SIZE_OFFSET); 	// inodes size      offset
		blocksCount    = byteBuffer.getInt(Constants.BLOCKS_COUNT_OFFSET);  	// blocks count     offset
		blocksPerGroup = byteBuffer.getInt(Constants.BLOCKS_PER_GROUP_OFFSET); 	// blocks per group offset
		inodesPerGroup = byteBuffer.getInt(Constants.INODES_PER_GROUP_OFFSET);	 	// inodes per group offset

		byte[] volumeBytes = new byte[16];
		int index = 0;

		for (int i = Constants.VOLUME_NAME_OFFSET; i < 136; i++) 
			volumeBytes[index++] = byteBuffer.get(i);
		
		volumeName = new String(volumeBytes);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("==================Superblock Contents==================\n");
		stringBuilder.append("Magic Number: " + magicNumber);
		stringBuilder.append("\nInodes Count: " + inodesCount);
		stringBuilder.append("\nInode Size: " + inodeSize);
		stringBuilder.append("\nBlocks Count: " + blocksCount);
		stringBuilder.append("\nBlocks Per Group: " + blocksPerGroup);
		stringBuilder.append("\nInodes Per Group: " + inodesPerGroup);

		return stringBuilder.toString();
	}

	public String getVolumeName() {
		return this.volumeName;
	}

	public int getMagicNumber() {
		return this.magicNumber;
	}

	public int getInodesCount() {
		return this.inodesCount;
	}

	public int getInodeSize() {
		return this.inodeSize;
	}

	public int getBlocksCount() {
		return this.blocksCount;
	}

	public int getBlocksPerGroup() {
		return this.blocksPerGroup;
	}

	public int getInodesPerGroup() {
		return this.inodesPerGroup;
	}
}
