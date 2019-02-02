package SUNNYWAANG;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

/* SUNNY WANG

*/
public class Main {


    public static void main(String[] args) throws IOException {

        BigInteger n = new BigInteger("2018");
        int d1 = 1;

        while (d1 < 1000) {
            d1++;
//            System.out.println(d1);
            for (int d0 = 1; d0 <= d1; d0++) {
                StringBuffer outputBuffer = new StringBuffer(d1 + d0);
                for (int i = 0; i < d1; i++)
                    outputBuffer.append('1');

                for (int i = 0; i < d0; i++)
                    outputBuffer.append('0');

                BigInteger i = new BigInteger(outputBuffer.toString());
//                System.out.println(i);
                if (i.mod(n) == BigInteger.ZERO) {
                    System.out.println(i);
//                    return;
                }
            }
        }
    }
}
