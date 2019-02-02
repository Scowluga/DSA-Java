package _y2007_r3_p1;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Sum of Primes 7/7pt
 * Simple Math

Pretty easy loops
Can be optimized more by breaking in the solvetwo function when searching for 3
*/
public class Main {

    static boolean[] sieve;
    static List<Integer> ps;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();
        setup();
        for (int T = 0; T < 5; T++) {
            int n = reader.nextInt();
            if (n % 2 == 0) { // 2 primes
                AbstractMap.SimpleEntry<Integer, Integer> entry = solvetwo(n);
                System.out.printf("%d = %d + %d", n, entry.getKey(), entry.getValue());
            } else {
                if (sieve[n]) {
                    System.out.printf("%d = %d", n, n);
                    System.out.println("");
                    continue;
                }

                double third = n / 3.0;

                int l = 0;
                int h = ps.size()-1;
                int m;

                while (l <= h) {
                    m = l + (h - l) / 2;
                    if (ps.get(m) >= third) {
                        h = m - 1;
                    } else {
                        l = m + 1;
                    }
                }

                for (int i = l; i >= 0; i--) {
                    int p = ps.get(i);
                    AbstractMap.SimpleEntry<Integer, Integer> entry = solvetwo(n - p);
                    if (entry.getKey() >= p) {
                        System.out.printf("%d = %d + %d + %d", n, p, entry.getKey(), entry.getValue());
                        break;
                    }
                }
            }
            System.out.println("");
        }
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> solvetwo (int n) {
        double half = n / 2.0;

        int l = 0;
        int h = ps.size()-1;
        int m;

        while (l <= h) {
            m = l + (h - l) / 2;
            if (ps.get(m) >= half) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }

        for (int i = l; i < ps.size(); i++) {
            int p = n - ps.get(i);
            if (sieve[p]) {
                return new AbstractMap.SimpleEntry<Integer, Integer>(p, ps.get(i));
            }
        }
        return new AbstractMap.SimpleEntry<Integer, Integer>(cap, cap);
    }

    static final int cap = 10000000;
    private static void setup() {
        sieve = new boolean[cap+1];
        Arrays.fill(sieve, true);
        for (int i = 2; i < cap+1; i++) {
            if (sieve[i]) {
                for (int k = 2 * i; k < cap+1; k+=i) {
                    sieve[k] = false;
                }
            }
        }
        sieve[2] = false;
        sieve[1] = false;
        sieve[0] = false;

        ps = new ArrayList<>();
        for (int i = 0; i < sieve.length; i++)
            if (sieve[i]) ps.add(i);
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
