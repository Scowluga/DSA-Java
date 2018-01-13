package y2016._s4_bottom_up;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Combining Riceballs 15/15

The second method of DP is more intuitive to me, where you recurse and memoize.
However, following that approach, upon calling getVal() you have two steps

1. if calculated: return calculated
2. else: calculate

That first step is because of ambiguity. In the beginning of the recursion,
the value will have not yet been calculated, so you have to calculate.

But in the later half, that step is a waste of time. In the look up table approach,
you can skip that first step to directly return, since you know everything before it
has already been calculated


*/
public class Main {

    static long[][] dp;  // memo table
    static int[] rb;     // rice balls

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int N = reader.nextInt();
        rb = reader.readLineAsIntArray(N + 1);

        dp = new long[N + 1][N + 1];
        for (int i = 1; i <= N; i++) Arrays.fill(dp[i], -1);

        for (int size = 2; size <= N; size++) {
            int limit = N - size + 1;

            for (int first = 1; first <= limit; first++) {
                int last = first + size - 1;

                if (r2(first, last)) continue;
                if (size > 2) r3(first, last);
            }

        }

        long max = 0;
        for (int s = 1; s <= N; s++) {
            for (int e = s; e <= N; e++) {
                max = Math.max(max, getVal(s, e));
            }
        }
        System.out.println(max);
    }

    static boolean isComb(int start, int end) {
        if (start == end) return true;
        return dp[start][end] != -1;
    }

    static long getVal(int start, int end) {
        if (start == end) return rb[start];
        return dp[start][end];
    }

    static boolean r2(int start, int end) {
        for (int i = start; i < end; i++) {
            // range [start, i], [i + 1, end]

            if (!isComb(start, i))   continue;
            if (!isComb(i + 1, end)) continue;

            long l = getVal(start, i);
            long r = getVal(i + 1, end);

            if (l == r) {
                dp[start][end] = l + r;
                return true;
            }
        }
        return false;
    }

    static void r3(int start, int end) {
        for (int s = start; s < end - 1; s++) {     // num s
            // left: [start, s]
            if (!isComb(start, s)) continue;
            long l = getVal(start, s);

            for (int m = s + 1; m < end; m++) { // num m
                // mid: [s + 1, m]
                // right: [m + 1, end]

                if (!isComb(s + 1, m)) continue;
                if (!isComb(m + 1, end)) continue;

                long r = getVal(m + 1, end);

                if (l != r) continue;

                long c = getVal(s + 1, m);

                dp[start][end] = l + c + r;
                return;
            }
        }
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
            int idx = 1;
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
