package y2016._s3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Phonomenal Reviews 15/15

*/

public class Main {

    static Node[] nodes; // all nodes
    static int[] phos; // all pho restaurants

    static int mD = 0; // max distance
    static Node fN; // farthest node (starting)

    static int tD = 0; // distance calculation for final solve
    static Boolean[] memo; // o p t i m i z e


    public static void main(String[] args) throws IOException {

        FastReader reader = new FastReader();

        // --- INPUT ---
        int n = reader.nextInt(); // number of restaurants
        int m = reader.nextInt(); // number of phos

        nodes = new Node[n];

        phos = reader.readLineAsIntArray(m);

        for (int i = 0; i < n - 1; i++) { // reading c
            int a = reader.nextInt();
            int b = reader.nextInt();

            if (nodes[a] == null) {
                nodes[a] = new Node(a);
            }
            if (nodes[b] == null) {
                nodes[b] = new Node(b);
            }

            nodes[a].c.add(b);
            nodes[b].c.add(a);
        }

        for (int i = 0; i < phos.length; i++) { // setting pho
            nodes[phos[i]].isPho = true;
        }

        // --- SOLVE ---
        Node sNode = nodes[phos[0]]; // start with any node
        memo = new Boolean[nodes.length];

        ff(sNode, sNode, 0, false); // find fN (starting node)

        ff(fN, fN, 0, true); // find tD and mD

        System.out.println(tD - mD);

        // printing tree
//        for (int i = 0; i < nodes.length; i++) {
//            System.out.println(nodes[i]);
//        }
    }

    static void ff (Node n1I,
                    Node n0I,
                    int dI,
                    boolean sI
    ) {

        // setup
        Queue<Node> dS = new LinkedList<>();
        n1I.f = n0I;
        n1I.d = dI;
        n1I.s = sI;
        dS.add(n1I);

        // dfs
        while (!dS.isEmpty()) {
            Node n1 = dS.remove(); // 1 (n1) --> 7 (n2) , [4, 3, 2]
            for (int i = n1.c.size() - 1; i >= 0; i--) {
                Node n2 = nodes[n1.c.get(i)];
                if (n2.id != n1.f.id) {
                    if (n2.isPho) {
                        if (n1.d + 1 > mD) {
                            mD = n1.d + 1;
                            fN = n2;
                        }
                    }
                    if (hasPho(n2, n1)) {
                        if (n1.s) tD += 2;

                        n2.f = n1;
                        n2.d = n1.d + 1;
                        n2.s = n1.s;
                        dS.add(n2);
                    }
                }
            }
        }
    }

    static boolean hasPho (Node n1, Node n0) {
        if (n1.isPho) {
            return true;
        } else if (memo[n1.id] != null){
            return memo[n1.id];
        } else { // calculate and save
            boolean hP = false;
            for (int i = n1.c.size() - 1; i >= 0; i--) {
                Node n2 = nodes[n1.c.get(i)];
                if (n2.id != n0.id) {
                    if (hasPho(n2, n1)) {
                        hP = true;
                        break;
                    }
                }
            }
            memo[n1.id] = hP;
            // this 'optimization' of removing nodes actually makes it slower?
            if (!hP) { // delete this and the rest of it's sub tree
                n0.c.remove(new Integer(n1.id));
                n1.c.remove(new Integer(n0.id));
            }
            return hP;
        }
    }

    static class Node {

        int id;
        boolean isPho;
        List<Integer> c;

        // for traversal
        Node f; // from
        int d; // distance from
        boolean s; // is solve

        public Node(int id) {
            this.id = id;
            this.c = new ArrayList<>();
            this.isPho = false;
        }

        @Override
        public String toString() {
            return id + " | " + (isPho ? "PHO" : "NOO") + " | " + c;
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
