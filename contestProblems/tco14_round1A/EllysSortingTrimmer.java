import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EllysSortingTrimmer {
	
	public String getMin(String S, int L){
		String sortedString = "";		
		List<String> stringList = new ArrayList<String>();		
		for(int i=0;i<S.length();i++){
			stringList.add(S.charAt(i)+"");
		}
		
		for(int i=0; i<S.length()-L+1;i++){
			List<String> subList = stringList.subList(stringList.size()-L-i, stringList.size()-i);
			Collections.sort(subList, new Comparator<String>(){
				public int compare(String s1, String s2){
					return s1.compareTo(s2);
				}
			});
		}
		
		for(String s: stringList)
			sortedString += s;
		sortedString = sortedString.substring(0, L);
		
		return sortedString;
	}

}
