public class Time {
	
	public String whatTime(int seconds){
		String time = null;
		
		int hours = seconds/3600;
		int minutes = (seconds - 3600*hours)/60;
		int secs = seconds -hours*3600 - minutes*60;		
		
		time = hours+":"+minutes+":"+secs;
		return time;
	}

}