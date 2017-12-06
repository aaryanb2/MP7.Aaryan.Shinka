import java.util.Scanner;
public class Playfair {
	/**
	 * Used to contain the key.
	 */
	static char[][] arr = new char[5][5];
	/**
	 * contains the message to be encoded, later made into an array in encode.
	 */
	static String code = "";
	
	/**
	 * contains the message to be encoded in AB CD EF . . .  format
	 */
	static String[] encode = null;
	
	/**
	 * this is the key used, has all letters of alphabet except Q
	 */
	static String key = "THEUICKBROWNFXJMPSVRLAZYD";


	/**
	 * This makes the key for the cipher.  
	 * Assumes that the key is 25 letters long, with no punctuations.
	 * @return
	 */
	public static char[][] setKey(String x){
	    x = key;
		int k = 0;
		while(k < 25) {
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < i; j++) {
						if(x.toUpperCase().charAt(k) >= 'A' 
								&& x.toUpperCase().charAt(k) <= 'Z') { // makes sure that the key is between A-Z								
							arr[i][j] = x.toUpperCase().charAt(k);
							k++;
						} else {
							return null; // reject any character not in the alphabet
						}
				}
			}
		}
		return arr;
	}
	
	/**
	 * A helper function
	 * Takes the string to be encoded and formats it so that its length is even and in all caps.
	 * Replaces duplicated characters with an X, so TREE = TR EX ES
	 * @param x
	 * @return
	 */
	public static String makeCode(String x) {
	
		if (x.length() != 0) { // string is not null
			for (int i = 0; i < x.length() - 2; i+= 2) {
				if (x.charAt(i) != x.charAt(i + 1)) { // checks for duplicated letter pairs, like EE
					code += x.toUpperCase().charAt(i) + x.toUpperCase().charAt(i + 1);
				} else {
					code += x.toUpperCase().charAt(i) + "X" + x.toUpperCase().charAt(i + 1); // adds filler character if there is a duplicate
				}
			}
			if (code.length() %2 != 0) {
				code += "Z"; // adds a filler character if length is odd
			} 
			return code;
		} else {
			return null;
		}
	}
	/**
	 * Formats the code so that it is in the AB CD EF ... form
	 * @param x
	 * @return
	 */
	public static String[] setCode(String x) {
		if(x != null) {
			String y = makeCode(x);
			encode = new String[y.length() / 2];
			for(int i = 0; i < encode.length - 1; i+=2) {
				encode[i] = x.substring(i, i+1);
			}
			return encode;
		} else {
			return null;
		}
	}

	public static String[] encode(String[] text) { // the input is AB CD EF . . . made from setCode
		int x = 0;
		int y = 0;
		String[] code = new String[encode.length];
		
		//find the i and j for the first letter of each pair (so H if HI is the pair)
		for(int k = 0; k < text.length; k++) {
			for(int i = 0; i < arr.length; i++) {
				for(int j = 0; j < i; j++) {
					if(arr[i][j] == text[k].charAt(0)) {
						x = i; // the row
						y = j; // the column
					}
					
				}
			}
			// for the case where the second pair is in the same row
			
			if(text[k].charAt(1) == arr[x][y + 1]) {
				code[k] += arr[x][(y + 1) % 5] + arr[x][(y + 2) % 5]; // %5 wraps around, so 5 % 5 = 0, 6 % 5 = 1, 7 % 5 = 2, etc
			}
			
			// for the case where the second pair is in the same column
			if(text[k].charAt(1) == arr[x + 1][y]) {
				code[k] += arr[(x + 1) % 5][y] + arr[(x + 2) % 5][y];
			}
			
			// for the rectangular case
			//checks row
			for(int a = 0; a < arr.length; a++) {
				if(text[k].charAt(1) == arr[x][a]) {
					// adds the letters in the opposite corners of the key
					//
					// A B C D
					// E F G H
					// I J K L
					// L M N O
					//
					//if input is EL, output is HI
					//
					code[k] += arr[a][y] + arr[x][a];
				}
			}
		}
		return code;
	}
	   @SuppressWarnings("resource")
	    public static void main(final String[] unused) {

	        String linePrompt = String.format("Enter a line of text with no punctuations, or a blank line to exit:");

	        /*
	         * Two steps here: first get a line, then a shift integer.
	         */
	        Scanner lineScanner = new Scanner(System.in);
	        repeat: while (true) {
	            String line = null;

	            System.out.println(linePrompt);
	            while (lineScanner.hasNextLine()) {
	                line = lineScanner.nextLine();
	                if (line.length() <= 0 || line.split("[ ,.;?!()-]").length != line.length()) { // checks if there are punctuations
	                    break repeat;
	                } else {	                
	                    break;
	                }
	            }
	            String[] original = setCode(line);
	            System.out.println("Encrypted line with the key: " + key);
	            System.out.println(encode(original));
	        }
	        lineScanner.close();
	   }
	            
}

	
	
	


