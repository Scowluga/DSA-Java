package y2016._j1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* Tournament Selection 15/15
Logic
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int wins = 0;
            for (int i = 0; i < 6; i++) {
                String line = in.readLine();
                if (line.equals("W")) {
                    wins += 1;
                }
            }
            if (wins >= 5) {
                System.out.println("1");
            } else if (wins >= 3) {
                System.out.println("2");
            } else if (wins >= 1) {
                System.out.println("3");
            } else {
                System.out.println("-1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
