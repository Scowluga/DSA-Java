import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*; 

/* --- Problem ---  
 * Topics: DP
 * 2019-01-31

Was XOR linked list. I read up on it, cool data structure, really great to know.
But I'm not going to implement it in Java. Too many downsides.


Instead: Let's do a classic:
https://leetcode.com/problems/maximum-product-subarray/

Given an integer array nums, find the contiguous subarray within an array
(containing at least one number) which has the largest product.

Ooh this is fun :)

 
 */
 
/* --- Solution ---  
Definitely DP. 100% DP. I've probably even done this before but don't remember.

Naive is you loop through every possible start and stop index
O(n^2) time with O(1) space. Not bad

But we can optimize maybe?

Immediately, 2 big points come to mind.
1. Negative numbers. Any 2 become positive again
2. Zero. Automatically 0'ifies the product. Avoid.

Now for recurrence.

So it looks like we need to store 2 values here
Maximum positive number, and maximum negative number

With recurrence, this can easily be made into
O(n) time with O(n) space

Can we do better?

Probably not tbh. Let's just code it :)


Learning
> Don't forget how recurrence works
> Or how you actually do dp problems lol

 
 */

public class D6 {

    static int maxProduct(int[] nums) {
        // Edge Case
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        // Create memo
        int[] maxP = new int[nums.length];
        int[] maxN = new int[nums.length];

        // Init memo
        maxP[0] = Math.max(0, nums[0]);
        maxN[0] = Math.min(0, nums[0]);

        // Fill memo
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                maxP[i] = 0;
                maxN[i] = 0;
            }

            if (nums[i] > 0) {
                if (maxP[i-1] == 0)
                    maxP[i] = nums[i];
                else
                    maxP[i] = maxP[i-1] * nums[i];

                maxN[i] = maxN[i-1] * nums[i];

            }

            if (nums[i] < 0) {
                maxP[i] = maxN[i-1] * nums[i];

                if (maxP[i-1] == 0)
                    maxN[i] = nums[i];
                else
                    maxN[i] = maxP[i-1] * nums[i];
            }

        }

        // Search memo
        int max = nums[0];
        for (int val : maxP)
            max = Math.max(max, val);
        return max;
    }


    public static void main(String[] args) throws IOException {
        FastReader reader = new FastReader();

        while (true) {
            int n = reader.nextInt();
            int[] arr = reader.readLineAsIntArray(n, false);
            System.out.println(maxProduct(arr));
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
