package y2014.j3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 
Simple loop
15/15
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int rounds = Integer.parseInt(in.readLine());
            int p1 = 100;
            int p2 = 100;
            for (int i = 0; i < rounds; i ++) {
                String[] results = in.readLine().split(" ");
                int first = Integer.parseInt(results[0]);
                int second = Integer.parseInt(results[1]);
                if (!(first == second)) {
                    if (first > second) {
                        p2 -= first;
                    } else {
                        p1 -= second;
                    }

                }
            }
            System.out.println(p1 + "\n" + p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

