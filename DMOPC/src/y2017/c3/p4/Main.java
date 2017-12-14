package y2017.c3.p4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* Solitaire Logic

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int N = reader.nextInt();
        int Q = reader.nextInt();
        int[] revealed = new int[N * 2 + 1]; // 0, r1, r2
        int[][] data = new int[2][N];

        for (int w = 0; w < Q; w++) {
            String[] vals = reader.readLine().split(" ");
            int t = Integer.valueOf(vals[0]);
            if (t == 1) {
                int r = Integer.valueOf(vals[1]);
                int i = Integer.valueOf(vals[2]);
                int v = Integer.valueOf(vals[3]);
                revealed[v] = r;
                data[r - 1][i - 1] = v;
                if (i == 1) {
                    if (v == 1) {

                    } else {
                        int nr = r == 0 ? 1 : 1;
                        revealed[1] = nr;
                        data[nr][0] = 1;
                    }
                }

                if (i == N * 2) {
                    if (v == N * 2) {

                    } else {
                        int nr = r == 0 ? 1 : 1;
                        revealed[N * 2] = nr;
                        data[nr][N - 1] = N * 2;
                    }
                }

            } else { // t == 2, calculate and display
                int v = Integer.valueOf(vals[1]);
                if (revealed[v] != 0) {                               // shown
                    System.out.println(1);
                } else if (v == 1) {                             // first
                    if (data[0][0] != 0 || data[1][0] != 0) {
                        System.out.println(1);
                    } else {
                        System.out.println(2);
                    }
                } else if (v == N * 2) {                         // last
                    if (data[0][N - 1] != 0 || data[1][N - 1] != 0) {
                        System.out.println(1);
                    } else {
                        System.out.println(2);
                    }
                } else { // actual logic lol
                    int si = 0;
                    int ei = N - 1;
                    if (v < N) {
                        ei = v - 1;
                    }
                    if (v > N + 1) {
                        si = v - (N + 1);
                    }

                    int sir1 = si;
                    int c1 = 0;
                    // FOR NOW, DON'T CHECK SANDWICH ON OUTSIDES
                    for (int i = sir1; i <= ei; i++) {
                        int vr1 = data[0][i];
                        if (vr1 == 0) {
                            c1++;
                        } else {
                            if (vr1 < v) {
                                sir1 = Math.max(sir1, i);
                                c1 = 0;
                            } else if (vr1 > v) {
                                break;
                            }
                        }
                    }

                    int sir2 = si;
                    int c2 = 0;
                    for (int i = sir2; i <= ei; i++) {
                        int vr2 = data[1][i];
                        if (vr2 == 0) {
                            c2++;
                        } else {
                            if (vr2 < v) {
                                sir2 = Math.max(sir2, i);
                                c2 = 0;
                            } else if (vr2 > v) {
                                break;
                            }
                        }
                    }
                    System.out.println(c1 + c2);
                }
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
