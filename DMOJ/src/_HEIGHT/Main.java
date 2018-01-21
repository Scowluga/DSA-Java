package _HEIGHT;

import java.io.*;

/* HEIGHT 10/10pt
 * DP (Longest Increasing Subsequence)

With a slight twist that it's not length, but value.

*/
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // > Input
        int N = Integer.parseInt(br.readLine().trim());
        int[] ns = new int[N];
        for (int i = 0; i < N; i++) ns[i] = Integer.parseInt(br.readLine().trim());

        // > Creating look-up-table
        int[] memo = new int[N];
        for (int i = 0; i < N; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (ns[j] < ns[i]) max = Math.max(max, memo[j]);
            }
            memo[i] = max + ns[i];
        }

        // > Output
        int max = 0;
        for (int i = 0; i < N; i++) max = Math.max(max, memo[i]);
        System.out.println(max);
    }
}
