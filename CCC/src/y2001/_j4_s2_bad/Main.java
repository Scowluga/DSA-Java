package y2001._j4_s2_bad;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Spirals 15/15
A really stupid solution... I forgot you can add empty strings :)
see 'good' solution
*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // move[0: x, 1: y][0: d, 1: r, 2: u, 3:l]
        int[][] move = new int[2][4];
        move[0][0] = 0; // x d
        move[1][0] = 1; // y d
        move[0][1] = 1; // x r
        move[1][1] = 0; // y r
        move[0][2] = 0; // x u
        move[1][2] = -1; // y u
        move[0][3] = -1; // x l
        move[1][3] = 0; // y l

        int n1 = reader.nextInt();
        int n2 = reader.nextInt();

        // check edge cases
        if (n1 == n2) {
            System.out.println(n1);
            return;
        } else if (n2 - n1 == 1) {
            System.out.println(n1);
            System.out.println(n2);
            return;
        }

        // access by out[y][x]
        int[][] out = new int[10][10];
        int x = 4;
        int y = 4;
        out[y][x] = n1;

        // first c1 or not
        boolean first = true;
        // 1, 1, 2, 2, 3, 3
        int c1 = 1;
        // counter for c1
        int c2 = 0;
        // direction counter
        int dc = 0;

        // for output
        int xMin = 4;
        int xMax = 4;
        int yMin = 4;
        int yMax = 4;

        for (int v = n1 + 1; v <= n2; v++) {
            if (c2 < c1) { // keep going
                c2++;
            } else if (c2 == c1) { // caught up
                if (first) { // first time
                    // same number
                    first = false;
                    c2 = 1;
                } else { // second time
                    // next number
                    first = true;
                    c1++;
                    c2 = 1;
                }
                dc++; // change direction
            }

            int d = dc % 4; // 0: d, 1: r, 2: u, 3:l
            x += move[0][d];
            y += move[1][d];

            xMax = Math.max(xMax, x);
            xMin = Math.min(xMin, x);
            yMax = Math.max(yMax, y);
            yMin = Math.min(yMin, y);

            out[y][x] = v;
        }

        // creating output strings

        int baseY = yMax - yMin + 1; // starting index
        String[] lines = new String[baseY];
        Arrays.fill(lines, "");

        for (int iy = yMin; iy <= yMax; iy++) { // each row
            for (int ix = xMin; ix <= xMax; ix++) { // each column
                if (!(out[iy][ix] == 0)) { // has value
                    lines[iy - yMin] += String.format("%3d", out[iy][ix]);
                }
            }
        }

        /*
        ok at this point, we have the array of lines. So all's good is good right? you can just use the following code:

        for (int i = 0; i < lines.length; i++) {
            System.out.println(String.format("%" + String.valueOf((xMax - xMin + 1) * 3) + "s", lines[i]));
        }

        right? no. freaking there's 4 cases where there's the original block of numbers, then trailing digits
        to the left, top, right, or bottom.
        ie. Trailing top
                  x x
            x x x x x
            x x x x x
            x x x x x
            x x x x x
        Each one needs to be treated by itself. I'm hardcoding it idc.
         */

        // output
        if (out[yMin][xMin] == 0) { // case 1: trailing top
            // simplest case. Print normally, as trailing top
            // is default to the right side
            for (int i = 0; i < lines.length; i++) {
                System.out.println(String.format("%" + String.valueOf((xMax - xMin + 1) * 3) + "s", lines[i]));
            }
        } else if (out[yMin][xMax] == 0) { // case 2: trailing right
            // print directly. Easy
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
            }
        } else if (out[yMax][xMin] == 0) { // case 3: trailing left
            // easy. print all formatted to the right
            for (int i = 0; i < lines.length; i++) {
                System.out.println(String.format("%" + String.valueOf((xMax - xMin + 1) * 3) + "s", lines[i]));
            }
        } else if (out[yMax][xMax] == 0) { // case 4: trailing bottom
            // not bad. Just print the last line with no format
            for (int i = 0; i < lines.length - 1; i++) {
                System.out.println(String.format("%" + String.valueOf((xMax - xMin + 1) * 3) + "s", lines[i]));
            }
            System.out.println(lines[lines.length - 1]);
        } else { // case 5: no trailing, perfect square. phew.
            for (int i = 0; i < lines.length; i++) {
                System.out.println(String.format("%" + String.valueOf((xMax - xMin + 1) * 3) + "s", lines[i]));
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
