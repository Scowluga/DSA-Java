package y2014._s5_binarysearch;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Lazy Fox
 * 13.3/20pt DMOJ (TLE)
 * 15/15 CCC Grader
 * DP
 * (My solution)

The main issue with this problem is storing memos based on different distances.
This solution approaches it through a binary search.

TLE's on DMOJ, but AC's on CCC Grader.

The other option is to store movements by pairs of locations.
    (See other solution)

*/
public class Main {

    static int nn;
    static N[] ns;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        nn = reader.nextInt();

        // > Input
        ns = new N[nn];
        for (int i = 0; i < nn; i++)
            ns[i] = new N(reader.nextInt(), reader.nextInt());

        // > Calculate distances
        for (int i = 0; i < nn; i++) {
            ns[i].goTo[i] = new V(i, 0, -1);
            for (int j = i + 1; j < nn; j++) {
                double d = N.dist(ns[i], ns[j]);
                ns[i].goTo[j] = new V(j, d, -1);
                ns[j].goTo[i] = new V(i, d, -1);
            }
        }

        // > Sort distances
        for (int i = 0; i < nn; i++)
            Arrays.sort(ns[i].goTo);

        // > Solve
        int max = 0; N origin = new N(0, 0);
        for (int i = 0; i < nn; i++)
            max = Math.max(max, dp(i, N.dist(origin, ns[i])));

        System.out.println(max);
    }

    static int dp(int i, double d) {

        // binary search for next possible distance
        int l = 0;
        int h = nn - 1;
        int m;
        while (l <= h) {
            m = l + (h - l) / 2;
            if (ns[i].goTo[m].d >= d) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }

        return dpi(i, l);
    }

    static int dpi(int i, int gi) {
        if (gi >= nn - 1) return 1;
        if (ns[i].goTo[gi].m != -1) return ns[i].goTo[gi].m;

        return ns[i].goTo[gi].m = Math.max(
                1 + dp(ns[i].goTo[gi].i, ns[i].goTo[gi].d),
                dpi(i, gi+1));
    }

    // neighbor
    static class N {

        int x;
        int y;
        V[] goTo;

        N(int x, int y) {
            this.x = x;
            this.y = y;
            goTo = new V[nn];
        }

        static double dist(N p1, N p2) {
            return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    // value
    static class V implements Comparable<V> {

        int i;    // index of neighbor
        double d; // distance to neighbor
        int m;    // max treats going to neighbor

        public V(int i, double d, int m) {
            this.i = i;
            this.d = d;
            this.m = m;
        }

        @Override
        public int compareTo(V o) {
            return Double.compare(o.d, this.d);
        }

        @Override
        public String toString() {
            return "i:" + this.i + " d:" + this.d;
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
