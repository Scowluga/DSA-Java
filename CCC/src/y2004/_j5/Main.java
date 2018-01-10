package y2004._j5;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Fractals
 * 50/50
 * Recursion

 Solve 2011 S3 Alice through the looking glass, then come back.
 This question is essentially that one, but with cases in the recursion


*/
public class Main {

    static int[][] VT;
    static int[][] VL;
    static int[][] VR;
    static int[][] VC;


    static final int N = -1; // impossible
    static final int Y = -2; // certain
    static final int T = 0;  // top case (general recursion, 2011 s3 alice)
    static final int L = 1;  // observe from left
    static final int R = 2;  // observe from right
    static final int C = 3;  // observe center case

    static void initialize() {
        VT = new int[][]{{L, N, N}, {Y, T, N}, {R, N, N}};
        VL = new int[][]{{L, N, N}, {Y, L, N}, {C, Y, L}};
        VR = new int[][]{{C, Y, R}, {Y, R, N}, {R, N, N}};
        VC = new int[][]{{C, Y, C}, {Y, C, Y}, {C, Y, C}};
    }

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int m0 = reader.nextInt(); // required magnification
        int w0 = (int) Math.pow(3, m0);

        int w1 = reader.nextInt(); // some power of 3, width <= 81
        int m1 = (int) Math.cbrt(w1);

        int x1 = reader.nextInt();  // [0, width]

        if (m0 == 0) { // input 0 magnification (flat line)
            System.out.println(1);

            return;
        }

        initialize();

        // setting up map
        boolean[][] map = new boolean[w1][w1];

        int ratio = (int)Math.pow(3, m1 - m0);

        for (int x = 0; x < w1; x++) {
            for (int y = 0; y < w1; y++) {
                if (ratio == 1) {
                    map[x][y] = recurse(x, y, T, m0);
                } else {
                    map[x][y] = recurse(x / ratio, y / ratio, T, m0);
                }
            }
        }


        // output
//        for (int yy = w1 - 1; yy >= 0; yy--) {
//            for (int xx = 0; xx < w1; xx++) {
//                System.out.print(map[xx][yy] ? "*" : " ");
//            }
//            System.out.println("");
//        }
//        System.out.println("-------------------------------");

        // solve question

        boolean l0 = true;
        boolean r0 = true;
        boolean l1;
        boolean r1;

        for (int y = 0; y < w1; y++) {
            // bl, br, tl, tr
            if (x1 == 0) {
                l1 = true;
            } else {
                l1 = map[x1 - 1][y];
            }

            if (x1 == w1) {
                r1 = true;
            } else {
                r1 = map[x1][y];

            }

            if (!(l0 == r0 && l0 == l1 && l0 == r1)) {
                System.out.print(y + 1 + " ");
            }

            l0 = l1;
            r0 = r1;
        }

    }


    static boolean recurse(int x, int y, int type, int mag) {
        if (x < 0 || y < 0) {
            return false;
        } else if (type == Y) {
            return true;
        } else if (type == N) {
            return false;
        }

        if (mag == 1) { // base cases
            switch(type) {
                case T:
                    return VT[x][y] == Y;
                case L:
                    return VL[x][y] == Y;
                case R:
                    return VR[x][y] == Y;
                case C:
                    return VC[x][y] == Y;
            }
        } else {
            int ratio = (int)Math.pow(3, mag - 1);
            int x1 = x % ratio;
            int y1 = y % ratio;
            int xr = x / ratio;
            int yr = y / ratio;
            int t1 = N;
            switch (type) {
                case T:
                    t1 = VT[xr][yr];
                    break;
                case L:
                    t1 = VL[xr][yr];
                    break;
                case R:
                    t1 = VR[xr][yr];
                    break;
                case C:
                    t1 = VC[xr][yr];
                    break;
            }

            return recurse(x1, y1, t1, mag - 1);

        }
        return false;
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
