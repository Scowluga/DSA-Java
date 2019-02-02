package _VM7WC_y2016_q2_Silver;

import java.io.*;

/* GG 7/7pt
 * Data structures (Prefix sum array)

>>>FOR SOME REASON, FASTREADER THROWS ARRAY INDEX EXCEPTION


*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // input
        String[] gs = br.readLine().split("");
        int[] psa = new int[gs.length + 1];

        int c = 0;
        for (int i = 0; i < gs.length; i++) {
            psa[i] = c;
            if (gs[i].equals("G")) c++;
        }
        psa[gs.length] = c;

        // output
        int t = Integer.parseInt(br.readLine());
        for (; t-- != 0;) {
            String[] sss = br.readLine().split(" ");
            int s = Integer.parseInt(sss[0]);
            int e = Integer.parseInt(sss[1]);
            System.out.println(psa[e + 1] - psa[s]);
        }
    }

}
