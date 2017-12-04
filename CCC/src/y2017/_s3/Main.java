package y2017._s3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Nailed It! 15/15

Brute force implementation.

Essentially, there's a HashMap<Integer, Integer> that maps wood length -> frequency
Then, for each unique pair of wood lengths, we 'check' that length (the height of the fence)

Prevents double checking of heights via HashSet

Check equation: Min (frequency of length 1, frequency of length 2)
    or if length 1 == length 2, take frequency integer divide by 2

 */

public class Main {

    static int maxLength = 0; // output (global variable)
    static int maxCount = 0;

    // map length -> frequency
    static Map<Integer, Integer> woods;
    // HashSet of checked heights
    static Set<Integer> visited;
    // Equivalent to new ArrayList(woods.keySet());
    // List of distinct lengths
    static List<Integer> distinctLengths; // essentially woods.keys()

    // Locally visited heights when checking for maximum height
    static Set<Integer> localVisited;

    public static void main(String[] args) {
        try {
            FastReader reader = new FastReader(); // Pre-built class for input

            int numBoard = reader.nextInt();

            woods = new HashMap<>();
            visited = new HashSet<>();
            distinctLengths = new ArrayList<>();

            // SET UP DATA STRUCTURES
            int temp;
            for (int i = 0; i < numBoard; i ++) {
                temp = reader.nextInt();
                if (woods.containsKey(temp)) {
                    woods.put(temp, woods.get(temp) + 1); // increment frequency count
                } else {
                    woods.put(temp, 1); // one frequency
                    distinctLengths.add(temp);
                }
            }

            for (int start = 0; start < distinctLengths.size(); start ++) { // for each first index
                for (int end = start; end < distinctLengths.size(); end ++) { // for each second index
                    int length = distinctLengths.get(end) + distinctLengths.get(start);
                    if (visited.contains(length)) {
                        continue;
                    } else {
                        visited.add(length);
                    }
                    // We now have a new length to check
                    checkLength(length);
                }
            }

            System.out.print(maxLength + " " + maxCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkLength(int length) {
        int count = 0; // local count
        localVisited = new HashSet<>(); // local visited
        for (int l1 : distinctLengths) { // for each distinct length
            int l2 = length - l1; // the new length required
            if (!woods.containsKey(l2) // the pair doesn't physically exist
                    || localVisited.contains(l2) // any of them have been visited already
                    || localVisited.contains(l1)
                    ) {
                continue;
            }
            // So we know the pair exists and is distinct
            localVisited.add(l1);
            localVisited.add(l2);

            if (l1 == l2) { // same length
                count += woods.get(l1) / 2;
            } else {
                count += Math.min(woods.get(l1), woods.get(l2));
            }
        }
        // Finished looping, now update
        verify(count);
    }

    private static void verify(int count) {
        if (maxLength == count) {
            maxCount += 1;
        } else if (count > maxLength) {
            maxCount = 1;
            maxLength = count;
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