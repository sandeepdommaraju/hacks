import java.util.*;
import java.util.Map.Entry;

//TODO does not work for all test cases
public class Bonuses {
	
	private int total = 0;
	private static int[] percent;
	private double leftOver = 0;
	private Hashtable<Integer, Integer> empPercentH = new Hashtable<Integer,Integer>();
	private List<Map.Entry<Integer,Integer>> entryList = new ArrayList<Entry<Integer, Integer>>();
	
	public int[] getDivision(int[] points){
		int[] bonuses = new int[points.length];
		this.getTotal(points);
		this.getPercent(points);
		this.populateHash();
		this.sortHashByValue();
		this.distributeLeftOver();
		bonuses = percent;
		return bonuses;
	}
	
	public void getTotal(int[] points){
		for(int i=0;i<points.length;i++)
			total += points[i];
	}
	
	public void getPercent(int[] points){
		percent = new int[points.length];
		double actual = 0;
		for(int i=0; i<points.length;i++){
			actual = (double) points[i]/total;
			percent[i] = (int) Math.floor(actual*100);
			leftOver += actual*100 - percent[i];
		}
	}
	
	public void populateHash(){
		for(int i=0;i<percent.length;i++)
			empPercentH.put(i, percent[i]);
		entryList = new ArrayList<Map.Entry<Integer,Integer>>(empPercentH.entrySet());
	}
	
	public void sortHashByValue(){
		
		Collections.sort(entryList, new Comparator<Map.Entry<Integer, Integer>>(){
			public int compare(Entry<Integer,Integer> e1, Entry<Integer,Integer> e2){
				if(e1.getValue()==e2.getValue()) return e1.getKey()-e2.getKey();
				return -e1.getValue() + e2.getValue();
			}
		});
	}
	
	public void distributeLeftOver(){
		int leftOverPercent = (int) Math.round(leftOver);
		int employee = 0;
		for(int i=0;i<leftOverPercent;i++){
			Entry<Integer,Integer> entry = entryList.get(i);
			employee = entry.getKey();
			percent[employee] += 1;
			empPercentH.put(employee, percent[employee]);
		}
	}
	
	public static void main(String[] args){
		Bonuses b = new Bonuses();
		int[] points = {5,5,5,5,5,5};
		b.getDivision(points);
		System.out.println(percent);
	}

}