package Utilities;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*

*/
public class OtherLibrary {

    public static void main(String[] args) throws IOException {


    }


    static void output(Object[][] array) {
        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                System.out.println(array[x][y]);
            }
        }
    }


    static void move() {
        /* move[0:x, 1:y][0:d, 1:r, 2:u, 3:l]
        - directions:
          2
        3 * 1
          0
        - access
        r 2 2 2
        r 1 1 2
        r 0 1 2
          c c c
        */

        int[][] move = new int[][]{
                {0, 1, 0, -1}, // x
                {-1, 0, 1, 0} // y
        };

        int x = 0, y = 0;

        for (int d = 0; d < 4; d++) {
            x += move[0][d];
            y += move[1][d];
        }
    }

    static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < (n / 2); ++i) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }


    static <T> T min(T... args) {
        Arrays.sort(args);
        return args[0];
    }

}
