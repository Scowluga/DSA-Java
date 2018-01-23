package GFSSOC._y2017_j5;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Choosing Extracurriculars 7/7pt
 * DP (multiple pointers)

Honestly I don't like this question. I just gave up.
This code is really bad.

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int cn = reader.nextInt(); // club num
        List<P>[] cs = new List[4];   // clubs

        for (int i = 0; i < 4; i++) cs[i] = new ArrayList();

        for (int c = 0; c < cn; c++)
            for (int y = 0; y < 4; y++)
                cs[y].add(new P(c, reader.nextInt()));

        for (int i = 0; i < 4; i++) Collections.sort(cs[i]);

        if (cn == 1) {
            int max = 0;
            for (int i = 0; i < 4; i++) max = Math.max(max, cs[i].get(0).e);
            System.out.println(max);
        } else if (cn == 2) {
            int max = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i != j)
                        max = Math.max(max, cs[i].get(0).e + cs[j].get(1).e);
                }
            }
            System.out.println(max);
        } else if (cn == 3) {
            int max = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (i != j && j != k && i != k)
                            max = Math.max(max, cs[i].get(0).e + cs[j].get(1).e + cs[k].get(2).e);
                    }
                }
            }
            System.out.println(max);
        } else {
            System.out.println(dp(new int[]{0, 0, 0, 0}, cs));
        }
    }


    static int dp(int[] is, List<P>[] cs) {

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (cs[i].get(is[i]).c == cs[j].get(is[j]).c) {
                    int max = 0;
                    int[] t;
                    if (is[i] < cs[0].size()) {
                        t = is.clone();
                        t[i]++;
                        max  = Math.max(max, dp(t, cs));
                    }
                    if (is[j] < cs[0].size()) {
                        t = is.clone();
                        t[j]++;
                        max = Math.max(max, dp(t, cs));
                    }
                    return max;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += cs[i].get(is[i]).e;
        }
        return sum;
    }

    static class P implements Comparable<P> {

        int c; // club (0 ... cn - 1)
        int e; // excellence

        P(int c0, int e0) {
            this.c = c0;
            this.e = e0;
        }

        @Override
        public boolean equals(Object obj) {
            P p = (P)obj;
            return p.e == this.e && p.c == this.c;
        }

        @Override
        public String toString() {
            return " c:" + this.c + " e:" + this.e + ").";
        }

        @Override
        public int compareTo(P o) {
            return Integer.compare(o.e, this.e);
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
