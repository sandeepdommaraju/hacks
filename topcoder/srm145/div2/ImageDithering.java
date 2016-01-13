package topcoder.srm145.div2;

public class ImageDithering {
	
	public int count(String dithered, String[] screen){
		int ditheredPixels = 0;
		for(int i=0;i<screen.length;i++){
			String rowPixels = screen[i];
			for(int j=0;j<rowPixels.length();j++){
				if(dithered.indexOf(rowPixels.charAt(j))!=-1)
					ditheredPixels++;
			}
		}
		return ditheredPixels;
	}
}