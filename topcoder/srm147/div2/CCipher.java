package topcoder.srm147.div2;

public class CCipher {
	
	public String decode(String cipherText, int shift) {
		StringBuffer d = new StringBuffer();
		for (int i=0; i<cipherText.length(); i++) {
			d.append(decodeSingle(cipherText.charAt(i), shift));
		}
		return d.toString();
	}
	
	public String decodeSingle(char c, int shift){
		for (int i=0; i< shift; i++) {
			if (c == 'A') c = 'Z';
			else c--;
		}
		return c+"";
	}
	
	public static void main(String[] args) {
		CCipher cip = new CCipher();
		String cipherText = "LIPPSASVPH";
		int shift = 4;
		System.out.println(cip.decode(cipherText, shift));
	}

}
