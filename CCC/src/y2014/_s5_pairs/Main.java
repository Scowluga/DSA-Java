package y2014._s5_pairs;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Lazy Fox
 * 13.3/20pt DMOJ (TLE)
 * 15/15 CCC Grader
 * DP
 * (Not my solution)

This solution instead stores pairs since we know that
binary searching distances each time is impossible.

Then we loop through all increasing distances, keeping a memo
table of most treats attainable without travelling more.

To finish, just print memo[0] (best value from origin)

TLE's on DMOJ, but AC's on CCC Grader.

The other option uses binary search.
    (See other solution)

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int N = reader.nextInt();

        // > Input
        P[] ps = new P[N+1]; ps[0] = new P(0, 0);
        for (int i = 1; i <= N; i++)
            ps[i] = new P(reader.nextInt(), reader.nextInt());

        // > Pairs of Points
        List<V> vs = new ArrayList<>();
        for (int i = 0; i <= N; i++)
            for (int j = i+1; j <= N; j++)
                vs.add(new V(i, j, P.dist(ps[i], ps[j])));

        Collections.sort(vs);

        // > Solve
        int[] cbs = new int[N+1]; // current bests
        int[] mds = new int[N+1]; // max distances
        int[] pbs = new int[N+1]; // previous bests

        for (V v : vs) {

            // if this new distance is greater than the previous distance
            if (v.d != mds[v.p1]) {
                // update previous distance
                mds[v.p1] = v.d;
                // update the previous best score
                pbs[v.p1] = cbs[v.p1];
            }

            if (v.d != mds[v.p2]) {
                mds[v.p2] = v.d;
                pbs[v.p2] = cbs[v.p2];
            }

            if (v.p1 == 0) { // can't return to origin
                cbs[v.p1] = Math.max(cbs[v.p1], pbs[v.p2] + 1);
            } else {
                // the current best can now include going to the node
                cbs[v.p1] = Math.max(cbs[v.p1], pbs[v.p2] + 1);
                cbs[v.p2] = Math.max(cbs[v.p2], pbs[v.p1] + 1);
            }
        }

        // > Output
        System.out.println(cbs[0]);
    }

    // Point
    static class P {

        int x;
        int y;

        P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }

        static int dist(P p1, P p2) {
            return (int)Math.round(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        }
    }

    // Pair of Points & distance
    static class V implements Comparable<V> {

        int p1;
        int p2;
        int d;

        public V(int p1, int p2, int d) {
            this.p1 = p1;
            this.p2 = p2;
            this.d = d;
        }

        @Override
        public int compareTo(V o) {
            return Long.compare(this.d, o.d);
        }

        @Override
        public String toString() {
            return "d:" + this.d + " | " + this.p1 + " -> " + this.p2;
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
