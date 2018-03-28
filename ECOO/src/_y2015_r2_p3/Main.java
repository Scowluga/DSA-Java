package _y2015_r2_p3;

import java.io.*;
import java.util.StringTokenizer;

/* Lucas' Other Tower

Lol just 2N - 1 i love it

*/
public class Main {

    public static void main(String[] args) throws IOException {
//        FastReader reader = new FastReader("C:\\Users\\david\\Documents\\Programming\\Java\\DSA-Java\\AAA\\src\\y2015_r2_p3\\DATA11.txt.txt");
        FastReader reader = new FastReader();
        for (int TT = 0; TT < 10; TT++) {
            int N = reader.nextInt();
            int T = reader.nextInt();

            System.out.println(2 * N - 1);



        }
    }



    static class FastReader {

        BufferedReader br;
        StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        FastReader(String filename) throws FileNotFoundException {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
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
