package _y2006_c5_q5;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/* Ivana 15/15pt
 * DP

This question is a pretty straightforward 15 pointer.
Use a prefix sum array to store the number of odds

memo[][] storing si and ei
    v = max number of odd numbers obtainable from that range onward
        if it's your turn

Then just calculate to see if it's less than half
*/

public class Main {

    static int N;

    static int[] vs; // inputs
    static int[] ps; // prefix sum of odd numbers
    static int[][] memo;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        N = reader.nextInt();
        if (N == 1) System.out.println(reader.nextInt() % 2 == 1 ? 1 : 0);

        vs = new int[N+1];
        ps = new int[N+1];

        for (int i = 1; i <= N; i++) {
            vs[i] = reader.nextInt();
            ps[i] = ps[i-1] + vs[i] % 2;
        }

        memo = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) Arrays.fill(memo[i], -1);

        int c = 0;
        int w = (ps[N] + ps[N]%2) / 2;
        for (int i = 1; i <= N; i++) {
            if (dp(i, i) < w) c++;
        }
        System.out.println(c);
    }

    static int dp(int si, int ei) {
        if (memo[si][ei] != -1) return memo[si][ei];
        if (d(si, ei) == N-1) return 0;

        // the max obtainable is just try right and try left
        int li = si == 1 ? N : si-1;
        int ln = vs[li] % 2 + oc(li, ei) - dp(li, ei);

        int ri = ei == N ? 1 : ei+1;
        int rn = vs[ri] % 2 + oc(si, ri) - dp(si, ri);

        return memo[si][ei] = Math.max(ln, rn);
    }

    // distance between indexes
    static int d (int si, int ei) {
        return ei >= si ? ei - si : ei+N-si;
    }

    // odd number count in index range
    static int oc (int si, int ei) {
        if (si <= ei)
            return ps[N] - (ps[ei] - ps[si-1]);
        else
            return ps[si-1] - ps[ei];
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        String nextLine() throws IOException {
            return br.readLine();
        }
    }
}
