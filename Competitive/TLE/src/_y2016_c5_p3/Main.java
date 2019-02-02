package _y2016_c5_p3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Flight Exam 10/10pt
 * DP (very very very tedious)

The algorithm is fairly standard
    key insight is not too bad (and is in the editorial)
    recurrence is simply max of all the nodes it can reach

(top solution is 40 lines of code... thinking...)


Notes:
When faced with a new problem like this, don't stress. Think
very carefully about the question to search for the key insight
that will allow us to use dp to solve it.

In this case, the plane should only ever pass by a point, or one S below
So, we only care about those points. Then, the recurrence is simply
max of all reachable points, for which there are 2N of them.


*/
public class Main {

    static int N; // checkpoints
    static int I; // increase speed
    static int D; // decrease speed
    static int S; // somersault distance

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // > Input
        N = reader.nextInt();
        I = reader.nextInt();
        D = reader.nextInt();
        S = reader.nextInt();

        In[] is = new In[N]; // ensures sorted input
        for (int i = 0; i < N; i++)
            is[i] = new In(reader.nextLong(), reader.nextLong());
        Arrays.sort(is);

        // > Setup
        List<List<P>> ps = new ArrayList<>();
        ps.add(new ArrayList<>());

        long cd = is[0].d; // current distance
        for (int i = 0; i < is.length; i++) {
            if (is[i].d == cd) { // same distance

                // add current point
                P c = new P(is[i].d, is[i].a, 1);
                int ci = l(ps).indexOf(c);
                if (ci < 0)
                    l(ps).add(c);
                else
                    l(ps).get(ci).v++;

                // add below point
                if (S != 0 && is[i].a - S >= 0) {
                    P b = new P(is[i].d, is[i].a - S, 1);
                    int bi = l(ps).indexOf(b);
                    if (bi < 0)
                        l(ps).add(b);
                    else
                        l(ps).get(bi).v++;
                }
            } else { // new distance
                // initialize array
                ps.add(new ArrayList<>());
                cd = is[i].d;
                // go back so you run the top half of this if
                i--;
            }
        }
        ps.add(0, new ArrayList<>());
        ps.get(0).add(new P(0, 0, 0)); // starting point

        // > Solve

        // for each distance going backward
         for (int c1 = ps.size() - 1; c1 >= 0; c1--) {
            // for each point at that distance
            for (P p1 : ps.get(c1)) {
                long max = 0L;
                // each distance after
                for (int c2 = c1+1; c2 < ps.size(); c2++) {
                    // each point after
                    for (P p2 : ps.get(c2)) {
                        // if reachable
                        if (r(p1, p2)) {
                            max = Math.max(max, p2.m);
                        }
                    }
                }
                p1.m = max + p1.v;
            }
        }

        // > Output
        System.out.println(ps.get(0).get(0).m);
    }

    // whether p1 can reach p2
    static boolean r(P p1, P p2) {
        if (p2.a == p1.a) return true;
        if (p2.a > p1.a) {
            return p2.a - p1.a <= (p2.d - p1.d) * I;
        } else { // p2.1 < p1.a
            return p1.a - p2.a <= (p2.d - p1.d) * D;
        }
    }

    // saves me code
    static List<P> l(List<List<P>> ps) {
        return ps.get(ps.size() - 1);
    }

    // class input
    static class In implements Comparable<In> {
        long d;
        long a;

        In(long d0, long a0) {
            this.d = d0;
            this.a = a0;
        }

        @Override
        public int compareTo(In o) {
            int cd = Long.compare(this.d, o.d);
            if (cd != 0) return cd;
            return Long.compare(this.a, o.a);
        }
    }

    // info
    static class P implements Comparable<P> {

        long d; // distance
        long a; // altitude
        int v; // value
        long m; // memo

        public P(long d0, long a0, int v0) {
            this.d = d0;
            this.a = a0;
            this.v = v0;
        }

        @Override
        public String toString() {
            return "d: " + this.d + " a: " + this.a + " v: " + this.v + " m: " + this.m;
        }

        @Override
        public boolean equals(Object obj) {
            P p = (P)obj;
            return this.d == p.d && this.a == p.a;
        }

        @Override
        public int compareTo(P o) {
            int cd = Long.compare(this.d, o.d);
            if (cd != 0) return cd;
            return Long.compare(this.a, o.a);
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

        public int[] readLineAsIntArray(int n, boolean isOneIndex) throws IOException {
            int[] ret;
            if (isOneIndex) {
                ret = new int[n + 1];
            } else {
                ret = new int[n];
            }
//            int ret = new ArrayList<>();
            int idx = isOneIndex ? 1 : 0;
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
