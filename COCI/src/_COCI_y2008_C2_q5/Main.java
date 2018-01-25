package _COCI_y2008_C2_q5;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/* Setnja 7/7pt
 * DP (Non-classical)

When looking for sub-problems, we have to keep in mind a relationship between them.
They have to relate together to solve the problem.

For this question, we understand that the root value can change, which then makes
this problem recursive.

f(i, n) =
	L: f(i+1, 2n);
	R: f(i+1, 2n + 1);
    P: f(i+1, n);

base: f(last, n) = n

To approach with recursion, however, is inefficient. Instead, we build a
look-up-table only saving two values that can model the linear function.

This 'look-up-table' (or look-up-BigIntegers)
is substantially faster, and thus passes.

From COCI Solution Online
*/
public class Main {

    static String[] moves;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        moves = st.nextToken().split("");

        BigInteger A1 = BigInteger.ONE;
        BigInteger B1 = BigInteger.ZERO;

        BigInteger A2, B2;
        for (int i = moves.length - 1; i >= 0; i--) {
            A2 = BigInteger.ZERO; B2 = BigInteger.ZERO;

            if (moves[i].equals("*") || moves[i].equals("P")) {
                A2 = A2.add(A1);
                B2 = B2.add(B1);
            }

            if (moves[i].equals("*") || moves[i].equals("L")) {
                A2 = A2.add(A1).add(A1);
                B2 = B2.add(B1);
            }

            if (moves[i].equals("*") || moves[i].equals("R")) {
                A2 = A2.add(A1).add(A1);
                B2 = B2.add(A1).add(B1);
            }

            A1 = A2; B1 = B2;
        }

        System.out.println(A1.add(B1));
    }


    // recursion (inefficient)
    // dp(0, BigInteger.ONE));
    static BigInteger dp(int index, BigInteger value) {
        if (index >= moves.length) return value;

        BigInteger ret = BigInteger.ZERO;

        if (moves[index].equals("*") || moves[index].equals("P")) {
            ret = ret.add(dp(index + 1, value));
        }

        if (moves[index].equals("*") || moves[index].equals("L")) {
            ret = ret.add(dp(index + 1, value.add(value)));
        }

        if (moves[index].equals("*") || moves[index].equals("R")) {
            ret = ret.add(dp(index + 1, value.add(value).add(BigInteger.ONE)));
        }

        return ret;
    }
}
