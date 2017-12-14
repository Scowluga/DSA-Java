package y2017.c3.p2;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Towers of Hanoi 100/100
FIX CODE LATER

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        int N = reader.nextInt();
        boolean[] city = new boolean[N + 1];

        for (int i = 1; i <= N; i++) { // 0:false, 1:true
            city[i] = reader.nextInt() == 0 ? false : true;
        }

        if (N == 1) {
            if (city[1]) {
                System.out.println(1);
                System.out.println(1);
            } else {
                System.out.println(0);
            }
            return;
        } else if (N == 2) {
            if (city[1] && city[2]) {
                System.out.println(1);
                System.out.println(1);
            } else {
                System.out.println(0);
            }
            return;
        }

        Stack<Board> q = new Stack<>();
        Board t;

        // toggle first light to solve parity (hopefully)
        t = new Board(city.clone(), new ArrayList<>(), 1);
        t.moves.add(1);
        t.state[1] = !t.state[1];
        t.state[2] = !t.state[2];
        q.add(t);

        // start normally
        t = new Board(city, new ArrayList<>(), 1);
        q.add(t);


        fl: while (!q.isEmpty()) {
            Board b = q.pop();
            if (b.moves.size() > 1000000) continue;
            if (b.position >= (N - 1)) { // last two
                if (b.state[N-1] && b.state[N]) {
                    b.moves.add(N);
                    output(b);
                    return;
                } else if (b.state[N-1] || b.state[N]) {
                    // single occupy parity
                    // do nothing? you're dunked. The
                    // other solution should solve.
                    continue;
                } else {
                    output(b);
                    return;
                }
            }
            for (int i = b.position; i < N - 1; i++) {
                if (b.state[i]) { // on
                    b.position = i + 1;
                    b.state[i] = false;
                    b.state[i + 1] = !b.state[i + 1];
                    b.state[i + 2] = !b.state[i + 2];
                    b.moves.add(i + 1);
                    q.add(b);
                    continue fl;
                }
            }
            // induce final check
            b.position = N - 1;
            q.add(b);

        }
        // should never reach here
        System.out.println(1000000);
    }

    static class Board {
        boolean[] state;
        List<Integer> moves;
        int position;

        Board(boolean[] state, List<Integer> moves, int position) {
            this.state = state;
            this.moves = moves;
            this.position = position;
        }

        @Override
        public String toString() {
            return "P: " + this.position + " " + moves;
        }
    }

    static void output(Board b) {
        System.out.println(b.moves.size());
        for (int i = 0; i < b.moves.size(); i++) {
            System.out.println(b.moves.get(i));
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
