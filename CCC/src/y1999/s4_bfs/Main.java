package y1999.s4_bfs;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* A Knightly Pursuit

*/
public class Main {

    static int stalemate;
    static int[][] move;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // move[r, c][val]
        move = new int[][]{
            //   ul  ur  rd  ru  dr  dl  ld  lu
                {+2, +2, -1, +1, -2, -2, -1, +1}, // r
                {-1, +1, +2, +2, +1, -1, -2, -2}  // c
        };


        int NNN = reader.nextInt();

        loop: for (int NN = 0; NN < NNN; NN++) {
            stalemate = Integer.MAX_VALUE;

            int r = reader.nextInt();
            int c = reader.nextInt();
            int pr = reader.nextInt() - 1;
            int pc = reader.nextInt() - 1;
            int kr = reader.nextInt() - 1;
            int kc = reader.nextInt() - 1;


            if (pc == kc && kr == pr + 1) {
                stalemate = 0;
            }

            boolean[][] visited = new boolean[c][r];
            visited[kc][kr] = true;

            Queue<State> queue = new LinkedList<>();
            State s = new State(kr, kc, 1);
            queue.add(s);

            littleLoop: while (!queue.isEmpty()) {
                s = queue.poll();

                int wr = pr + s.t; // win row (pawn)
                if (wr >= r - 1) continue littleLoop;

                for (int d = 0; d < 8; d++) {
                    int nkr = s.kr + move[0][d];
                    int nkc = s.kc + move[1][d];

                    if (nkr < 0 || nkr >= r || nkc < 0 || nkc >= c) {
                        continue;
                    } else if (nkr == wr && nkc == pc) {
                        // win
                        System.out.println("Win in " + (s.t) + " knight move(s).");
                        continue loop;
                    }

                    // valid move, not win

                    if (nkr == wr + 1 && nkc == pc) {
                        // update stalemate
                        stalemate = Math.min(stalemate, s.t);
                    }

                    if (!visited[nkc][nkr]) {
                        queue.add(new State(nkr, nkc, s.t + 1));
                        visited[nkc][nkr] = true;
                    }
                }



            }

            if (stalemate == Integer.MAX_VALUE) {
                System.out.println("Loss in " + (r - pr - 2) + " knight move(s).");
            } else {
                System.out.println("Stalemate in " + stalemate + " knight move(s).");
            }
        }


    }


    static class State {
        int kr;
        int kc;
        int t;

        State(int kr, int kc, int t) {
            this.kr = kr;
            this.kc = kc;
            this.t = t;
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
