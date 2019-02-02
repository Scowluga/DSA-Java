package s4;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/* Animal Farm 15/15pt
 * Graph Theory (MST)

Each pen is a node, with edges being the fences
Run Prim's twice, once for animals meeting inside,
once for animals meeting outside

Take minimum

COME BACK TO THIS
    understand Prim's
    why last case WA?

*/
public class Main {

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        // number of pens
        int N = reader.nextInt();

        // nodes
        N[] ns = new N[N+1];
        ns[0] = new N(0); // the outside

        // edge builder
        E[][] eb = new E[1001][1001];

        // > Input
        for (int ni = 1; ni <= N; ni++) {
            N n = new N(ni); ns[ni] = n;
            int edges = reader.nextInt();
            int[] corners = new int[edges];
            for (int i = 0; i < edges; i++) corners[i] = reader.nextInt();
            int[] costs = new int[edges];
            for (int i = 0; i < edges; i++) costs[i] = reader.nextInt();

            for (int i1 = 0; i1 < edges; i1++) {
                int i2 = i1 + 1;
                if (i1 == edges - 1) i2 = 0;
                if (eb[corners[i1]][corners[i2]] == null) {
                    E e = new E(n, ns[0], costs[i1]);
                    n.es.add(e);
                    ns[0].es.add(new E(ns[0], n, costs[i1]));
                    eb[corners[i1]][corners[i2]] = e;
                    eb[corners[i2]][corners[i1]] = e;
                } else {
                    E e = eb[corners[i1]][corners[i2]];
                    e.t = n;
                    ns[0].es.removeIf(obj->obj.t.ni==e.f.ni&&obj.w==e.w);
                    n.es.add(new E(n, e.f, e.w));
                }
            }
        }


        // > Prim's WITH outside
        PriorityQueue<E> pq = new PriorityQueue<>();

        int[] dis = new int[N+1];
        Arrays.fill(dis, Integer.MAX_VALUE);

        boolean[] vis = new boolean[N+1];

        pq.add(new E(null, ns[1], 0));
        dis[1] = 0;

        while (!pq.isEmpty()) {
            E e1 = pq.poll();
            if (vis[e1.t.ni]) continue;
            vis[e1.t.ni] = true;
            for (E e2 : ns[e1.t.ni].es) {
                if (dis[e2.t.ni] > e2.w && !vis[e2.t.ni]) {
                    dis[e2.t.ni] = e2.w;
                    pq.add(new E(null, e2.t, e2.w));
                }
            }
        }

        int sum1 = 0;
        for (int i = 0; i <= N; i++) {
            sum1 += dis[i];
        }

        // > Prim's WITHOUT outside
        pq = new PriorityQueue<>();

        dis = new int[N+1];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;

        vis = new boolean[N+1];
        vis[0] = true;

        pq.add(new E(null, ns[1], 0));
        dis[1] = 0;

        while (!pq.isEmpty()) {
            E e1 = pq.poll();
            if (vis[e1.t.ni]) continue;
            vis[e1.t.ni] = true;
            for (E e2 : ns[e1.t.ni].es) {
                if (dis[e2.t.ni] > e2.w && !vis[e2.t.ni]) {
                    dis[e2.t.ni] = e2.w;
                    pq.add(new E(null, e2.t, e2.w));
                }
            }
        }

        int sum2 = 0;
        boolean successful = true;
        for (int i = 0; i <= N; i++) {
            if (dis[i] == Integer.MAX_VALUE) {
                successful = false;
                break;
            }
            sum2 += dis[i];
        }
        if (!successful) sum2 = Integer.MAX_VALUE;

        // > Output
        System.out.println(Math.min(sum1, sum2));
    }

    static class N {
        int ni; // index
        List<E> es; // edges

        N(int ni) {
            this.ni = ni;
            this.es = new ArrayList<>();
        }

        @Override
        public String toString() {
            String s = String.format("(%d): ", this.ni);
            for (E e : es) {
                s += e + " ";
            }
            return s;
        }
    }

    static class E implements Comparable<E> {
        N t; // to
        N f; // from
        int w; // weight

        public E(N f, N t, int w) {
            this.f = f;
            this.t = t;
            this.w = w;
        }

        @Override
        public boolean equals(Object obj) {
            E e = (E)obj;
            return this.t.ni == e.t.ni && this.f.ni == e.t.ni && this.w == e.w;
        }

        @Override
        public String toString() {
            if (f == null) return "ni:" + this.t.ni + " w:" + this.w;
            return String.format("%d -> %d w:%d", f.ni, t.ni, w);
        }

        @Override
        public int compareTo(E o) {
            return Integer.compare(this.w, o.w);
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

        public int[] readLineAsIntArray(int n, boolean isOneIndex) throws IOException {
            int[] ret;
            if (isOneIndex) {
                ret = new int[n + 1];
            } else {
                ret = new int[n];
            }
//            int ret = new ArrayList<>();
            int idx = isOneIndex ? 1 : 0;
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
