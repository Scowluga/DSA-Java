package _y2016_r2_p1;

import java.io.*;

/* Palindrome Panic 12/12pt
 * Really simple stuff

Palindromes!
*/
public class Main {
    static char[] cs;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int T = 0; T < 10; T++) {
            cs = br.readLine().toCharArray();
            for (int n = cs.length - 1; n >= 0; n--) {
                if (p(0, 0 + n) || p(cs.length - n - 1, cs.length - 1)) {
                    System.out.println(cs.length - n - 1);
                    break;
                }
            }
        }
    }

    static boolean p(int si, int ei) {
        while (ei > si)
            if (cs[ei--] != cs[si++])
                return false;
        return true;
    }
}
