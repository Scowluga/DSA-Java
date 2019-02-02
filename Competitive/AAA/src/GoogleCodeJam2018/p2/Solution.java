package GoogleCodeJam2018.p2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Trouble Sort

Key Insight
> T-Sort sorts all odd indexes, and then all even indexes
> So we just have to check each number to see if it's correct odd/even index wise

Algorithm
1. Loop through to count odd and even occurrences
2. Loop through sorted list and check

*/
public class Solution {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader(); int TT = reader.nextInt(); gl: for (int T = 1; T <= TT; T++) {
            // ----- VARIABLES -----

            // Length of numbers
            int N = reader.nextInt();
            // Raw numbers
            int[] rns = reader.readLineAsIntArray(N, false);
            // Sorted numbers
            int[] sns = rns.clone(); Arrays.sort(sns);

            // Count numbers
            // Holds value -> Odd count, Even count, Frequency
            Map<Integer, Integer[]> cns = new HashMap<>();

            // ----- COUNTING -----
            for (int i = 0; i < N; i++) {
                if (!cns.containsKey(rns[i]))
                    cns.put(rns[i], new Integer[] {0, 0, 0});
                cns.get(rns[i])[i % 2]++;
                cns.get(rns[i])[2]++;
            }

            // ----- SOLUTION -----
            int toc = 0;
            int tec = 0;
            boolean isEven = true;

            int i = 0; // index of number being examined (from sns)
            while (i < N) {

                Integer[] info = cns.get(sns[i]);

                tec += info[0];
                toc += info[1];

                if (tec - toc > 1 || toc - tec > 0) { // uh oh
                    tec -= info[0];
                    toc -= info[1];

                    int oc; // of sorted list
                    int ec; // of sorted list
                    if (info[2] % 2 == 0) { // even amount
                        oc = info[2] / 2;
                        ec = info[2] / 2;
                    } else { // odd amount
                        if (i % 2 == 0) { // starts even
                            oc = info[2] / 2;
                            ec = (info[2] / 2) + 1;
                        } else { // starts odd
                            oc = (info[2] / 2) + 1;
                            ec = info[2] / 2;
                        }
                    }
                    int c = 0;
                    while (info[0] > 0 && info[1] > 0 && oc > 0 && ec > 0) {
                        if (isEven) {
                            info[0]--;
                            ec--;
                        } else {
                            info[1]--;
                            oc--;
                        }
                        c++;
                        isEven = !isEven;
                    }

                    System.out.printf("Case #%d: %s\n", T, tec + toc + c - 1);
                    continue gl;
                }
                if (info[2] % 2 == 1)
                    isEven = !isEven;
                i += info[2];
            }

            // > Output
            System.out.printf("Case #%d: %s\n", T, "OK");
        }
    }


    public static class FastReader {

        private final int BUFFER_SIZE = 1 << 32;
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
