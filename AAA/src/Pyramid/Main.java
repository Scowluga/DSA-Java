package Pyramid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.println("What is the height of the pyramid?");
            System.out.print("> ");

            printPyramid(Integer.parseInt(br.readLine()));
        }

    }

    static void printPyramid(int height)
    {
        int width = (height - 1) * 2;

        for (int dashNum = 0; dashNum < height; dashNum++)
        {
            int slashNum = (width - (2 * dashNum)) / 2;

            System.out.printf("%s%s%s%s\n",
                    repeat("*", slashNum * 4),
                    repeat("/", dashNum * 4),
                    repeat("\\", dashNum * 4),
                    repeat("*", slashNum * 4));
        }

        System.out.println("");
    }

    // Repeats string s, n times
    static String repeat(String s, int n)
    {
        String r = "";
        for (int i = 0; i < n; i++)
        {
            r += s;
        }
        return r;
    }
}