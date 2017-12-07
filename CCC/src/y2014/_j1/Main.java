package y2014._j1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* Triangle Times 15/15
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int s1 = Integer.parseInt(in.readLine());
            int s2 = Integer.parseInt(in.readLine());
            int s3 = Integer.parseInt(in.readLine());
            if (s1 + s2 + s3 != 180) {
                System.out.println("Error");
            } else if (s1 == s2 && s2 == s3) {
                System.out.println("Equilateral");
            } else if (s1 != s2 && s2 != s3 && s1 != s3) {
                System.out.println("Scalene");
            } else {
                System.out.println("Isosceles");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

