package y2010._p2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Tree Pruning 15/15pt
 * DP (knapsack on a tree)

This is a recursive solution. Compute the original difference,
then create a memo table of each node where it stores the least
number of moves for that sub tree to get to each difference.

If a node has two children, we need to merge the two memoized arrays
of the children.

For that, just take every pair of differences.

Learning: Not much, this is just recurrence.
    Think recursively in terms of the tree structure
    Merging possible differences
    Optimization based on a difference

*/
public class Main {

    static int N;
    static int D;

    static int OD;

    static Node[] ns;
    static int[][] memo;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        N = reader.nextInt();
        D = reader.nextInt();

        // > Tree Input
        ns = new Node[N];
        for (int i = 0; i < N; i++) ns[i] = new Node();

        for (int i = 0; i < N; i++) {
            reader.nextInt(); // burn the id input (it comes in order)
            if (reader.nextInt() == 1) ns[i].wn++;
            else ns[i].bn++;

            switch(reader.nextInt()) {
                case 0:
                    ns[i].li = N;
                    ns[i].ri = N;
                    break;
                case 1:
                    ns[i].li = reader.nextInt();
                    ns[ns[i].li].pi = i;
                    ns[i].ri = N;
                    break;
                case 2:
                    ns[i].li = reader.nextInt();
                    ns[ns[i].li].pi = i;
                    ns[i].ri = reader.nextInt();
                    ns[ns[i].ri].pi = i;
                    break;
            }
        }

        // > DFS for wn and bn
        dfs1(0);

        // > Build memo table and solve
        memo = new int[N][2*N];
        OD = ns[0].wn - ns[0].bn;
        dfs2(0);

        // > Output
        System.out.println(memo[0][N+D]);

    }

    // builds wn and bn
    static void dfs1 (int n) {
        if (ns[n].li != N) dfs1(ns[n].li);
        if (ns[n].ri != N) dfs1(ns[n].ri);
        if (n != 0) {
            ns[ns[n].pi].wn += ns[n].wn;
            ns[ns[n].pi].bn += ns[n].bn;
        }
    }

    // builds memo table
    static void dfs2 (int n) {
        if (n == N) return;

        if (ns[n].li != N) dfs2(ns[n].li);
        if (ns[n].ri != N) dfs2(ns[n].ri);

        if (ns[n].li != N && ns[n].ri != N) {
            Arrays.fill(memo[n], -1);

            for (int d1 = 0; d1 < 2*N; d1++) {
                if (memo[ns[n].li][d1] == -1) continue;
                for (int d2 = 0; d2 < 2*N; d2++) {
                    if (memo[ns[n].ri][d2] == -1) continue;

                    int ti = N + OD - (N+OD-d1) - (N+OD-d2);
                    memo[n][ti] = Math.min(memo[n][ti] == -1 ? Integer.MAX_VALUE : memo[n][ti],
                            memo[ns[n].li][d1] + memo[ns[n].ri][d2]);

                }
            }

        } else if (ns[n].li != N) {
            System.arraycopy(memo[ns[n].li], 0, memo[n], 0, 2*N);
        } else if (ns[n].ri != N) {
            System.arraycopy(memo[ns[n].ri], 0, memo[n], 0, 2*N);
        } else {
            Arrays.fill(memo[n], -1);
        }

        // update 0
        memo[n][N + OD] = 0;
        // remove this one
        int ti = N + OD + (ns[n].bn - ns[n].wn);

        memo[n][ti] = Math.min(
                memo[n][ti] == -1 ? Integer.MAX_VALUE : memo[n][ti],
                1
        );
    }

    static class Node {

        int wn; // white #
        int bn; // black #

        int pi; // parent id
        int li; // left child id
        int ri; // right child id

        Node () {}
    }

    public static class FastReader {

        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream din;

        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastReader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[6400]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public List<Integer> readLineAsIntegers() throws IOException {
//        int[] ret = new int[1024];
            List<Integer> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                int i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');
//            ret[idx++] = (negative) ? -i : i;
                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public List<Long> readLineAsLongs() throws IOException {
            List<Long> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                long i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');
                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public List<Double> readLineAsDoubles() throws IOException {
            List<Double> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                double d = 0, div = 1;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    d = d * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');

                if (c == '.') {
                    while ((c = read()) >= '0' && c <= '9') {
                        d += (c - '0') / (div *= 10);
                    }
                }
                ret.add((negative) ? -d : d);
            }
            return ret;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return (neg) ? -ret : ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            return (neg) ? -ret : ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            return (neg) ? -ret : ret;
        }

        public String nextString() throws IOException {
            byte[] ret = new byte[1024];
            int idx = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            do {
                ret[idx++] = c;
                c = read();
            } while (c != -1 && c != ' ' && c != '\n' && c != '\r');
            return new String(ret, 0, idx);

        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                // fill buffer
                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1) {
                    buffer[0] = -1;
                }
            }
            return buffer[bufferPointer++];
        }

        public int[] readLineAsIntArray(int n, boolean isOneIndex) throws IOException {
            int[] ret;
            if (isOneIndex) {
                ret = new int[n + 1];
            } else {
                ret = new int[n];
            }
//            int ret = new ArrayList<>();
            int idx = isOneIndex ? 1 : 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                int i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');

                ret[idx++] = (negative) ? -i : i;
                if (idx >= n) {
                    break;
                }

//                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public void close() throws IOException {
            if (din != null) {
                din.close();
            }
        }

    }
}
