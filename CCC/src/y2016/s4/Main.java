package y2016.s4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Combining Riceballs

dp table: indexed by one, dp[s][e] inclusive
    Holds n if able
    Holds -1 if unable
    Holds 0 if un-calculated

*/
public class Main {

    static long max = 0; // max rb size
    static long[][] dp;  // memo table
    static int[] rb;     // rice balls

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int N = reader.nextInt();

        rb = reader.readLineAsIntArray(N + 1);
        dp = new long[N + 1][N + 1];

        long n = recurse(1, N);
        if (n != -1) System.out.println(n);
        else {
            Arrays.sort(rb);
            System.out.println(Math.max(max, rb[N]));
        }
    }


    static long recurse(int start, int end) {
        if (dp[start][end] != 0) { // calculated base case

        } else if (start == end) { // 1 base case
            dp[start][end] = rb[start];
        } else if (end - start == 1) { // 2 base case
            if (rb[start] == rb[end]) {
                dp[start][end] = rb[start] + rb[end];
            } else {
                dp[start][end] = -1;
            }
        } else if (end - start == 2) { // 3 base case
            int v1 = rb[start];
            int v2 = rb[start + 1];
            int v3 = rb[end];
            if (
                    (v1 == v2 && (v1 + v2) == v3)    // 2 2 4
                    || (v1 == (v2 + v3) && v2 == v3) // 4 2 2
                    || v1 == v3                      // 2 4 2
            ) {
                dp[start][end] = v1 + v2 + v3;
            } else {
                dp[start][end] = -1;
            }
        } else {

            // now you've dealt with base cases, time to begin the fun
            // there are 4 or more balls

            // 2 BALL CASE

            boolean done = false;

            for (int s = start; s < end; s++) {
                long left = recurse(start, s);
                if (left == -1) continue;
                long right = recurse(s + 1, end);
                if (right == -1) continue;

                if (left == right) {
                    dp[start][end] = left + right;
                    done = true;
                }
            }

            // 3 BALL CASE

            if (!done)
            for (int s = start; s < end - 1; s++) {     // num s

                long left = recurse(start, s);
                if (left == -1) continue;

                for (int m = s + 1; m < end; m++) { // num m

                    long right = recurse(m + 1, end);
                    if (right == -1) continue;

                    if (left == right) {
                        long mid = recurse(s + 1, m);
                        if (mid != -1) {
                            dp[start][end] = left + right + recurse(s + 1, m);
                            done = true;
                        }
                    } else if (left == right * 2) {
                        if (recurse(s + 1, m) == right) {
                            dp[start][end] = left + right + right;
                            done = true;
                        }
                    } else if (right == left * 2) {
                        if (recurse(s + 1, m) == left) {
                            dp[start][end] = left + left + right;
                            done = true;
                        }
                    }
                }
            }
            if (!done) dp[start][end] = -1;
        }
        max = Math.max(max, dp[start][end]);
        return dp[start][end];
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
