package y2014.p1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* Troyangles 10/10pt
 * DP (logic, not too bad)



*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] ts = new int[N + 2][N + 2];
        for (int r = 1; r <= N; r++) {
            char[] cs = br.readLine().toCharArray();
            for (int c = 0; c < N; c++) {
                ts[c + 1][r] = cs[c] == '#' ? 1 : 0;
            }
        }

        long tn = 0;

        for (int r = N; r > 0; r--) {
            for (int c = 1; c <= N; c++) {
                if (ts[c][r] == 1) { // there's a triangle
                    ts[c][r] += Math.min(ts[c-1][r+1], Math.min(ts[c][r+1], ts[c+1][r+1]));
                    tn += ts[c][r];
                }
            }
        }

        System.out.println(tn);
    }
}
