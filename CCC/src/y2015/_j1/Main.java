package y2015._j1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* Special Day 15/15
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int month = Integer.parseInt(in.readLine());
            int day = Integer.parseInt(in.readLine());
            if (month > 2 || (month == 2 && day > 18)) {
                System.out.println("After");
            } else if (month == 1 || day < 18) {
                System.out.println("Before");
            } else {
                System.out.println("Special");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
