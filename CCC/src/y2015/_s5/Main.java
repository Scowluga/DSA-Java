package y2015._s5;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Greedy For Pies 20/20pt
 * DP (hard)


*/
public class Main {

    static int N;
    static int[] nps;

    static int M;
    static int[] mps;

    static long[][][][] memo;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // > INPUT
        N = reader.nextInt();
        nps = new int[N];
        for (int i = 0; i < N; i++) nps[i] = reader.nextInt();

        M = reader.nextInt();
        mps = new int[M+1];
        for (int i = 1; i <= M; i++) mps[i] = reader.nextInt();

        Arrays.sort(mps);

        // > SOLVE
        memo = new long[3001][2][102][102];
        System.out.println(dp(0, true, M, 1));
    }

    static long dp(int ci,    // current index
                  boolean t, // whether you can take | 0: no 1: yes
                  int ui,    // upper bound for M
                  int li     // lower bound for M
    ) { if (memo[ci][t ? 1 : 0][ui][li] == 0) { // not solved

            if (ci >= N) { // done original list
                if (ui >= li) { // more to take
                    if (t) { // can take
                        // take
                        memo[ci][t ? 1 : 0][ui][li] = mps[ui] + dp(ci, !t, ui-1, li);
                    } else {
                        // skip
                        memo[ci][t ? 1 : 0][ui][li] = dp(ci, !t, ui, li+1);
                    }
                } else { // can't take more
                    memo[ci][t ? 1 : 0][ui][li] = 0; // no more sugar :(
                }
            } else { // not done original list
                if (t) { // can take
                    long max = 0;

                    // take current
                    long take = nps[ci] + dp(ci+1, !t, ui, li);
                    max = Math.max(max, take);

                    // take inserted
                    if (ui >= li) {
                        long intake = mps[ui] + dp(ci, !t, ui-1, li);
                        max = Math.max(max, intake);
                    }

                    // skip
                    long skip = dp(ci+1, t, ui, li);
                    max = Math.max(max, skip);

                    memo[ci][t ? 1 : 0][ui][li] = max;

                } else { // cannot take
                    long max = 0;

                    // skip current
                    long skip = dp(ci+1, !t, ui, li);
                    max = Math.max(max, skip);

                    // skip inserted
                    if (ui >= li) {
                        long skipin = dp(ci, !t, ui, li+1);
                        max = Math.max(max, skipin);
                    }
                    memo[ci][t ? 1 : 0][ui][li] = max;
                }
            }
        }

        return memo[ci][t ? 1 : 0][ui][li];
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

        public int[] readLineAsIntArray(int n) throws IOException {
            int[] ret = new int[n];
//            int ret = new ArrayList<>();
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
