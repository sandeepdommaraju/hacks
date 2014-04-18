package srm146.div1;

/*
 * @idea copied from the top submission
 */
public class Masterbrain {
	
	public int possibleSecrets(String[] guesses, String[] results){
		int noOfPossibleSecrets = 0;
		int lies = 0;
		
		for(int i=1;i<=7;i++){
			for(int j=1;j<=7;j++){
				for(int k=1;k<=7;k++){
					for(int l=1;l<=7;l++){
						String secret = i + "" + j + "" + k + "" + l;
						for(int g=0;g<guesses.length;g++){
							if(!isGuessEqualsResult(secret, guesses[g], results[g]))
								lies++;
						}
						if(lies==1)
							noOfPossibleSecrets++;
					}
				}
			}
		}
		
		return noOfPossibleSecrets;
	}
	
	public boolean isGuessEqualsResult(String secret, String guess, String result){
		int black = 0;
		int white = 0;
		char[] secretChars = secret.toCharArray();
		char[] guessChars  = guess.toCharArray();
		for(int i=0;i<secret.length();i++){
			if(secret.charAt(i)==guess.charAt(i)){
				black++;
				secretChars[i] = '@';
				guessChars[i]  = '#';
			}
		}
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(secretChars[i]==guessChars[j]){
					white++;
					secretChars[i] = '@';
					guessChars[i] = '#';
				}
					
			}
		}
		if(result.equals(black+"b "+white+" w"))
			return true;
		return false;
	}
	
}
