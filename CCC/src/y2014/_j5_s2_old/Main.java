package y2014._j5_s2_old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/* Good Luck


 */

public class Main {
    public static void main(String[] args) {
        try {
            Reader.init(System.in);

            int[] integers = new int[10];
            Arrays.fill(integers, 5);
            String infoString = Reader.nextLine();
            List<String> info = new ArrayList<>(10);
            info.addAll(Arrays.asList(infoString.split(" ")));

        } catch (Exception e) {
            e.printStackTrace();
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


