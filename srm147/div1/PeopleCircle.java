package srm147.div1;

public class PeopleCircle {
	
	public static String order(int m, int f, int k){
		String s = "";
		
		int[] removed = new int[m+f];
		int pos = (k-1)%(m+f);
		
		for(int i=0;i<f;i++){
			removed[pos] =1;
			pos = getPos(pos,k,removed);
		}
		
		for(int i=0;i<removed.length;i++)
			if(removed[i]==0) s+= "M";
			else s+= "F";
		
		return s;
	}
	
	public static int getPos(int startPos, int k, int[] removed){
		for(int i=0;i<k;i++){
			startPos = (startPos+1)%removed.length;
			while(removed[startPos]!=0)
				startPos = (startPos+1)%removed.length;
		}
		return startPos;
	}

}
