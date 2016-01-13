package fbhackercup;

import java.util.*;
import java.io.*;

/**
 * Created by sunito on 1/11/16.
 */
public class Editor {

    static String path = "/Users/sunito/IdeaProjects/interviews/input/";

    public static void main(String[] args) throws Exception{
        new Editor().solve();
    }

    public void solve() throws Exception{
        Scanner sc = new Scanner(new File(path + "text_editor.in"));
        //Scanner sc = new Scanner(System.in);
        File out = new File(path + "myeditor.txt");
        if (!out.exists()) {
            out.createNewFile();
        }
        FileWriter fw = new FileWriter(out.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        int tc = Integer.parseInt(sc.nextLine());
        for (int t=1; t<=tc; t++) {
            String[] p = sc.nextLine().split(" ");
            int N = Integer.parseInt(p[0]);
            int K = Integer.parseInt(p[1]);
            String[] words = new String[N];
            for (int n=0; n<N; n++){
                words[n] = sc.nextLine();
            }
            Arrays.sort(words);
            int[][] DP = new int[K][N];
            for (int i=0; i<K; i++)
                Arrays.fill(DP[i], Integer.MAX_VALUE);

            for (int k=0; k<K; k++) {
                for (int n=k; n<N; n++) {
                    if (n == 0){
                        DP[k][n] = cost(words[n]);
                        continue;
                    }
                    if (k == 0){
                        DP[k][n] = Math.min(DP[k][n-1], cost(words[n]));
                        continue;
                    }
                    int count = 0, tmp = 0;
                    List<String> common = new ArrayList<String>();
                    int i = 1;
                    common.add(words[n]);
                    count = 1;
                    while (n - i >= 0 && count < k + 1 && commonPrefix(words[n], words[n-i])){
                        common.add(words[n-i]);
                        i++;
                        count++;
                    }
                    tmp += cost(words, n-i+1, n);
                    if (k + 1 - count > 0) {
                        tmp += DP[k - count][n - count];
                    }
                    DP[k][n] = Math.min(DP[k][n-1], tmp);
                }
            }
            int res = DP[K-1][N-1];
            String ans = "Case #" + t + ": " + res + "\n";
            System.out.print(ans);
            bw.write(ans);
        }
        bw.close();
    }

    public int cost(String word){
        return word.length()*2 + 1;
    }

    public int cost(String[] words, int start, int end){
        if (start == end) return cost(words[start]);
        int cost = 0;
        int s = 0, idx = 0;
        for (int i=start; i<=end; i++){
            if(i<end){
                idx = commonIdx(words[i], words[i+1]);
            }else{
                idx = -1;
            }
            if (s == words[i].length() -1) cost += 1; //print
            else {
                cost += s == 0 ? words[i].length() : words[i].length() - 1 - s; //type
                cost += 1;                     //print
            }
            cost += idx == -1 ? words[i].length() : words[i].length() - 1 - idx;   //del
            s = idx;
        }
        return cost;
    }

    public int commonIdx(String a, String b){
        if (!commonPrefix(a, b)) return 0;
        int min = Math.min(a.length(), b.length());
        for (int i=0; i<min; i++){
            if(a.charAt(i)!= b.charAt(i))
                return i - 1;
        }
        return min - 1;
    }

    public boolean commonPrefix(String a, String b){
        return a.charAt(0) == b.charAt(0);
    }
}
