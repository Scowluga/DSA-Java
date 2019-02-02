package _VM7WC_y2016_q5_Silver;

import java.io.*;

/* Jayden Eats Chocolate 5/5pt
 * DP (Jumps and visits)

*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] t = br.readLine().split(" ");
        int X = Integer.parseInt(t[0]);
        int Y = Integer.parseInt(t[1]);
        int Z = Integer.parseInt(t[2]);

        int[] dp = new int[N + X + Y + Z];
        dp[0] = 1;

        for (int i = 0; i < N; i++) {
            if (dp[i] != 0) {
                dp[i+X] = Math.max(dp[i+X], dp[i] + 1);
                dp[i+Y] = Math.max(dp[i+Y], dp[i] + 1);
                dp[i+Z] = Math.max(dp[i+Z], dp[i] + 1);
            }
        }

        System.out.println(dp[N] - 1);

    }
}
