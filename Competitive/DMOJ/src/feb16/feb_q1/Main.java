package feb16.feb_q1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/* Good Luck
40/100 at best

 */

public class Main {
    public static void main(String[] args) {
        try {
            Reader.init(System.in);

            int numBatch = Reader.nextInt(); // number
            Set<Batch> batches = new HashSet<>(numBatch); // list
            int count = 0; // current count

            int temp;

            for (int i = 0; i < numBatch; i ++) { // for each incoming batch
                String[] info = Reader.nextLine().split(" ");
                temp = Integer.parseInt(info[2]);
                batches.add(new Batch(
                        Integer.parseInt(info[0]),
                        Integer.parseInt(info[1]),
                        temp));
                count += temp;
            }

            int numFail = Reader.nextInt();
            Set<Batch> toBeRemoved;

            for (int i = 0; i < numFail; i ++) { // for each incoming failed case
                temp = Reader.nextInt();
                toBeRemoved = new HashSet<>();
                for (Batch b : batches) {
                    if (b.hasIn(temp)) {
                        toBeRemoved.add(b);
                        count -= b.value;
                    }
                }
                batches.removeAll(toBeRemoved);
            }
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Batch {

        public int start;
        public int end;
        public int value;

        public Batch (int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }

        public boolean hasIn(int x) {
            return x >= start && x <= end;
        }

        @Override
        public String toString() {
            return "Start: " + start + " end: " + end + " with value: " + value;
        }

        @Override
        public int hashCode() {
            return this.start * 1000 + this.end;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Batch)) {
                return false;
            }
            Batch rhs = (Batch)obj;
            return this.start == rhs.start
                    && this.end == rhs.end
                    && this.value == rhs.value;
        }
    }

    static class Reader {

        static BufferedReader reader;

        static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader((new InputStreamReader(input)));
            tokenizer = new StringTokenizer("");
        }

        static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        static String nextLine() throws IOException {
            return reader.readLine();
        }

        static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

    }
}



