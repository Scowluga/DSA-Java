package y2016.j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
Through the first line, creates 'sum' as the sum of one line.
Finishes checking horizontally as the remaining 3 lines are read in.
Finally, checks vertically through nested for loop.
15/15
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(answer() ? "magic" : "not magic");
    }

    private static boolean answer() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int[][] square = new int[4][4]; // Total magic square
            int sum = 0; // Sum of each line

            String[] string = in.readLine().split(" "); // First line
            for (int m = 0; m < 4; m ++) {
                int numb = Integer.parseInt(string[m]);
                square[0][m] = numb;
                sum += numb;
            }

            for (int i = 1; i < 4; i ++) { // For the other 3 lines
                String[] strings = in.readLine().split(" ");
                int lineSum = 0;
                for (int m = 0; m < 4; m ++) {
                    int num = Integer.parseInt(strings[m]);
                    square[i][m] = num;
                    lineSum += num;
                }
                if (lineSum != sum) {
                    return false;
                }
            }
            // Reading in each of the lines, if the horizontal sum is incorrect, false is automatically returned.
            // Now check for vertical sums

            for (int i = 0; i < 4; i ++) {
                int colSum = 0;
                for (int m = 0; m < 4; m ++) {
                    colSum += square[i][m];
                }
                if (colSum != sum) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
