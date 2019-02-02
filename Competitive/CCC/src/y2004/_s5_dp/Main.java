package y2004._s5_dp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Super Plumber 50/50
 * DP, Recursion, Movement

DP is beautiful

*/
public class Main {

    static int T = 0;  // top direction
    static int R = 1;  // right direction
    static int B = 2;  // bottom direction
    static int L = 3;  // never used

    static int[][] move = new int[][]{
            {0, 1, 0, -1}, // x
            {-1, 0, 1, 0} // y
    };

    static int r;
    static int c;

    static String[][]    cs; // course strings
    static Integer[][][] cc; // meme-o



    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        r = reader.nextInt();
        c = reader.nextInt();

        do {// for each case
            cs = new String[c][r];
            cc = new Integer[c][r][3];

            // input
            for (int y = 0; y < r; y++) {
                String[] line = reader.readLine().split("");
                for (int x = 0; x < c; x++) {
                    if (line[x].equals("*")) {
                        // all directions returns impossible (-1)
                        for (int d = 0; d < 3; d++) {
                            cc[x][y][d] = -1;
                        }
                    }
                    cs[x][y] = line[x];

                }
            }

            // solve last column
            int x = c - 1;
            int t = 0;
            for (int y = r - 1; y >= 0; y--) {
                if (t == -1) {
                    cc[x][y][R] = -1;
                } else if (cs[x][y].equals("*")) {
                    t = -1;
                    cc[x][y][R] = -1;
                } else if (cs[x][y].equals(".")) {
                    cc[x][y][R] = t;
                } else {
                    t += Integer.parseInt(cs[x][y]);
                    cc[x][y][R] = t;
                }
            }

            // output
            int output = recurse(0, r - 1, T);
            System.out.println(output == -1 ? 0 : output);

            r = reader.nextInt(); c = reader.nextInt();
        } while (!(r == 0 && c == 0));
    }

    static int recurse(int x, int y, int d0) {

        if (cc[x][y][d0] != null) { // memoized
            return cc[x][y][d0];
        }

        int tMax = -1;

        for (int d = 0; d < 3; d++) {
            if ((d0 == 0 && d == 2) || (d0 == 2 && d == 0)) continue;

            int x1 = x + move[0][d];
            int y1 = y + move[1][d];

            if (!((x1 < 0 || x1 == c)       // x out of bounds
                    || (y1 < 0 || y1 == r)  // y out of bounds
            )) {

                int max = recurse(x1, y1, d);

                if (max > tMax) {
                    tMax = max;
                }
            }
        }

        if (tMax != -1) {
            try {
                tMax += Integer.parseInt(cs[x][y]);
            } catch (Exception e) {
            }

        }
        cc[x][y][d0] = tMax;
        return tMax;
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
