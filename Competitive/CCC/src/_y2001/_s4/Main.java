package _y2001._s4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Cookies - https://dmoj.ca/problem/ccc01s4
 * 50/50
 * Intermediate Math
 * Full Solution

Ok so initially, just loop through largest pairs of points, and use largest dist.

However, that's the incorrect algorithm. Instead, observe that when points
A, B, C exist, if a^2 + b^2 = c^2, then points A and B make the diameter, and
point C is on the edge of the circle (this is a property).

When a^2 + b^2 <= c^2, we can use the distance as the diameter.

However, when a^2 + b^2 > c^2, point C is outside of the circle formed by the
diameter of line AB. Thus a new algorithm is required.

For that, we will take a new circle with all three points on the edges. Then
take separate chords AC and BC. Take the midpoints, draw a perpendicular line,
then they will intersect at the center of the circle.

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // setup
        int n = reader.nextInt();
        P[] ps = new P[n];
        for (int i = 0; i < n; i++) ps[i] = new P(reader.nextInt(), reader.nextInt());

        // max diameter for output
        double md = 0;

        // loop through each triple combination of points
        for (int Ai = 0; Ai < n; Ai++) {
            for (int Bi = Ai + 1; Bi < n; Bi++) {
                for (int Ci = 0; Ci < n; Ci++) {
                    if (Ci == Ai || Ci == Bi) continue; // repeated point

                    P A = ps[Ai];
                    P B = ps[Bi];
                    P C = ps[Ci];
                    double a = dist(B, C);
                    double b = dist(A, C);
                    double c = dist(A, B);

                    double m = Math.max(a, Math.max(b, c)); // max distance
                    if ((m == a) && (c*c + b*b > a*a) ||
                            (m == b) && (c*c + a*a > b*b) ||
                            (m == c) && (a*a + b*b > c*c)){
                        // case 2: check chords
                        md = Math.max(md, triple(A, B, C));
                    } else {
                        // case 1: take max distance (diameter)
                        md = Math.max(md, Math.max(a, Math.max(b, c)));
                    }
                }
            }
        }

        System.out.println(String.format("%.2f", md));
    }

    private static double triple(P A, P B, P C) {
        // let us set the two chords as AC (1) and BC (2)
        // the equation of each line can be modelled as
        // y = mx + b, where m is invSlope, b is y-intercept

        double m1 = invSlope(A, C);   // invSlope
        double x1 = middle(A.x, C.x); // mid x
        double y1 = middle(A.y, C.y); // mid y
        double b1 = y1 - (m1 * x1);   // y int

        double m2 = invSlope(B, C);   // invSlope
        double x2 = middle(B.x, C.x); // mid x
        double y2 = middle(B.y, C.y); // mid y
        double b2 = y2 - (m2 * x2);   // y int

        // if a chord is horizontal or vertical, it's annoying
        // we're going to escape it by then changing that
        // chord to the third one (AB)
        if (m1 == Integer.MAX_VALUE || m1 == 0) {
            m1 = invSlope(A, B);      // invSlope
            x1 = middle(A.x, B.x);    // mid x
            y1 = middle(A.y, B.y);    // mid y
            b1 = y1 - (m1 * x1);      // y int
        } else if (m2 == Integer.MAX_VALUE || m2 == 0) {
            m2 = invSlope(A, B);      // invSlope
            x2 = middle(A.x, B.x);    // mid x
            y2 = middle(A.y, B.y);    // mid y
            b2 = y2 - (m2 * x2);      // y int
        }
        /*
        (1) y = m1 * x + b1
        (2) y = m2 * x + b2
            substitute (1) into (2)
        m1 * x + b1 = m2 * x + b2
        (m1 - m2) * x = b2 - b1
        x = (b2 - b1) / (m1 - m2)
        y = m1 * x + b1

        thus (x, y) is the center of the circle
         */

        double x0 = (b2 - b1) / (m1 - m2);
        double y0 = m1 * x0 + b1;
        double diameter = 2 * dist(x0, y0, A.x, A.y);

        return diameter;
    }

    // invSlope of two points
    private static double invSlope(P p1, P p2) {
        if (p1.x == p2.x || p1.y == p2.y) { // horizontal
            return Integer.MAX_VALUE;
        }

        return -1.0 / ((p2.y - p1.y * 1.0) / (p2.x - p1.x));
    }

    // middle of two values
    private static double middle (int v1, int v2) {
        return (v1 + v2 * 1.0) / 2.0;
    }

    // distance between points
    static double dist(P p1, P p2) {
        return Math.sqrt(Math.pow(1.0 * p2.x - p1.x, 2) + Math.pow(1.0 * p2.y - p1.y, 2));
    }

    // distance between points (as values)
    static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(1.0 * x2 - x1, 2) + Math.pow(1.0 * y2 - y1, 2));
    }

    // point
    static class P {
        int x;
        int y;

        P() {
            this.x = 0;
            this.y = 0;
        }

        P(int x, int y) {
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
