package topcoder.srm148;

public class CeyKaps {
	
	public String decipher (String typed, String[] switches) {
		   String s = "";
		   char[] arr = new char[26];
		   for (char c = 'A'; c <= 'Z'; c++) {
			   arr[c - 65] = c;
		   }
		   for (int i = 0; i < switches.length; i++) {
			   update(arr, switches[i]);
		   }
		   for (int i=0; i < typed.length(); i++) {
			   s += arr[typed.charAt(i) - 65];
		   }
		   return s;
	   }
	   
	   public void update(char[] arr, String str) {
		   char x = str.charAt(0);
		   char y = str.charAt(2);
		   int xpos = getpos(arr, x);
		   int ypos = getpos(arr, y);
		   if (xpos == -1 || ypos == -1) return;
		   char temp = arr[xpos];
		   arr[xpos] = arr[ypos];
		   arr[ypos] = temp;
	   }
	   
	   public int getpos(char[] arr, char c) {
		   int pos = -1;
		   for (int i = 0; i < arr.length; i++) {
			   if (arr[i] == c) return i;
		   }
		   return pos;
	   }

}
