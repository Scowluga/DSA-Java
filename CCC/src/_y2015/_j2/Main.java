package _y2015._j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* Happy or Sad 15/15
 */
public class Main {

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String total = in.readLine();
            int happy = 0;
            int sad = 0;
            for (int i = 0; i < (total.length()-2); i ++) {
                if (total.substring(i, i + 3).equals(":-)")) {
                    happy += 1;
                } else if (total.substring(i, i + 3).equals(":-(")) {
                    sad += 1;
                }
            }
            if (happy > sad) {
                System.out.println("happy");
            } else if (sad > happy) {
                System.out.println("sad");
            } else if (sad == 0 && happy == 0) {
                System.out.println("none");
            } else {
                System.out.println("unsure");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
