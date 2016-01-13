package fbhackercup;

import java.util.*;
import java.io.*;

/**
 * Created by sunito on 1/10/16.
 */
public class Security {

    static String path = "/Users/sunito/IdeaProjects/interviews/input/";

    public static class Line{
        int start;
        int end;
        public Line(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) throws  Exception {
        Scanner sc = new Scanner(new File(path + "in2.txt"));
        //Scanner sc = new Scanner(System.in);
        File out = new File(path + "out2.txt");
        if (!out.exists()) {
            out.createNewFile();
        }
        FileWriter fw = new FileWriter(out.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        int tc = Integer.parseInt(sc.nextLine());
        for (int t=1; t<=tc; t++) {
            int N = Integer.parseInt(sc.nextLine());
            char[][] matrix = new char[2][N];
            matrix[0] = sc.nextLine().toCharArray();
            matrix[1] = sc.nextLine().toCharArray();

            Set<Line> firstHorizontal = getLines(matrix[0]);
            Set<Line> secondHorizontal = getLines(matrix[1]);
            Set<Integer> verticals = getVerticalSegments(matrix);

            Set<Line> fcommon = new HashSet<Line>();
            Set<Line> scommon = new HashSet<Line>();

            int intersectCount = 0;
            for (int v : verticals){
                Iterator<Line> f = firstHorizontal.iterator();
                while(f.hasNext()){
                    Line line = f.next();
                    if (intersect(line, v, -1, null)){
                        fcommon.add(line);
                        ++intersectCount;
                    }
                }
                Iterator<Line> s = secondHorizontal.iterator();
                while(s.hasNext()){
                    Line line = s.next();
                    if (intersect(line, v, -1, null)){
                        scommon.add(line);
                    }
                }
            }
            firstHorizontal.removeAll(fcommon);
            secondHorizontal.removeAll(scommon);
            int guards = intersectCount + firstHorizontal.size() + secondHorizontal.size();
            System.out.println("Case #" + t + ": " + guards);
            String ans = "Case #" + t + ": " + guards + "\n";
            bw.write(ans);
        }
        bw.close();
    }

    public static boolean intersect(Line line, Integer v, int rowno, char[][] matrix) {
        return line.start <= v && v <= line.end;
    }

    public static Set<Line> getLines(char[] arr){
        Set<Line> lines = new HashSet<Line>();
        int start = 0, count = 0;
        for (int i=0; i<arr.length; i++){
            if (arr[i]=='X'){
                if (count > 0){
                    lines.add(new Line(start, start + count - 1));
                    count = 0;
                }
                start = i + 1;
            }else if (arr[i] == '.'){
                count++;
            }
        }
        if (arr[arr.length-1]=='.'){
            lines.add(new Line(start, start + count - 1));
        }
        return lines;
    }

    public static Set<Integer> getVerticalSegments(char[][] matrix){
        Set<Integer> v = new HashSet<Integer>();
        int N = matrix[0].length;
        for (int i=0; i<N; i++) {
            if (matrix[0][i] == '.' && matrix[1][i] == '.') {
                if (N == 1) {
                    v.add(0);
                    return v;
                }
                if (i == 0){
                    if (matrix[0][1] == 'X' || matrix[1][1] == 'X'){
                        v.add(0);
                    }
                } else if (i == N - 1){
                    if (matrix[0][N - 2] == 'X' || matrix[1][N - 2] == 'X'){
                        v.add(N - 1);
                    }
                }else{
                    if ((matrix[0][i - 1] == 'X' && matrix[0][i + 1] == 'X') || (matrix[1][i - 1] == 'X' && matrix[1][i + 1] == 'X')){
                        v.add(i);
                    }
                }
            }
        }
        return v;
    }
}
