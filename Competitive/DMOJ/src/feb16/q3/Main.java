package feb16.q3;

import java.io.IOException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.*;

/* Good Luck
note: doesn't work. Just wrote it for fun since i already planned it. DID NOT DEBUG but idea is there

 */

public class Main {

    private static List<Stand> stands;

    public static void main(String[] args) {
        try {
            FastReader reader = new FastReader();
            int numStall = reader.nextInt();
            int numSell = reader.nextInt();

            stands = new ArrayList<>();
            Map<Integer, TreeSet<StandID>> types = new HashMap<>();
            int stand;
            int type;

            for (int i = 0; i < numStall; i ++) { // for each stall, initialize distance
                stand = reader.nextInt();
                stands.add(new Stand(stand));
                types.put(i + 1, new TreeSet<>());
            }

            for (int i = 0; i < numSell; i ++) { // for each selling
                stand = reader.nextInt();
                type = reader.nextInt();
                stands.get(stand - 1).selling.add(type);
                types.get(stand).add(new StandID(type));
            }

            int queryNo = reader.nextInt();

            for (int i = 0; i < queryNo; i ++) {
                String letter = reader.nextString();
                switch(letter) {
                    case "Q":
                        type = reader.nextInt();
                        System.out.println(stands.get(types.get(type).first().id));
                        break;
                    case "A":
                        stand = reader.nextInt();
                        type = reader.nextInt();
                        stands.get(stand - 1).selling.add(type);
                        types.get(type).add(new StandID(stand));
                        break;
                    case "S":
                        stand = reader.nextInt();
                        type = reader.nextInt();
                        List<Integer> sellings = stands.get(stand - 1).selling;
                        if (sellings.contains(type)) { // remove if exists
                            sellings.remove(type);
                            types.get(type).remove(new StandID(stand));
                        } else { // wasn't selling in the first place
                            // do nothing
                        }
                        break;
                    case "E":
                        stand = reader.nextInt();
                        type = reader.nextInt();

                        // move locations
                        stands.get(stand - 1).distance = type;

                        // remove all selling
                        for (Integer id : stands.get(stand - 1).selling) { // all apple types it was selling
                            types.get(id).remove(new StandID(id));
                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class StandID implements Comparable{

        int id;

        public StandID (int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StandID) {
                StandID o = (StandID)obj;
                return this.id == o.id;
            }
            return false;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof StandID) {
                StandID other = (StandID)o;
                return stands.get(this.id).distance - stands.get(other.id).distance;
            }
            return 0;
        }
    }


    public static class Stand {

        int distance;
        List<Integer> selling;

        public Stand (int distance) {
            this.distance = distance;
            this.selling = new ArrayList<>();
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
            byte[] buf = new byte[64]; // line length
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


