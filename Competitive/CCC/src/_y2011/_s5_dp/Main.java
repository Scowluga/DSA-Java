package _y2011._s5_dp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Switch 12/12pt
 * DP (hard)

First, we understand an important part of the problem:
    lights can only be solved in groups of 1-7.

Next, understand that we only care about off lights in between
groups of those that are on.

From this, we can create 'groups' of lights that are on.
Then, create a look-up-table based on groups of groups where
the range is <= 7.

Then, simple take minimum of solving each group of groups, and
draw from the dp table.

FULL EXPLANATION: http://mmhs.ca/ccc/2011/s5switchsf.txt
I read the explanation, and wrote my own code to implement it

Take away:
    Analyze the problem for specific points that can help us
    isolate the sub-problem.

    In this case, noting that groups cap at 7 lights, we can
    take minimum from each group of lights and recurse
*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // 1. Input
        int N = reader.nextInt();

        boolean[] ls = new boolean[N]; // lights
        for (int i = 0; i < N; i++) ls[i] = reader.nextInt() == 1;


        // 2. Creating Groups
        List<G> gs = new ArrayList<>(); // groups

        boolean isG = false; // is making a group
        int si = 0;
        int ei = 0;

        for (int i = 0; i < N; i++) {
            if (!isG) { // NOT group
                if (ls[i]) { // light on -> start group
                    si = i;
                    ei = i;
                    isG = true;
                }
            } else { // IS group
                if (ls[i]) ei++; // continue group
                else { // end group
                    gs.add(new G(si, ei));
                    isG = false;
                }
            }
        }
        if (isG) gs.add(new G(si, ei));

        // 3. Dynamic Programming
        int[] dp = new int[gs.size() + 1]; // best of group and all to right
        dp[gs.size()] = 0;

        for (int i1 = gs.size() - 1; i1 >= 0; i1--) { // each group
            G g1 = gs.get(i1);

            dp[i1] = Integer.MAX_VALUE;
            for (int i2 = i1; i2 < gs.size(); i2++) {
                G g2 = gs.get(i2);
                if (g2.r - g1.l <= 6) {
                    dp[i1] = Math.min(dp[i1], process(g1.l, g2.r, ls) + dp[i2 + 1]);
                }
            }
        }

        // 4. Output
        System.out.println(dp[0]);

    }

    static int process(int l, int r, boolean[] ls) {
        int d = r - l; // range

        int c = 0; // number of OFF lights
        for (int i = l; i <= r; i++) if (!ls[i]) c++;

        if (d == 0 || d == 1) return 3 - d;
        if (d == 2) return ls[l + 1] ? 1 : 2;
        if (d == 3 || d == 4) return c;
        if (d == 5) { // 6 light case
            if (ls[l + 2] && ls[l + 3]) {
                return 4;
            } else { // good
                return c;
            }
        }
        if (d == 6) { // 7 light case
            if (ls[l + 3]) {
                if (c == 4) return 5;
                else return 4;
            } else { // good
                return c;
            }
        }
        return Integer.MAX_VALUE;
    }

    static class G {

        int l;
        int r;

        G(int x, int y) {
            this.l = x;
            this.r = y;
        }

        @Override
        public boolean equals(Object obj) {
            G p = (G)obj;
            return p.l == this.l && p.r == this.r;
        }

        @Override
        public String toString() {
            return "Group (" + this.l + ", " + this.r + ")";
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
