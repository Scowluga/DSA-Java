import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*; 

/* --- Problem ---  
 * Topics: Data Structures (Trie) 
 * 2019-02-06

Implement an autocomplete system.
That is, given a query string s and a set of all possible query strings,
return all strings in the set that have s as a prefix.

For example, given the query string "de"
and the set of strings [dog, deer, deal],
return [deer, deal].

Hint: Try preprocessing the dictionary into a more efficient
data structure to speed up queries.
 
 */
 
/* --- Solution ---  

Huh so it appears like I'll be building a Trie today for the first time
Never implemented a Trie before, so let's see how that goes!

Trie Complexity: O(k) where k is the length of the string

Well that was a fun exercise!
 
 */

public class P11 {


    static class Trie {
        Node head;

        Trie() {
            this.head = new Node('*');
        }

        void add(String word) {
            Node curr = head;
            char[] chars = word.toCharArray();
            for (Character c : chars) {
                if (!curr.map.keySet().contains(c))
                    curr.map.put(c, new Node(c));
                curr = curr.map.get(c);
            }
            curr.isComplete = true;
        }

        List<String> check(String prefix) {
            return head.check(
                    prefix,
                    prefix.substring(0, prefix.length()-1)
            );
        }

    }

    static class Node {
        Character c;
        Map<Character, Node> map;
        boolean isComplete;

        Node(Character c) {
            this.c = c;
            this.isComplete = false;
            this.map = new HashMap<>();
        }

        List<String> check(String prefix, String currentWord) {
            Node curr = this;
            char[] chars = prefix.toCharArray();
            for (Character c : chars) {
                if (!curr.map.keySet().contains(c))
                    return new ArrayList<>();
                curr = curr.map.get(c);
            }

            List<String> completed = new ArrayList<>();
            if (curr.isComplete)
                completed.add(currentWord + curr.c);

            for (Map.Entry<Character, Node> entry : curr.map.entrySet())
                completed.addAll(entry.getValue().check("", currentWord + curr.c));

            return completed;
        }

        @Override
        public String toString() {
            return String.format(
                    "Node{Character: '%s', isComplete = %s}", String.valueOf(this.c), this.isComplete ? "True" : "False"
            );
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();
        Trie trie = new Trie();

        while (true) {
            String type = reader.nextString();
            if (type.toLowerCase().equals("add")) {
                trie.add(reader.nextString());
            } else if (type.toLowerCase().equals("check")) {
                List<String> output = trie.check(reader.nextString());

                for (String s : output)
                    System.out.println(s);
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
