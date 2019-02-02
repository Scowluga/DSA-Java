package boardwide.q0;

import java.io.*;

/* Good Luck


 */

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\scowluga\\Documents\\GitHub\\DSA-Java\\ECOO\\src\\boardwide.q0\\DATA11.txt"));

            String[] info;
            String correct, wrong;
            int points;
            for (int i = 0; i < 5; i ++) {
                info = br.readLine().split(" ");
                points = 0;
                correct = info[0];
                wrong = info[1];
                for (int n = 0; n < correct.length(); n ++ ){
                    if (correct.substring(n, n + 1).equals(wrong.substring(n, n + 1))) {
                        points += 1;
                    }
                }
                System.out.println(points * 5);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}