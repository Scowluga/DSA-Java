package y2014.j2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 
Simple counting.
15/15
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int votes = Integer.parseInt(in.readLine());
            String results = in.readLine();
            int voteA = 0;
            for (int i = 0; i < votes; i ++) {
                char c = results.charAt(i);
                if (c == 'A') {
                    voteA += 1;
                } else {
                    voteA -= 1;
                }
            }
            if (voteA == 0) {
                System.out.println("Tie");
            } else {
                System.out.println(voteA > 0 ? "A" : "B");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

