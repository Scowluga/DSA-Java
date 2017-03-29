package y2013.j1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/* Good Luck


 */

public class Main {
    public static void main(String[] args) {
        try {
            Reader.init(System.in);
            int one = Reader.nextInt();
            int two = Reader.nextInt();
            System.out.println(two + (two - one));

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


