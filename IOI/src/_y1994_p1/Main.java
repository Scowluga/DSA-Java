package _y1994_p1;

import java.io.*;
import java.util.StringTokenizer;

/* The Triangle 6/6
 * DP (simple)

*/
public class Main {

    static int R;
    static int[][] vals; // input triangle
    static int[][] memo; // greatest path

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        vals = new int[R + 1][R + 1];
        for (int r = 1; r <= R; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= r; c++) {
                vals[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        memo = new int[R][R];

        System.out.println(dp(1, 1));
    }

    static int dp(int r, int c) {
        if (r == R) return vals[r][c];
        if (memo[r][c] != 0) return memo[r][c];

        memo[r][c] = Math.max(dp(r + 1, c), dp(r + 1, c + 1)) + vals[r][c];
        return memo[r][c];
    }
}
