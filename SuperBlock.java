import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.IOException;

public class SuperBlock {
	private Ext2File file;
	private int startingPoint;
	private String volumeName;
	private int magicNumber;
	private int inodesCount;
	private int inodeSize;
	private int blocksCount;
	private int blocksPerGroup;
	private int inodesPerGroup;
	private ByteBuffer byteBuffer;

	public SuperBlock(int start, Ext2File file) throws IOException {
		// bytes represents the data that is stored in the Superblock
		startingPoint = start;
		this.file = file;
		process();
	}

	private void process() throws IOException {
		// read the bytes from the file and order it as LITTLE_ENDIAN
		byteBuffer = ByteBuffer.wrap(file.read(startingPoint + 136)).order(ByteOrder.LITTLE_ENDIAN);
		// System.out.println(file.size());
		magicNumber    = byteBuffer.getInt(startingPoint + 56 + 2); // magic number     offset
		inodesCount    = byteBuffer.getInt(startingPoint + 0 + 4);  // inodes count     offset
		inodeSize      = byteBuffer.getInt(startingPoint + 88 + 4); // inodes size      offset
		blocksCount    = byteBuffer.getInt(startingPoint + 4 + 4);  // blocks count     offset
		blocksPerGroup = byteBuffer.getInt(startingPoint + 32 + 4); // blocks per group offset
		inodesPerGroup = byteBuffer.getInt(startingPoint + 40); // inodes per group offset
		
		byte[] volumeBytes = new byte[16];
		int index = 0;
		for (int i = 120; i < 136; i++) 
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

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public int getMagicNumber() {
		return this.magicNumber;
	}

	public void setMagicNumber(int magicNumber) {
		this.magicNumber = magicNumber;
	}

	public int getInodesCount() {
		return this.inodesCount;
	}

	public void setInodesCount(int inodesCount) {
		this.inodesCount = inodesCount;
	}

	public int getInodeSize() {
		return this.inodeSize;
	}

	public void setInodeSize(int inodeSize) {
		this.inodeSize = inodeSize;
	}

	public int getBlocksCount() {
		return this.blocksCount;
	}

	public void setBlocksCount(int blocksCount) {
		this.blocksCount = blocksCount;
	}

	public int getBlocksPerGroup() {
		return this.blocksPerGroup;
	}

	public void setBlocksPerGroup(int blocksPerGroup) {
		this.blocksPerGroup = blocksPerGroup;
	}

	public int getInodesPerGroup() {
		return this.inodesPerGroup;
	}

	public void setInodesPerGroup(int inodesPerGroup) {
		this.inodesPerGroup = inodesPerGroup;
	}

}
