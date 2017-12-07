package y2016._j3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* Hidden Palindrome 15/15
Logic
 */
public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String word = in.readLine();
            int length = word.length();
            int longest = 1;
            for (int start = 0; start < length; start ++) {
                for (int end = start + 1; end <= length; end ++) {
                    String sub = word.substring(start, end);
                    if (sub.length() > longest && isPalindrome(sub)) {
                        longest = sub.length();
                    }
                }
            }
            System.out.println(longest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < (n/2); ++i) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
