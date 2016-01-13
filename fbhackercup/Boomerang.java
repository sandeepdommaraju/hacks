package fbhackercup;

import java.util.*;
import java.io.*;
/**
 * Created by sunito on 1/9/16.
 */
public class Boomerang {

    static String path = "/Users/sunito/IdeaProjects/interviews/input/";

    public class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public double distTo(Point other){
            double val  = (this.x - other.x)*(this.x - other. x);
            val += (this.y - other.y)*(this.y - other.y);
            return Math.sqrt(val);
        }
    }

    public static void main(String[] args) throws Exception{
            new Boomerang().solve();
    }

    public void solve() throws Exception{
            Scanner sc = new Scanner(new File(path + "in1.txt"));
            File out = new File(path + "out1.txt");
            if (!out.exists()) {
                out.createNewFile();
            }
            FileWriter fw = new FileWriter(out.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            int tc = sc.nextInt();
            for (int t=1; t<=tc; t++) {
                int N = sc.nextInt();
                Point points[] = new Point[N + 1];
                for (int i = 1; i <= N; i++)
                    points[i] = new Point(sc.nextInt(), sc.nextInt());

                double dist[][] = new double[N + 1][N + 1];
                for (int i = 1; i <= N; i++)
                    Arrays.fill(dist[i], -1);

                long boomerangs = 0;
                for (int i = 1; i <= N; i++) {
                    Map<Double, Integer> map = new HashMap<Double, Integer>();
                    for (int j = 1; j <= N; j++) {
                        if (i == j) {
                            dist[i][j] = 0;
                        } else if (dist[i][j] == -1) {
                            dist[i][j] = points[i].distTo(points[j]);
                        }
                        dist[j][i] = dist[i][j];
                        double d = dist[i][j];
                        if (!map.containsKey(d)) {
                            map.put(d, 0);
                        } else {
                            map.put(d, map.get(d) + 1);
                        }
                    }
                    for (Map.Entry<Double, Integer> entry : map.entrySet()) {
                        if (entry.getKey() == 0) continue;
                        int n = entry.getValue();
                        boomerangs += n * (n + 1) / 2;
                    }
                }
                bw.write("Case #" + t + ": " + boomerangs + "\n");
                //System.out.println("Case #" + t + ": " + boomerangs);
            }
        bw.close();
    }

    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() throws Exception{
            br = new BufferedReader(new InputStreamReader(System.in));
            //br = new BufferedReader(new FileReader(path + "boomerang_constellations_example_input.txt"));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine(){
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

    }
}
