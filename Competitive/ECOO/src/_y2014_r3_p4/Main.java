package _y2014_r3_p4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/* Baby Talk


omg I literally forgot recurrence.
You have to remember how to do that.

Think in terms of recurrence

*/
public class Main {

    static int[] memo;
    static List<List<Integer>> bs;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();
        for (int TT = 0; TT < 10; TT++) {
            String in = reader.nextLine();
            int N = in.length();

            // building baby words
            bs = new ArrayList<>();
            for (int i = 0; i <= N; i++) bs.add(new ArrayList<>());

            for (int s = 0; s < N-1; s++)
                for (int e = s+2; e <= N; e+=2)
                    if (in.substring(s, (s+e)/2).equals(
                            in.substring((s+e)/2, e)))
                        bs.get(s).add(e);

            // look for longest
            memo = new int[N+1];
            Arrays.fill(memo, -1);

            int max = 0;
            for (int s = 0; s < N; s++)
                max = Math.max(max, dp(s));
            System.out.println(max);
        }
    }

    static int dp(int s) {
        if (memo[s] != -1) return memo[s];

        int max = 0;
        for (int e : bs.get(s))
            max = Math.max(max, (e - s) + dp(e));

        return memo[s] = max;
    }

    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        FastReader(String filename) throws FileNotFoundException {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
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
