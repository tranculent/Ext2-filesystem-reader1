
/**
 * Outputs an array of bytes as returned by read() in a readable hexadecimal format, perhaps with printable ASCII codes by the side.
 * Need to be able to neatly handle having too few bytes -- note the XX entries.
 */ 
public class Helper {
	private int offset;
    
     public void dumpHexBytes(byte[] bytes) {
		offset = 0;
		System.out.println(bytes.length + " bytes length");
        
        while (offset <= bytes.length) {
			// print 8 bytes/"XX" at a time
        	for (int j = offset; j < offset + 8; j++) {
        		if (j < bytes.length) 
					System.out.printf("%02X ", bytes[j]); // print the current byte in hexadecimal format
				else // if the bytes length is greater than the current j (startin from the offset of the current iteration)
        			System.out.print("XX ");
        	}
        	
        	System.out.print("| ");
			
			// print 8 bytes/"XX" at a time starting from the bytes after the previously printed bytes
        	for (int j = offset + 8; j < offset + 16; j++) {
        		if (j < bytes.length)
        			System.out.printf("%02X ", bytes[j]); // print the current byte in hexadecimal format
        		else // if the bytes length is greater than the current j (startin from the offset of the current iteration)
        			System.out.print("XX ");
        	}
        	
        	System.out.print("| ");
			
			// print the first 8 bytes of the current sequence (0-8)
        	for (int j = offset; j < offset + 8; j++) {
        		if (bytes.length > j) {
        			if (bytes[j] > 31 && bytes[j] < 123) // Starting from Space character up until lower case 'z' {
        				System.out.print((char)bytes[j]); // Print the actual character value of the byte
					else
        				System.out.print("~"); // Print '~' if the character is not in the ASCII table in between values 31 and 123
        		}
			}
			
			System.out.print(" | ");
			
			// print the next 8 bytes of the current sequence (8-16)
        	for (int j = offset + 8; j < offset + 16; j++) {
        		if (bytes.length > j) {
        			// Starting from Space character up until lower case 'z'
        			if (bytes[j] > 31 && bytes[j] < 123) 
        				System.out.print((char) bytes[j]); // print the actual character value of the byte
        			else
        				System.out.print("~"); // print the '~' symbol if the character is not in the ASCII table in between values 31 and 123
				}
			}
			
			System.out.print(" |");

        	System.out.println();
    		offset += 16; // increment offset by 16 so 2 sequences of 8 bytes can be read in the next iteration
        }
    }
}