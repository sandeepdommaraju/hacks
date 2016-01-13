package fbhackercup;

import java.util.*;
import java.io.*;

/**
 * Created by sunito on 1/10/16.
 */
public class Price {

    static String path = "/Users/sunito/IdeaProjects/interviews/input/";

    public static void main(String[] args) throws Exception{
        new Price().solve();
    }

    public void solve() throws Exception{
        Scanner sc = new Scanner(new File(path + "price.in"));
        //Scanner sc = new Scanner(System.in);
        File out = new File(path + "myprice.txt");
        if (!out.exists()) {
            out.createNewFile();
        }
        FileWriter fw = new FileWriter(out.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        int tc = Integer.parseInt(sc.nextLine());
        for (int t=1; t<=tc; t++){
            String[] p = sc.nextLine().split(" ");
            int N = Integer.parseInt(p[0]);
            double P = Double.parseDouble(p[1]);
            double[] B = new double[N];
            for (int n=0; n<N; n++)
                B[n] = sc.nextDouble();
            sc.nextLine();
            int res = (int) solve(N, P, B);
            String ans = "Case #" + t + ": " + res + "\n";
            System.out.print(ans);
            bw.write(ans);
        }
        bw.close();
    }

    public double solve(int N, double P, double[] B) {
        double res = 0, sum = 0;
        int i = N - 1;
        int start = i, end = i;
        while (i >= 0) {
            if (B[i] > P) {
                i--;
                sum = 0;
                end = i;
                continue;
            }
            start = i;
            sum += B[start];
            while(sum > P && end > start){
                sum -= B[end];
                --end;
            }
            while(end + 1 < N && sum + B[end + 1] < P) {
                ++end;
                sum += B[end];
            }
            res += (end - start + 1);
            --i;
        }
        return res;
    }
}
