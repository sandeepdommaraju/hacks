import java.util.*;

public class Lottery {
		
	public String[] sortByOdds(String[] rules){
		
		String[] lotteries 		= new String[rules.length];
		int[] 	 choices 		= new int[rules.length];
		int[] 	 blanks  		= new int[rules.length];
		String[] sorted 		= new String[rules.length];
		String[] unique 		= new String[rules.length];
		List<ComputedLottery> computedLotteries = new ArrayList<ComputedLottery>();
		
		long possibilities = 0;
		
		for(int i=0;i<rules.length;i++){
			lotteries[i] = rules[i].substring(0, rules[i].indexOf(":"));
			String tempString = rules[i].substring(rules[i].indexOf(":")+1,rules[i].length());
			StringTokenizer st = new StringTokenizer(tempString, " ");
			choices[i] = Integer.parseInt(st.nextToken());
			blanks[i]  = Integer.parseInt(st.nextToken());
			sorted[i] = st.nextToken();
			unique[i] = st.nextToken();
			
			if(sorted[i].equals("T") &&unique[i].equals("T")){
				// nCr
				possibilities = nPr(choices[i], blanks[i]);
				possibilities = possibilities/factorial(blanks[i]);
			}else if(sorted[i].equals("T") &&unique[i].equals("F")){
				// (n-1+r)Cr
				possibilities = nPr(choices[i]-1+blanks[i],blanks[i]);
				possibilities = possibilities/factorial(blanks[i]);
			}else if(sorted[i].equals("F") && unique[i].equals("T")){
				// nPr = nCr*r!=fact(n)/fact(n-r)
				possibilities = nPr(choices[i], blanks[i]); //10*9*8
			}else if(sorted[i].equals("F") && unique[i].equals("F")){
				// n^r
				possibilities = (long) Math.pow(choices[i], blanks[i]); // 10*10*10
			}
			
			computedLotteries.add(new ComputedLottery(lotteries[i],possibilities));
			
		}
		
		Collections.sort(computedLotteries);
		int i=0;
		for(ComputedLottery c : computedLotteries) {
			lotteries[i] = c.lotteryName;
			//System.out.println(c.lotteryName+": "+c.possibilities);
			i++;
		}
		
		return lotteries;
	}
	
	public long power(int a, int b){
		if(a==0||b==0) return 1;
		int exp = 1;
		for(int k=0;k<b;k++)
			exp *=a;
		return exp;
	}
	
	/*
	 * n!/(n-r)! = n(n-1)(n-2)..(n-r-1)
	 */
	public long nPr(int n, int r){
		long mul = 1;
		for(int k=0;k<r;k++)
			if(n-k >0) mul *= (n-k);
		return mul;
	}
	
	public long factorial(int n){
		if(n==0||n==1) return 1;
		return n*factorial(n-1);
	}
	
	/*
	 * probability = 1/possibilities; so more chances of lottery if possibilities are less; sort ascending order of possibilities
	 */
	public class ComputedLottery implements Comparable<ComputedLottery>{
		String lotteryName;
		long possibilities;
		
		public ComputedLottery(String lotteryName, long possibilities){
			this.lotteryName = lotteryName;
			this.possibilities = possibilities;
		}
		
		public int compareTo(ComputedLottery c){
			if(this.possibilities==c.possibilities){
				return this.lotteryName.compareTo(c.lotteryName);
			}
			else return Long.valueOf(this.possibilities).compareTo(c.possibilities);
		}
	}
	
}