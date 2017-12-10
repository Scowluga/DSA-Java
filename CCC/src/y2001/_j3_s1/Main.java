package y2001._j3_s1;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Keeping Score 15/15
Please don't read this code. It's so bad holy.
I'm almost ashamed to have written it...

...

...

almost ;)

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // --- helpers ---
        // length of line (maximum for suit with 13 cards)
        final int SIZE = 38;

        // card points
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 4);
        map.put("K", 3);
        map.put("Q", 2);
        map.put("J", 1);

        // suit points
        int[] bonus = new int[13 + 1];
        bonus[0] = 3;
        bonus[1] = 2;
        bonus[2] = 1;

        // --- input ---
        String input = reader.readLine();

        // --- logic ---
        // suit ou<t>put
        String ct = "Clubs ";
        String dt = "Diamonds ";
        String ht = "Hearts ";
        String st = "Spades ";

        // suit <p>oints
        int cp = 0;
        int dp = 0;
        int hp = 0;
        int sp = 0;

        // suit starting <i>ndex
        int ci = 0; // always first
        int di = input.indexOf("D");
        int hi = input.indexOf("H");
        int si = input.indexOf("S");

        // suit <c>ards
        String[] cc = input.substring(ci + 1, di).trim().split("");
        String[] dc = input.substring(di + 1, hi).trim().split("");
        String[] hc = input.substring(hi + 1, si).trim().split("");
        String[] sc = input.substring(si + 1).trim().split("");

        // card & suit calculations
        for (int i = 0; i < cc.length; i++) {
            cp += map.getOrDefault(cc[i], 0);
            ct += cc[i] + " ";
        }
        if (cc[0].equals("")) {
            cp += 3;
        } else {
            cp += bonus[cc.length];
        }

        for (int i = 0; i < dc.length; i++) {
            dp += map.getOrDefault(dc[i], 0);
            dt += dc[i] + " ";
        }
        if (dc[0].equals("")) {
            dp += 3;
        } else {
            dp += bonus[dc.length];
        }

        for (int i = 0; i < hc.length; i++) {
            hp += map.getOrDefault(hc[i], 0);
            ht += hc[i] + " ";
        }
        if (hc[0].equals("")) {
            hp += 3;
        } else {
            hp += bonus[hc.length];
        }

        for (int i = 0; i < sc.length; i++) {
            sp += map.getOrDefault(sc[i], 0);
            st += sc[i] + " ";
        }
        if (sc[0].equals("")) {
            sp += 3;
        } else {
            sp += bonus[sc.length];
        }

        // output
        System.out.println("Cards Dealt" + String.format("%" + String.valueOf(SIZE - 11) + "s", "Points"));

        // suits
        System.out.print(ct);
        System.out.println(String.format("%" + String.valueOf(SIZE - ct.length()) + "s", cp));

        System.out.print(dt);
        System.out.println(String.format("%" + String.valueOf(SIZE - dt.length()) + "s", dp));

        System.out.print(ht);
        System.out.println(String.format("%" + String.valueOf(SIZE - ht.length()) + "s", hp));

        System.out.print(st);
        System.out.println(String.format("%" + String.valueOf(SIZE - st.length()) + "s", sp));

        // total
        System.out.println(String.format("%" + SIZE + "s", "Total " + (cp + dp + hp + sp)));
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
