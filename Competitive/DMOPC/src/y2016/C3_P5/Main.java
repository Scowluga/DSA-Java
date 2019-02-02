//package y2016.C3_P5;
//
//import java.io.DataInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.*;
//
///* Shoe Shopping II
//
//*/
//public class Main {
//
//    public static void main(String[] args) throws IOException {
//        FastReader reader = new FastReader();
//
//        int N = reader.nextInt();
//        int k = reader.nextInt();
//
//        List<Long> vs = reader.readLineAsLongs();
//        List<Double>[] memo = new List[4];
//        for (int i = 0; i < 4; i++) memo[i] = new ArrayList<>();
//        memo[0].add(0.0);
//        memo[1].add((double)vs[1]);
//        memo[2].add((double)vs[1] + vs[2]);
//        memo[2].add(two(vs[1], vs[2]));
//
//        for (int i = 3; i <= N; i++) {
//            List<Double> l = new ArrayList<>();
//            for (Double d : memo[(i-1)%4]) {
//                l.add(d + vs[i]);
//            }
//
//            for (Double d : memo[(i-2)%4]) {
//                l.add(d + two(vs[i], vs[i-1]));
//            }
//
//            for (Double d : memo[(i-3)%4]) {
//                l.add(d + three(vs[i], vs[i-1], vs[i-2]));
//            }
//            Collections.sort(l);
//            if (l.size() > k) {
//                l = l.subList(0, k+1);
//            }
//            memo[i%4] = l;
//        }
//
//        List<Double> lst = memo[N%4];
//        if (lst.size() < k) {
//            System.out.println(-1);
//
//        } else {
//            Collections.sort(lst);
//            System.out.println(lst.get(k-1));
//        }
//
//    }
//
//
//    static double two(int n1, int n2) {
//        return (Math.min(n1, n2) * 0.5) + Math.max(n1, n2);
//    }
//    static double three(int n1, int n2, int n3) {
//        return n1 + n2 + n3 - min(n1, n2, n3);
//    }
//    static <T> T min(T... args) {
//        Arrays.sort(args);
//        return args[0];
//    }
//
//    public static class FastReader {
//
//        private final int BUFFER_SIZE = 1 << 16;
//        private final DataInputStream din;
//
//        private final byte[] buffer;
//        private int bufferPointer, bytesRead;
//
//        public FastReader() {
//            din = new DataInputStream(System.in);
//            buffer = new byte[BUFFER_SIZE];
//            bufferPointer = bytesRead = 0;
//        }
//
//        public FastReader(String file_name) throws IOException {
//            din = new DataInputStream(new FileInputStream(file_name));
//            buffer = new byte[BUFFER_SIZE];
//            bufferPointer = bytesRead = 0;
//        }
//
//        public String readLine() throws IOException {
//            byte[] buf = new byte[6400]; // line length
//            int cnt = 0, c;
//            while ((c = read()) != -1) {
//                if (c == '\n')
//                    break;
//                buf[cnt++] = (byte) c;
//            }
//            return new String(buf, 0, cnt);
//        }
//
//        public List<Integer> readLineAsIntegers() throws IOException {
////        int[] ret = new int[1024];
//            List<Integer> ret = new ArrayList<>();
//            int idx = 0;
//            byte c = read();
//            while (c != -1) {
//                if (c == '\n' || c == '\r')
//                    break;
//
//                // next integer
//                int i = 0;
//                while (c <= ' ') {
//                    c = read();
//                }
//                boolean negative = (c == '-');
//                if (negative) {
//                    c = read();
//                }
//
//                do {
//                    i = i * 10 + (c - '0');
//                    c = read();
//                } while (c >= '0' && c <= '9');
////            ret[idx++] = (negative) ? -i : i;
//                ret.add((negative) ? -i : i);
//            }
//            return ret;
//        }
//
//        public List<Long> readLineAsLongs() throws IOException {
//            List<Long> ret = new ArrayList<>();
//            int idx = 0;
//            byte c = read();
//            while (c != -1) {
//                if (c == '\n' || c == '\r')
//                    break;
//
//                // next integer
//                long i = 0;
//                while (c <= ' ') {
//                    c = read();
//                }
//                boolean negative = (c == '-');
//                if (negative) {
//                    c = read();
//                }
//
//                do {
//                    i = i * 10 + (c - '0');
//                    c = read();
//                } while (c >= '0' && c <= '9');
//                ret.add((negative) ? -i : i);
//            }
//            return ret;
//        }
//
//        public List<Double> readLineAsDoubles() throws IOException {
//            List<Double> ret = new ArrayList<>();
//            int idx = 0;
//            byte c = read();
//            while (c != -1) {
//                if (c == '\n' || c == '\r')
//                    break;
//
//                // next integer
//                double d = 0, div = 1;
//                while (c <= ' ') {
//                    c = read();
//                }
//                boolean negative = (c == '-');
//                if (negative) {
//                    c = read();
//                }
//
//                do {
//                    d = d * 10 + (c - '0');
//                    c = read();
//                } while (c >= '0' && c <= '9');
//
//                if (c == '.') {
//                    while ((c = read()) >= '0' && c <= '9') {
//                        d += (c - '0') / (div *= 10);
//                    }
//                }
//                ret.add((negative) ? -d : d);
//            }
//            return ret;
//        }
//
//        public int nextInt() throws IOException {
//            int ret = 0;
//            byte c = read();
//            while (c <= ' ')
//                c = read();
//            boolean neg = (c == '-');
//            if (neg)
//                c = read();
//            do {
//                ret = ret * 10 + c - '0';
//            } while ((c = read()) >= '0' && c <= '9');
//
//            return (neg) ? -ret : ret;
//        }
//
//        public long nextLong() throws IOException {
//            long ret = 0;
//            byte c = read();
//            while (c <= ' ')
//                c = read();
//            boolean neg = (c == '-');
//            if (neg) {
//                c = read();
//            }
//
//            do {
//                ret = ret * 10 + c - '0';
//            }
//            while ((c = read()) >= '0' && c <= '9');
//            return (neg) ? -ret : ret;
//        }
//
//        public double nextDouble() throws IOException {
//            double ret = 0, div = 1;
//            byte c = read();
//            while (c <= ' ') {
//                c = read();
//            }
//            boolean neg = (c == '-');
//            if (neg) {
//                c = read();
//            }
//            do {
//                ret = ret * 10 + c - '0';
//            } while ((c = read()) >= '0' && c <= '9');
//
//            if (c == '.') {
//                while ((c = read()) >= '0' && c <= '9') {
//                    ret += (c - '0') / (div *= 10);
//                }
//            }
//
//            return (neg) ? -ret : ret;
//        }
//
//        public String nextString() throws IOException {
//            byte[] ret = new byte[1024];
//            int idx = 0;
//            byte c = read();
//            while (c <= ' ') {
//                c = read();
//            }
//            do {
//                ret[idx++] = c;
//                c = read();
//            } while (c != -1 && c != ' ' && c != '\n' && c != '\r');
//            return new String(ret, 0, idx);
//
//        }
//
//        private byte read() throws IOException {
//            if (bufferPointer == bytesRead) {
//                // fill buffer
//                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
//                if (bytesRead == -1) {
//                    buffer[0] = -1;
//                }
//            }
//            return buffer[bufferPointer++];
//        }
//
//        public int[] readLineAsIntArray(int n, boolean isOneIndex) throws IOException {
//            int[] ret;
//            if (isOneIndex) {
//                ret = new int[n + 1];
//            } else {
//                ret = new int[n];
//            }
////            int ret = new ArrayList<>();
//            int idx = isOneIndex ? 1 : 0;
//            byte c = read();
//            while (c != -1) {
//                if (c == '\n' || c == '\r')
//                    break;
//
//                // next integer
//                int i = 0;
//                while (c <= ' ') {
//                    c = read();
//                }
//                boolean negative = (c == '-');
//                if (negative) {
//                    c = read();
//                }
//
//                do {
//                    i = i * 10 + (c - '0');
//                    c = read();
//                } while (c >= '0' && c <= '9');
//
//                ret[idx++] = (negative) ? -i : i;
//                if (idx >= n) {
//                    break;
//                }
//
////                ret.add((negative) ? -i : i);
//            }
//            return ret;
//        }
//
//        public void close() throws IOException {
//            if (din != null) {
//                din.close();
//            }
//        }
//
//    }
//}
