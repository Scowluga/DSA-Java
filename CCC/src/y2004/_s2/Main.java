package y2004._s2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* TopYodeller 15/15
Have an array of yodellers
After each competition, just update a "worst rank" attribute
    worst rank calculated by sorting a clone, then looping through
*/
public class Main {

    static int x = 4;
    static Y[] y;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int n = reader.nextInt();
        int k = reader.nextInt();
        y = new Y[n];

        for (int i = 0; i < n; i++) {
            y[i] = new Y(i + 1, reader.nextInt());
        }
        us();

        for (int i = 0; i < k - 1; i++) {
            int[] t = reader.readLineAsIntArray(n);
            for (int ii = 0; ii < n; ii++) {
                y[ii].s += t[ii];
            }
            us();
        }

        List<Y> l = Arrays.asList(y);
        Collections.sort(l);

        int score = l.get(0).s;
        int i = 0;

        while (i < l.size() && l.get(i).s == score) {
            System.out.println(l.get(i).out());
            i++;
        }
    }

    static void us () { // update scores
        // 1. Sort
        List<Y> t = Arrays.asList(y.clone());
        Collections.sort(t);

        // 2. Loop through for tied ranks
        int rank = 1;
        int score = t.get(0).s;
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).s == score) { // same score as previous
                t.get(i).tr = rank;
            } else { // new score
                t.get(i).tr = i + 1;
                score = t.get(i).s;
                rank = i + 1;
            }
        }

        // 3. Update scores in y array
        for (int i = 0; i < y.length; i++) {
            int wr = t.get(
                    t.indexOf(y[i])
            ).tr;
            int nwr = Math.max(y[i].wr, wr);
            y[i].wr = nwr;
        }
    }

    static class Y implements Comparable {

        int n;  // number
        int s;  // score
        int wr; // worst rank
        int tr; // temporary rank for wr calc

        Y(int n, int s) {
            this.n = n;
            this.s = s;
            this.wr = 0;
        }

        @Override
        public boolean equals(Object obj) {
            Y y = (Y)obj;
            return (y.n == this.n && y.s == this.s);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            // for cloning to sort
            Y y = new Y(this.n, this.s);
            y.wr = this.wr;
            return y;
        }

        @Override
        public int compareTo(Object o) {
            // for sorting. By score then number
            Y y = (Y)o;
            if (y.s == this.s) {
                return this.n - y.n;
            } else {
                return y.s - this.s;
            }
        }

        @Override
        public String toString() {
            return "Y| num: " + this.n + " | score: " + this.s + " and wrank: " + this.wr;
        }

        public String out() {
            // for final display
            return "Yodeller " + this.n + " is the TopYodeller: score " + this.s + ", worst rank " + this.wr;
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
