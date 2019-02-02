package _y2002._s5;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Follow the Bouncing Ball 100/100

*/
public class Main {

    /* starting from bottom, hitting right, from 0
     <
     2
    3 1 ^
     0
     >
     */

    static double W;
    static double H;

    static P sp; // start point
    static double ss; // start slope

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        W = reader.nextDouble();
        H = reader.nextDouble();
        double x0 = reader.nextDouble();
        double y0 = reader.nextDouble();

        P p1 = new P(x0, 0);
        P p2 = new P(W, y0);

        sp = p1;
        ss = slope(p1, p2);

        int c = 1;
        int w = 1;
        while (!done(p2)) {
            double m1 = slope(p1, p2);
            double m2 = m1 * -1.0; // new slope of line

            for (int wn = 0; wn < 4; wn++) {
                if (wn == w) continue;
                P pt = intersect(wn, m2, p2);
                if (pt.x >= 0 && pt.x <= W && pt.y >= 0 && pt.y <= H) {
                    w = wn;
                    p1 = p2;
                    p2 = pt;

                    if ((p2.equals(sp) && m1 == ss) || c > 1000000) {
                        System.out.println("0");
                        return;
                    } else {
                        c++;
                    }
                    break;
                }
            }

        }
        System.out.println(c - 1);
    }

    static P intersect(int wall, double slope, P point) {
        // point-slope form
        // y - y1 = m(x - x1)
        switch(wall) {
            case 0:
                return new P((0 - point.y) / slope + point.x, 0);
            case 1:
                return new P(W, (slope * (W - point.x)) + point.y);
            case 2:
                return new P((H - point.y) / slope + point.x, H);
            case 3:
                return new P(0, (slope * (0 - point.x)) + point.y);
        }
        return null;
    }

    static boolean done(P p0) {
        P p = new P(Math.round(p0.x * 1000) / 1000, p0.y);
        boolean r = (p.x < 5 || p.x > (W - 5)) && (p.y < 5 || p.y > (H - 5));
        return r;
    }

    static double slope(P p1, P p2) {
        if (p1.x == p2.x) {
            // horizontal
        } else if (p1.y == p2.y) {
            // vertical
        }

        return ((p2.y - p1.y) / (p2.x - p1.x));
    }

    static class P {
        double x;
        double y;

        P() {
            this.x = 0;
            this.y = 0;
        }

        P(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            P p = (P)obj;
            return p.x == this.x && p.y == this.y;
        }

        @Override
        public String toString() {
            return "Point (" + this.x + ", " + this.y + ").";
        }
    }

    static class FastReader {

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
