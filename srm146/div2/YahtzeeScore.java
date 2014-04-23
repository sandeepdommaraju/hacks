package srm146.div2;

public class YahtzeeScore {
	
	public int maxPoints(int[] toss){
		int points = 0;
		int number[] = new int[6];
		for(int i=0;i<toss.length;i++){
			number[toss[i]-1] += toss[i];
			if(number[toss[i]-1] >= points)
				points = number[toss[i]-1];
		}
		return points;
	}

}
