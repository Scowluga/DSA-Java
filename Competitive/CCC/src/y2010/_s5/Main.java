package y2010._s5;

import java.io.*;
import java.util.StringTokenizer;

/* Nutrient Tree 20/20pt
 * DP (tree)

1. At each Node we hold a memo list of max nutrients by number of growth agents
2. To calculate max nutrients FROM child node (l or r)
    we loop from 0->g for growth agents to stem and to node
    and take minimum (limiting)
3. To combine max obtainable from left and right
    one final loop 0->g for growth agents to left and right
    add together


*/
public class Main {

    static int G;

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();
        String in = reader.nextLine().trim();
        in = in.replace("( ", "(");
        in = in.replace(" )", ")");


        Node root = initializeTree(in);
        G = reader.nextInt();
        dp(root);

        System.out.println(root.memo[G]);
    }

    static void dp (Node n) {

        if (n.l == null && n.r == null) {
            n.memo = new int[G+1];
            for (int i = 0; i <= G; i++)
                n.memo[i] = n.v + i;

        } else {
            // solve left
            dp(n.l);
            int[] memol = new int[G+1];
            for (int g = 0; g <= G; g++) {
                int max = 0;
                for (int i = 0; i <= g; i++)
                    max = Math.max(
                            max,
                            Math.min((1+i)*(1+i), n.l.memo[g-i])
                    );
                memol[g] = max;
            }

            // solve right
            dp(n.r);
            int[] memor = new int[G+1];
            for (int g = 0; g <= G; g++) {
                int max = 0;
                for (int i = 0; i <= g; i++)
                    max = Math.max(
                            max,
                            Math.min((1+i)*(1+i), n.r.memo[g-i])
                    );
                memor[g] = max;
            }

            // combine
            n.memo = new int[G+1];
            for (int g = 0; g <= G; g++) {
                int max = 0;
                for (int i = 0; i <= g; i++)
                    max = Math.max(
                            max,
                            memol[i] + memor[g-i]
                    );
                n.memo[g] = max;
            }
        }
    }

    static Node initializeTree (String in) {
        try {
            return new Node(Integer.parseInt(in));
        } catch (Exception e) {
            Node root = new Node(-1);
            char[] cs = in.toCharArray();
            int c = 0;
            int m = 0;
            for (int i = 1; i < cs.length-1; i++) {
                if (cs[i] == ' ' && c == 0) {
                    m = i;
                    break;
                } else if (cs[i] == ')') {
                    c--;
                } else if (cs[i] == '(') {
                    c++;
                }
            }

            root.l = initializeTree(in.substring(1, m));
            root.r = initializeTree(in.substring(m+1, in.length()-1));
            return root;
        }
    }

    static class Node {
        int v;
        int[] memo;

        Node l;
        Node r;

        Node(int v) {
            this.v = v;
        }

        @Override
        public String toString() {
            if (v != -1) return Integer.toString(this.v);
            else return "(" + this.l + ", " + this.r + ")";
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
        String nextLine() throws IOException {
            return br.readLine();
        }
    }
}
