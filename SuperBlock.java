import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.IOException;
import java.lang.Math;

public class SuperBlock {
	private String volumeName;
	private int magicNumber;
	private int inodesCount;
	private int inodeSize;
	private int blocksCount;
	private int blocksPerGroup;
	private int inodesPerGroup;
	private ByteBuffer byterBuffer; // modifications made to the buffer will also be made to the bytes array

	public SuperBlock(byte[] bytes) {
		// bytes represents the data that is stored in the Superblock
		byterBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
	}

	public void initialize() {

	}
}
