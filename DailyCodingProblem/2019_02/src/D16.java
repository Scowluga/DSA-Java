import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*; 

/* --- Problem ---  
 * Topics: Sliding Window Extensions
 * 2019-02-10

https://leetcode.com/problems/subarrays-with-k-different-integers/
https://leetcode.com/problems/minimum-window-substring/
 
 */
 
/* --- Solution: Subarrays with K Different Integers ---
This is solving P4 from yesterday's contest
I didn't actually solve this myself, but looked at a solution for help

And oh my god is it genius:

Generally our sliding window solution can be extended to find subarrays
with strictly LESS than K different integers.

But extending to find exactly K is very difficult. So what do you do?

****** exactly(K) = atMost(K) - atMost(K-1)

This brilliant insight trivializes the problem into sliding window
It can also be extended in the future

Learning:
Whenever we have the phrase "exactly"
we can apply the concept of prefix sum and find with simply 2 calculations

 */


/* --- Solution: Minimum Window Substring
Without as beautiful of an insight, this question is a lot harder (solution in this file)
This is a pretty complicated play on sliding window

A lot more variables, and a lot to do
But it's complete!

This question gives us insight into an extension of sliding
So that's pretty cool


 */

public class D16 {

    static String minWindow(String s, String t) {
        Map<Character, Integer> tCharCount = new HashMap<>();

        for (char c : t.toCharArray())
            tCharCount.put(c, tCharCount.getOrDefault(c, 0) + 1);

        char[] sChars = s.toCharArray();

        int min = sChars.length+1;
        int minLI = 0; // left index of min

        int count = 0;
        Map<Character, Integer> winCharCount = new HashMap<>();

        int left = 0;
        beeg_beeg_yoshi: for (int right = 0; right < sChars.length; right++) {

            if (tCharCount.containsKey(sChars[right]))
                count++;
            winCharCount.put(sChars[right], winCharCount.getOrDefault(sChars[right], 0) + 1);

            while (count >= t.length()) {
                if (tCharCount.containsKey(sChars[left]) && winCharCount.get(sChars[left]) <= tCharCount.get(sChars[left]))
                    break;
                if (tCharCount.containsKey(sChars[left]) && winCharCount.get(sChars[left]) > tCharCount.get(sChars[left]))
                    count--;
                winCharCount.put(sChars[left], winCharCount.get(sChars[left]) - 1);
                left++;
            }

            if (right - left + 1 < min) {
                for (Map.Entry<Character, Integer> entry : tCharCount.entrySet())
                    if (winCharCount.getOrDefault(entry.getKey(), 0) < entry.getValue())
                        continue beeg_beeg_yoshi;

                min = right - left + 1;
                minLI = left;
            }
        }

        if (min == sChars.length+1)
            return "";
        return s.substring(minLI, minLI + min);
    }

    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();
        //        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(minWindow(reader.nextString(), reader.nextString()));
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
