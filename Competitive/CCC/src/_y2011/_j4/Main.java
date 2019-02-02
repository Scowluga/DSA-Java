package _y2011._j4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Boring Business 15/15

*/
public class Main {

    /*
    (0, 0) --> b[200][0]
    (0, -200) --> b[200][200]
    (200, -200) --> b[400][200]
    (-200, -200) --> b[0][200]
     */

    static int x; // current x
    static int y; // current y
    static boolean[][] b; // board
    static int[][] m; // udlr movement

    public static void main(String[] args) throws IOException {
        String file = "t.txt";
//        FastReader reader = new FastReader("C:\\Users\\david\\Documents\\Programming\\Java\\DSA-Java\\CCC\\src\\" + "y2011._j4".split(".")[0] + "\\" + "y2011._j4".split(".")[1] + "\\" + "file");
        FastReader reader = new FastReader();

        x = 200;
        y = 1;
        b = new boolean[400 + 1][200 + 1];

        List<String> s = new ArrayList<>(Arrays.asList(
            "d 2", "r 3", "d 2", "r 2", "u 2", "r 2",
            "d 4", "l 8", "u 2"
        ));

        m = new int[4][2];
        m[0][0] = 0; // u x
        m[0][1] = -1; // u y
        m[1][0] = 0; // d x
        m[1][1] = 1; // d y
        m[2][0] = -1; // l x
        m[2][1] = 0; // l y
        m[3][0] = 1; // r x
        m[3][1] = 0; // r y

        b[x][y] = true;

        for (String i : s) {
            g(c(i.substring(0, 1)), Integer.valueOf(i.substring(2, 3)), false);
        }

        while (true) {
            String[] in = reader.readLine().split(" ");
            String d = in[0];
            int n = Integer.valueOf(in[1]);
            if (d.equals("q") || g(c(d), n, true)) {
                return;
            }
        }
    }

    static boolean g(int d, int n, boolean o) { // return true if break
        boolean c = false; // whether we're breaking
        for (int i = 0; i < n; i ++) { // for each move, move once
            x += m[d][0];
            y += m[d][1];
            if (b[x][y]) {
                c = true;
            } else {
                b[x][y] = true;
            }
        }
        if (c) { // uh oh
            System.out.println(p(x, y) + " DANGER");
        } else { // we're safe
            if (o) System.out.println(p(x, y) + " safe");
        }
        return c;
    }

    static int c(String d) {
        switch (d) {
            case "u":
                return 0;
            case "d":
                return 1;
            case "l":
                return 2;
            case "r":
                return 3;
        }
        return -1;
    }

    static String p(int x, int y) {
        return x - 200 + " " + y * -1;
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
