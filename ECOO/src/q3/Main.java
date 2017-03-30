package q3;

import java.io.*;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* Good Luck


 */

public class Main {
    public static BufferedReader br;

    public static List<Integer> heights;
    public static int[] points;

    public static void main(String[] args) {
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\scowluga\\Documents\\GitHub\\DSA-Java\\ECOO\\src\\q3\\DATA31.txt"));

            for (int input = 0; input < 10; input ++) {
                int mountains = Integer.parseInt(br.readLine());
                String[] initInput = br.readLine().split(" ");

                heights = new ArrayList<>(mountains);
                points = new int[mountains];
                heights.add(Integer.parseInt(initInput[0])); // add first one
                points[0] = 1;
                for (int i = 1; i < mountains - 1; i ++) {
                    int mountain = Integer.parseInt(initInput[i]);
                    heights.add(mountain);
                    points[i] = 2;
                }
                heights.add(Integer.parseInt(initInput[mountains - 1]));
                points[mountains - 1] = 1;

                // heights [5, 4, 2]
                // points [1, 2, 1]

                for (int n = 0; n < mountains - 2; n ++) {
                    double highest = findSlope(n, n + 1);
                    for (int m = n + 2; m < mountains; m ++) {
                        double slope = findSlope(n, m);
                        if (slope > highest) {
                            points[n] = points[n] + 1;
                            points[m] = points[m] + 1;
                            highest = slope;
                        }
                    }
                }

                // Find maximum of int array points
                int index = 0;
                int max = points[0];
                for (int i = 1; i < mountains; i ++) {
                    if (points[i] > max) {
                        index = i;
                        max = points[i];
                    }
                }
                System.out.println(index + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double findSlope(int n, int m) {
        double x = m - n;
        double y = heights.get(m) - heights.get(n);

        return y / x;
    }


}