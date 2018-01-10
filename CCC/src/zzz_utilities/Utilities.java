package zzz_utilities;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* 

*/
public class Utilities {

    public static void main(String[] args) throws IOException {



    }

    public static void output(Object[][] array) {

        // format the table by largest length?

        for (int y = 0; y < array[0].length; y++) {
            for (int x = 0; x < array.length; x++) {
                System.out.print(array[x][y] + "");
            }
            System.out.println("");
        }
    }
}
