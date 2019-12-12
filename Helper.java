
public class Helper {
	private int currentAmount;
	private byte[] bytes;
	private int bytesLength;

	public Helper(byte[] bytes) {
		this.bytes = bytes;
		bytesLength = bytes.length;
	}
	
     public void dumpHexBytes() {
		
        for (int i = 0; i < bytes.length; i++) {
			System.out.print(String.format("%02X", bytes[i]));

			if ((i + 1) % 8 == 0 && (i + 1) != 0) System.out.print("| ");

			for (int k = 0; k < 16; k++) {

				if ((i + 1) % 16 == 0) 
					if ((char) bytes[i] > 31 && (char) bytes[i] <= 123)
						System.out.print((char) bytes[i] + " ");
					else 
						System.out.print("-");
			}
			
			if ((i + 1) % 16 == 0 && (i + 1) != 0) System.out.println("| ");
		}
    }
}
