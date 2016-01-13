package topcoder.srm145.div2;


public class ExerciseMachine {
	public int getPercentages(String time){
		int wholePercentages = 0;
		String[] timeSplit = time.split(":");
		double totalSeconds = Integer.parseInt(timeSplit[0])*3600 + Integer.parseInt(timeSplit[1])*60 + Integer.parseInt(timeSplit[2]);
		for(int i=1;i<totalSeconds;i++){
			double percent = i*100/totalSeconds;
			if(percent - Math.floor(percent) ==0)
				wholePercentages++;
		}
		return wholePercentages;
	}
}