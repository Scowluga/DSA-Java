package y2004._s3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/* Spreadsheet 100/100
 * Implementation (recursion)

*/
public class Main {

    static String alphabet = "ABCDEFGHIJ";
    static String[][] ss;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        ss = new String[9][10];

        // input
        for (int r = 0; r < ss[0].length; r++) {
            for (int c = 0; c < ss.length; c++) {
                ss[c][r] = reader.nextString();
            }
        }

        // recursion
        for (int r = 0; r < ss[0].length; r++) {
            for (int c = 0; c < ss.length; c++) {
                try {
                    // if it's a number, move on
                    Integer.parseInt(ss[c][r]);
                } catch (Exception e) {
                    if (!ss[c][r].equals("*")) // not asterisk
                        recurse(r, c, new HashSet<>());
                }
            }
        }

        // output
        for (int y = 0; y < ss[0].length; y++) {
            for (int x = 0; x < ss.length; x++) {
                System.out.print(ss[x][y].equals("-1")
                        ? "* "
                        : ss[x][y] + " ");
            }
            System.out.println("");
        }
    }

    static int recurse(int row, int col, Set<Entry<Integer, Integer>> vis) {
        String s = ss[col][row];
        try {
            // if it's a number, return directly
            return Integer.parseInt(s);
        } catch (Exception e) {
            String[] values = s.split("\\+");
            int c = 0; // increment value
            int t;     // temporary return
            for (int i = 0; i < values.length; i++) { // for each location (ie. A2, A5, B3)
                t = 0; // the value of that location
                try {
                    t = Integer.parseInt(values[i]); // the number
                } catch (Exception e1) {

                    // recursion
                    Entry<Integer, Integer> entry = parse(values[i]);

                    if (vis.contains(entry)) { // visited
                        // cyclic reference, return -1
                        ss[col][row] = "-1";
                        return -1;
                    } else {
                        // recurse
                        Set<Entry<Integer, Integer>> temp = new HashSet<>(vis);
                        temp.add(new SimpleEntry<Integer, Integer>(row, col));
                        t = recurse(entry.getKey(), entry.getValue(), temp);
                    }
                }

                if (t == -1) { // cyclic
                    ss[col][row] = "-1";
                    return t;
                } else { // increment value
                    c += t;
                }
            }

            // updating spreadsheet
            ss[col][row] = c + "";
            return c;
        }
    }

    static Entry<Integer, Integer> parse(String s) {
        // takes in spreadsheet location, returns row and col

        String l0 = s.substring(0, 1);
        Integer l1 = alphabet.indexOf(l0);

        String n0 = s.substring(1, 2);
        Integer n1 = Integer.parseInt(n0);

        return new SimpleEntry<>(l1, n1 - 1);
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
