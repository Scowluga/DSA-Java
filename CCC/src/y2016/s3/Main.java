package y2016.s3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/* Phonomenal Reviews 13/15

> Dmoj Editorial: https://dmoj.ca/problem/ccc16s3/editorial
> Amorim guide: https://amorim.ca/display/CS/Phonomenal+Reviews

Not any direct copying from them, but I had to read their guides to even solve it :( feelsbad

*/

public class Main {

    static Node[] nodes; // all nodes
    static int[] phos; // all pho restaurants
    static Map<Integer, List<Integer>> outtaheres; // map of nodes I'm deleting (subtrees contain no phos)

    static int mD = 0; // max distance
    static Node mN; // farthest node

    static int d = 0; // distance calculation for final solve call
    static Boolean[] memo; // what's a dp?


    public static void main(String[] args) {
        try {
//            FastReader reader = new FastReader("C:\\Users\\david\\Documents\\Programming\\Java\\DSA-Java\\CCC\\src\\y2016\\s3\\t1.txt");
//            FastReader reader = new FastReader("C:\\Users\\david\\Documents\\Programming\\Java\\DSA-Java\\CCC\\src\\y2016\\s3\\s3.22.in");
            FastReader reader = new FastReader();

            int n = reader.nextInt(); // number of restaurants
            int m = reader.nextInt(); // number of phos

            nodes = new Node[n];

//            reader.readLine();
            phos = reader.readLineAsIntArray(m);

            for (int i = 0; i < n - 1; i++) { // reading connections
                int a = reader.nextInt();
                int b = reader.nextInt();

                if (nodes[a] == null) nodes[a] = new Node();
                if (nodes[b] == null) nodes[b] = new Node();

                nodes[a].id = a;
                nodes[b].id = b;

                nodes[a].connections.add(b);
                nodes[b].connections.add(a);
            }

            for (int i = 0; i < phos.length; i++) { // setting pho
                nodes[phos[i]].isPho = true;
            }


            Node sNode = nodes[phos[0]]; // start with any node
            memo = new Boolean[nodes.length];

            outtaheres = new HashMap<>(); // setting up nodes to be deleted

            // ?
            ff(sNode, sNode, 0); // find farthest
            cleanUp(); // deleting all nodes


            // ok max distance and starting node are set. Now let's party.

            solve(mN, mN);
            cleanUp();
            // determines d (total distance to solve)


            // finding the one leaf not travelled twice
            ff(mN, mN, 0);

            int ans = d - mD;
//            System.out.println(d + " " + mD);
            System.out.println(ans);

            // printing tree
//            for (int i = 0; i < nodes.length; i++) {
//                System.out.println(nodes[i]);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cleanUp() { // clean up the outtaheres (nodes to be deleted)
        for (Map.Entry<Integer, List<Integer>> entry : outtaheres.entrySet()) {
            nodes[entry.getKey()].connections.removeAll(entry.getValue());
        }
        outtaheres = new HashMap<>();
    }


    static void ff (Node node,
                    Node from,
                    int cD // current distance
    ) {
        for (int i = 0; i < node.connections.size(); i++) {
            Node temp = nodes[node.connections.get(i)];
            if (temp.id != from.id) { // not the past one
                if (temp.isPho) {
                    if (cD + 1 > mD) { // new farthest
                        mD = cD + 1; // new max distance
                        mN = temp;
                    }
                }
                if (hasPho(temp, node)) { // auto delete
                    // there's a pho, continue
                    ff(temp, node, cD + 1);
                } else {
                    // no pho, and deleted. Get outta here
                }

            }
        }
    }

    static void solve (Node start, Node from) {
        // perform a DFS from the starting Node (mN)
        // at each one, if hasPho, d += 2;

        for (int i = 0; i < start.connections.size(); i ++) {
            Node temp = nodes[start.connections.get(i)];
            if (temp.id != from.id) {
                boolean x = hasPho(temp, start);
                if (x) {
                    d += 2;
                    solve(temp, start);
                }
            }
        }
    }


    static boolean hasPho (Node n, Node from) { // simply call for if a subtree has a pho

        // this solution is proper? hopefully let's try it :D

        if (n.isPho) {
            return true;
        } else if (memo[n.id] != null){
            return memo[n.id]; // previously calculated
        } else { // calculate and save
            boolean has = false;
            for (int i = 0; i < n.connections.size(); i++) {
                Node t = nodes[n.connections.get(i)];
                if (t.id != from.id) {
                    boolean tH = hasPho(t, n);
                    if (tH) {
                        has = true;
                        break;
                    }
                }
            }
            memo[n.id] = has;
            if (!has) { // delete this and the rest of it's sub tree
                // old
//                from.connections.remove(new Integer(n.id));
//                n.connections.remove(new Integer(from.id));

                // new
                if (outtaheres.containsKey(n.id)) { //
                    outtaheres.get(n.id).add(from.id);
                } else { // doesn't contain
                    outtaheres.put(n.id, new ArrayList<>(Arrays.asList(from.id)));
                }
                if (outtaheres.containsKey(from.id)) { //
                    outtaheres.get(from.id).add(n.id);
                } else { // doesn't contain
                    outtaheres.put(from.id, new ArrayList<>(Arrays.asList(n.id)));
                }

            }
            return has;
        }


        // this algorithm is basically have a stack, then DFS the entire thing
        /// but we want recursive to memoize (DP)

//        Set<Integer> visited = new HashSet<>();
//        visited.add(from.id);
//
//        Stack<Node> s = new Stack<>();
//        s.push(n);
//
//        while (!s.isEmpty()) {
//            Node temp = s.pop();
//            if (temp.isPho) return true;
//            for (int i = 0; i < temp.connections.size(); i++) {
//                Node temp2 = nodes[temp.connections.get(i)];
//                if (visited.contains(temp2.id)) { // visited, do nothing
//
//                } else if (temp2.isPho) {
//                    return true;
//                } else {
//                    visited.add(temp2.id);
//                    s.push(temp2);
//                }
//            }
//        }
//
//        return false;

    }

    static class Node {

        int id;
        boolean isPho;
        List<Integer> connections;

        public Node() {
            this.connections = new ArrayList<>();
            this.isPho = false;
        }

        @Override
        public String toString() {
            return id + " | " + (isPho ? "PHO" : "NOO") + " | " + connections;
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
