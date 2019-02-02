package GoogleCodeJam2018.p3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* Go. Gopher!

Not sure how to solve batch 2, but this is brute force for batch 1

*/
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        gl: for (int TT = 1; TT <= T; TT++) {
            int A = Integer.parseInt(br.readLine());
            boolean[][] map = new boolean[9][5];

            int x;
            int y;

            while (!(map[5][2] && map[5][3] && map[5][4])) {
                System.out.println("5 3");
//                System.out.flush();

                st = new StringTokenizer(br.readLine());
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                if (x == 0 && y == 0)
                    continue gl;
                map[x][y] = true;
            }

            while (!(
                    map[2][2] && map[2][3] && map[2][4]
                            && map[3][2] && map[3][3] && map[3][4]
                            && map[4][2] && map[4][3] && map[4][4]
            )) {
                System.out.println("3 3");
//                System.out.flush();

                st = new StringTokenizer(br.readLine());
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                if (x == 0 && y == 0)
                    continue gl;
                map[x][y] = true;
            }

            while (!(
                    map[6][2] && map[6][3] && map[6][4]
                            && map[7][2] && map[7][3] && map[7][4]
                            && map[8][2] && map[8][3] && map[8][4]
            )) {
                System.out.println("7 3");
//                System .out.flush();

                st = new StringTokenizer(br.readLine());
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                if (x == 0 && y == 0)
                    continue gl;
                map[x][y] = true;
            }
        }
    }
}
