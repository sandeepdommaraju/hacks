public class BinaryCode {
	
	public String[] decode(String message){
		
		String[] decodedStrings = new String[2];
		decodedStrings[0] = "0";
		decodedStrings[1] = "1";
		
		for(int i=0;i<decodedStrings.length;i++){
			for(int j=0; j<message.length();j++){
				int currentMessageNumber = Integer.parseInt(message.substring(j, j+1));
				int previousDecodedNumber = j-1>=0? Integer.parseInt(decodedStrings[i].substring(j-1, j)):0;
				int currentDecodedNumber = Integer.parseInt(decodedStrings[i].substring(j, j+1));
				int nextDecodedNumber = currentMessageNumber - previousDecodedNumber - currentDecodedNumber;
				if(nextDecodedNumber<0 || nextDecodedNumber>1){
					decodedStrings[i] = "NONE";
					break;
				}else if(j!=message.length()-1) decodedStrings[i] += nextDecodedNumber;
			}
		}
		
		return decodedStrings;
	}

}