package y2013._j5_s3;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/* Chances of Winning 15/15

 */
public class Main {


    public static void main (String[] args) {
        try {
            // 3, 0; 1, 1; 0, 3
            int[][] gameResults = {{3, 0}, {1, 1}, {0, 3}};
            // the list of single integers representing each 6 games
            // each integer is the product of the two team numbers
            // this is unique to each game
            List<Integer> gameIDs = new ArrayList<>(Arrays.asList(
                    2, 3, 4, 6, 8, 12
            ));;
            // a map from games to the pair of teams
            Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> IDMap = new HashMap<Integer, AbstractMap.SimpleEntry<Integer, Integer>>(6) {{
                put(2, new SimpleEntry<Integer, Integer>(1, 2));
                put(3, new SimpleEntry<Integer, Integer>(1, 3));
                put(4, new SimpleEntry<Integer, Integer>(1, 4));
                put(6, new SimpleEntry<Integer, Integer>(2, 3));
                put(8, new SimpleEntry<Integer, Integer>(2, 4));
                put(12, new SimpleEntry<Integer, Integer>(3, 4));
            }};

            // scores of the 4 teams after initial input
            int[] baseScores = new int[4];


            FastReader reader = new FastReader();

            int team = reader.nextInt();
            int gamesPlayed = reader.nextInt();

            for (int i = 0; i < gamesPlayed; i ++) {
                int[] input = reader.readLineAsIntArray(4);
                int difference = input[2] - input[3];
                if (difference > 0) {
                    baseScores[input[0] - 1] += 3;
                } else if (difference < 0) {
                    baseScores[input[1] - 1] += 3;
                } else {
                    baseScores[input[0] - 1] += 1;
                    baseScores[input[1] - 1] += 1;
                }
                gameIDs.remove(Integer.valueOf(input[0] * input[1]));
            }

            /**
             * All games played have been read in and the scores are in scores
             * Loop through all remaining games in games, map through teamMapping
             * Then add to scores and check if team has highest points
             */

            // all cases of all int[4] arrays
            List<int[]> gameStates = new LinkedList<>();
            gameStates.add(baseScores);

            int size;
            int[] tempArray;

            for (int g = 0; g < gameIDs.size() - 1; g++ ) {
                int game = gameIDs.get(g);
                AbstractMap.SimpleEntry<Integer, Integer> entry = IDMap.get(game);
                size = gameStates.size();
                for (int s = 0; s < size; s++ ) {
                    int[] item = gameStates.get(s);
                    for (int i = 0; i < 3; i ++) {
                        tempArray = item.clone();
                        tempArray[entry.getKey() - 1] += gameResults[i][0];
                        tempArray[entry.getValue() - 1] += gameResults[i][1];
                        gameStates.add(tempArray);
                    }
                }
                // Reset gameStates so the states before aren't retained
                gameStates = gameStates.subList(size, gameStates.size());
            }

            // Loop through on the last game, but instead of appending to list, check if won;

            int wins = 0;
            int game = gameIDs.get(gameIDs.size() - 1);
            AbstractMap.SimpleEntry<Integer, Integer> entry = IDMap.get(game);

            for (int[] item : gameStates) {
                for (int i = 0; i < 3; i ++) {
                    tempArray = item.clone();
                    tempArray[entry.getKey() - 1] += gameResults[i][0];
                    tempArray[entry.getValue() - 1] += gameResults[i][1];
                    wins += checkWin(team, tempArray);
                }
            }


            System.out.println(wins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int checkWin(int team, int[] tempArray) {
        for (int i = 0; i < 4; i ++) {
            if (!(i == team - 1)) { // not the team itself
                if (tempArray[team - 1] <= tempArray[i]) {
                    return 0;
                }
            }
        }

        return 1;
    }


    public static class FastReader {

        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream din;

        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastReader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[6400]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public List<Integer> readLineAsIntegers() throws IOException {
//        int[] ret = new int[1024];
            List<Integer> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                int i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');
//            ret[idx++] = (negative) ? -i : i;
                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public List<Long> readLineAsLongs() throws IOException {
            List<Long> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                long i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');
                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public List<Double> readLineAsDoubles() throws IOException {
            List<Double> ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                double d = 0, div = 1;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    d = d * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');

                if (c == '.') {
                    while ((c = read()) >= '0' && c <= '9') {
                        d += (c - '0') / (div *= 10);
                    }
                }
                ret.add((negative) ? -d : d);
            }
            return ret;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            return (neg) ? -ret : ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            return (neg) ? -ret : ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            return (neg) ? -ret : ret;
        }

        public String nextString() throws IOException {
            byte[] ret = new byte[1024];
            int idx = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            do {
                ret[idx++] = c;
                c = read();
            } while (c != -1 && c != ' ' && c != '\n' && c != '\r');
            return new String(ret, 0, idx);

        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                // fill buffer
                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1) {
                    buffer[0] = -1;
                }
            }
            return buffer[bufferPointer++];
        }

        public int[] readLineAsIntArray(int n) throws IOException {
            int[] ret = new int[n];
//            int ret = new ArrayList<>();
            int idx = 0;
            byte c = read();
            while (c != -1) {
                if (c == '\n' || c == '\r')
                    break;

                // next integer
                int i = 0;
                while (c <= ' ') {
                    c = read();
                }
                boolean negative = (c == '-');
                if (negative) {
                    c = read();
                }

                do {
                    i = i * 10 + (c - '0');
                    c = read();
                } while (c >= '0' && c <= '9');

                ret[idx++] = (negative) ? -i : i;
                if (idx >= n) {
                    break;
                }

//                ret.add((negative) ? -i : i);
            }
            return ret;
        }

        public void close() throws IOException {
            if (din != null) {
                din.close();
            }
        }

    }
}
