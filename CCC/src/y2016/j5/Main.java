package y2016.j5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/*
Regardless of type 1 or type 2, read in 2 arrays of people and sort.
If it is type 1, pair up by index (slowest goes with slowest)
if it is type 2, pair up reversed (slowest goes with fastest)
15/15
 */
public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String type = in.readLine();
            int num = Integer.parseInt(in.readLine());
            String[] dmojIn = in.readLine().split(" ");
            String[] pegIn = in.readLine().split(" ");
            int[] dmoj = new int[num];
            Integer [] peg = new Integer[num];
            for (int i = 0; i < num; i ++) {
                dmoj[i] = Integer.parseInt(dmojIn[i]);
                peg[i] = Integer.parseInt(pegIn[i]);
            }
            Arrays.sort(dmoj);
            Arrays.sort(peg);
            int totalSpeed = 0;
            if (type.equals("1")) { // Reverse the second list
                for (int i = 0; i < num; i ++) {
                    totalSpeed += Math.max(dmoj[i], peg[i]);
                }
                System.out.println(totalSpeed);
            } else {
                for (int i = 0; i < num; i ++) {
                    totalSpeed += Math.max(dmoj[i], peg[num- 1 - i]);
                }
                System.out.println(totalSpeed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
