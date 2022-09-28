package hr.fer.oprpp1.hw05.crypto;

/**
 * Class granting functions for byte and hex.
 * @author Antonio
 *
 */
public class Util {
	
	public Util(){
		
	}
	
	/**
	 * Function for getting byte array form hex-encoded {@link String}.
	 * @param keyText hex-encoded {@link String}
	 * @return byte array
	 */
	public static byte[] hextobyte(String keyText) {
		
		if (keyText==null || !isAlphanumeric(keyText) || keyText.length() % 2 != 0 ) {
			throw new IllegalArgumentException();
		}
		
		if(keyText.length()==0) {
			byte[] arr= {};
			return arr;
		}
		keyText=keyText.toLowerCase();
		int n= keyText.length()/2;
		byte[] arr= new byte[n];	
		String text=keyText.toUpperCase();
		int j=0;
		for(int i=0; i<keyText.length();i=i+2) {
			String str1=String.valueOf(text.charAt(i));
			String str2=String.valueOf(text.charAt(i+1));
			int broj= Integer.parseInt(str1+str2, 16);
			arr[j]=(byte)broj;
			j++;
		}
		
		return arr;

	}

	/**
	 * Function for getting hex.encoded {@link String} form byte array.
	 * @param byteArray
	 * @return hex-encoded {@link String}
	 */
	public static String bytetohex(byte[] byteArray) {
		if(byteArray.length==0)
			return "";
		StringBuilder sb= new StringBuilder();
		
		for(byte b : byteArray) {
			sb.append(String.format("%02x", b));
		}
		
		return sb.toString();
	}
	
	/**
	 * Checks if string is alphanumeric.
	 * @param str given {@link String}
	 * @return true if string is alphanumeric, else false
	 */
	private static boolean isAlphanumeric(String str) {
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			if (!Character.isLetterOrDigit(c))
				return false;
		}
		return true;
	}

}
