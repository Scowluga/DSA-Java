package y2006._s5;

import java.io.*;
import java.util.*;

/* Origin of Life 15/15pt
 * DP

Use bits to store each state
Compute each next state
then BFS backward

That should've taken a lot less time... in real CCC i wouldn't get it
*/
public class Main {

    static int rn, cn;
    static int A, B, C;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        rn = reader.nextInt();
        cn = reader.nextInt();
        A = reader.nextInt();
        B = reader.nextInt();
        C = reader.nextInt();

        List<Integer>[] prev = new List[1<<(cn*rn)];
        for (int i = 0; i < 1<<(cn*rn); i++)
            prev[i] = new ArrayList<>();

        // > Build map
        for (int s1 = 0; s1 < 1<<(cn*rn); s1++) {
            boolean[][] g1 = expand(s1);
            boolean[][] g2 = live(g1);
            int s2 = reduce(g2);
            prev[s2].add(s1);
        }

        // > Current state input
        boolean[][] g = new boolean[cn][rn];

        for (int r = 0; r < rn; r++) {
            char[] in = reader.nextLine().toCharArray();
            for (int c = 0; c < cn; c++) {
                g[c][r] = (in[c] == '*');
            }
        }

        int start = reduce(g);

        // > BFS
        Queue<S> q = new LinkedList<>();
        q.add(new S(start, 0));

        while (!q.isEmpty()) {
            S s = q.poll();
            if (s.m > 50 || s.n == 0) continue;
            if (prev[s.n].isEmpty()) {
                System.out.println(s.m);
                return;
            }
            for (int i : prev[s.n]) {
                q.add(new S(i, s.m + 1));
            }
        }
        System.out.println(-1);
    }

    static boolean[][] live(boolean[][] g0) {

        boolean[][] g1 = new boolean[cn+2][rn+2];
        for (int r = 0; r < rn; r++)
            for (int c = 0; c < cn; c++)
                g1[c+1][r+1] = g0[c][r];

        boolean[][] g2 = new boolean[cn][rn];
        for (int r = 1; r <= rn; r++) {
            for (int c = 1; c <= cn; c++) {
                // count live neighbors
                int n = 0;

                if (g1[c-1][r-1]) n++;
                if (g1[c-1][r]) n++;
                if (g1[c-1][r+1]) n++;
                if (g1[c][r-1]) n++;
                if (g1[c][r+1]) n++;
                if (g1[c+1][r-1]) n++;
                if (g1[c+1][r]) n++;
                if (g1[c+1][r+1]) n++;

                // update new
                if (g1[c][r]) {
                    if (n < A || n > B) g2[c-1][r-1] = false;
                    else g2[c-1][r-1] = g1[c][r];
                } else { // currently dead
                    if (n > C) g2[c-1][r-1] = true;
                    else g2[c-1][r-1] = g1[c][r];
                }
            }
        }

        return g2;
    }


    // generation -> state (int)
    static int reduce(boolean[][] g) {
        int s = 0;
        for (int r = 0; r < rn; r++)
            for (int c = 0; c < cn; c++)
                if (g[c][r]) s = (s|(1<<(r*cn+c)));

        return s;
    }

    // state (int) --> generation
    static boolean[][] expand(int s) {
        boolean[][] g = new boolean[cn][rn];

        for (int r = 0; r < rn; r++)
            for (int c = 0; c < cn; c++)
                g[c][r] = (s&(1<<(r*cn+c))) != 0;

        return g;
    }

    static class S {
        int n; // state
        int m; // moves

        S(int n, int m) {
            this.n = n;
            this.m = m;
        }
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
