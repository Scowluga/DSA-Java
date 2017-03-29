package MockJunior.j4;

import java.io.IOException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.*;

/* Good Luck


 */

public class Main {
    public static void main(String[] args) {
        try {
            FastReader reader = new FastReader();

            int count = 0;

            int n1 = reader.nextInt();
            int n2 = reader.nextInt();
            int n3 = reader.nextInt();

            Line l1 = new Line(reader.readLineAsIntegers());
            Line l2 = new Line(reader.readLineAsIntegers());
            Line l3 = new Line(reader.readLineAsIntegers());

            List<Line> lines = new ArrayList<>(Arrays.asList(l1, l2, l3));

            int moveCount = 1;
            while (!l1.complete() || !l2.complete() || !l3.complete()) { // the lines aren't all done
                int time = Collections.min(Arrays.asList((30 * moveCount) - count, l1.lowest(),
                        l2.lowest(),
                        l3.lowest()));

                for (Line l : lines) { // lapse 1 seconds change to min seconds later.
                    l.lapse(time);
                }
                count += time;
                if (count % 30 == 0) {
                    moveCount += 1;
                    move(lines);
                }
            }
            System.out.println(count);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void move(List<Line> lines) {
        int[] longs = new int[3];

        int size = lines.get(0).info.size();

        int max = size;
        int maxIndex = 0;

        int min = size;
        int minIndex = 0;

        longs[0] = size;

        for (int i = 1; i < 3; i ++)  { // the other 2
            size = lines.get(i).info.size();
            longs[i] = size;
            if (size > max) { // if size greater than current max
                max = size; // new max
                maxIndex = i; // new maxIndex
            }
            if (size < min) { // if size less than current min
                min = size;
                minIndex = i;
            }
        }
        if (longs[0] == longs[1]
                || longs[1] == longs[2]
                || longs[0] == longs[2]) {
            // do nothing
        } else { // no ties
            // Do the swap from max index -> minIndex
            lines.get(minIndex).info.add(lines.get(maxIndex).info.removeLast());
        }
    }

    public static class Line {

        LinkedList<Integer> info;

        public Line(List<Integer> info) {
            this.info = new LinkedList<Integer>(info);
        }

        public void lapse(int time) {
            if (this.info.isEmpty()) { return; } // if empty, do nothing
            int size = this.info.getFirst();
            if (size > time ) {
                this.info.set(0, size - time);
            } else if (size == time) {
                this.info.removeFirst();
            } else { // more time removed than initial guy
                this.info.removeFirst();
                lapse(time - size);
            }
        }

        @Override
        public String toString() {
            return this.info.toString();
        }

        public boolean complete() {
            return this.info.isEmpty();
        }

        public Integer lowest() {
            if (this.info.isEmpty()) {
                return 31;
            } else {
                return this.info.getFirst();
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


