package y2016.C6_Mock_CCC_Senior.s3;

import java.io.IOException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/* Good Luck


 */

public class Main {

    static int maxTravelled = 0;

    public static void main(String[] args) {
        try {
            FastReader reader = new FastReader();
            int numHouse = reader.nextInt();

            int houseLoc = reader.nextInt();
            int houseSug = reader.nextInt();

            Fox fox = new Fox(houseSug);

            List<House> houses = new ArrayList<>();
            for (int i = 0; i < (numHouse - 1); i ++) {
                houses.add(new House(reader.nextInt(), reader.nextInt()));
            }

            playGame(fox, houses);

            System.out.println(maxTravelled);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playGame(Fox fox, List<House> houses) {
        boolean done = true;
        for (int i = 0; i < houses.size(); i ++) { // for each house
            House h = houses.get(i);
            if (fox.movableTo(h)) { // if the fox can make it to that house
                done = false; // the game is not over

                // Clone the list of houses, but remove the new house
                List<House> tempH = new ArrayList<>();
                copyHouse(houses, tempH);
                tempH.remove(i);

                // Create fox after travelling to that house
                Fox tempF = fox.travelTo(h);

                // Recursive call with new map (minus the house travelled to) and new fox
                playGame(tempF, tempH);
            }
        }
        if (done) { // if there are no more houses to travel to
            if ((fox.travelled + fox.currentSugar) > maxTravelled) { // if travelled distance greater than current max
                maxTravelled = fox.travelled + fox.currentSugar; // replace
            }
        }
    }

    public static void copyHouse(List<House> start, List<House> end) {
        for (House h : start) {
            end.add(new House(h.x, h.sugar));
        }
    }


    public static class Fox {

        int currentX; // current x location
        int currentSugar; // current amount of sugar remaining
        int travelled; // distance travelled

        public Fox (int sugar) {
            this.currentX = 0;
            this.currentSugar = sugar;
            this.travelled = 0;
        }

        public Fox (int currentX, int sugar, int travelled) {
            this.currentX = currentX;
            this.currentSugar = sugar;
            this.travelled = travelled;
        }

        public Fox travelTo (House h) { // return new Fox after travelling to that house and eating the pie
            int distance = Math.abs(h.x - this.currentX);
            return new Fox(h.x, // new x location of fox
                    this.currentSugar - distance + h.sugar, // current sugar, deplete by journey, replenish at house
                    this.travelled + distance); // current distance + distance about to travel
        }

        public boolean movableTo (House h) { // if Fox has enough sugar to move to new house
            int distance = Math.abs(h.x - this.currentX);
            return currentSugar >= distance; // if more sugar that required movement
        }

        @Override
        public String toString() {
            return "X: " + this.currentX + " with " + this.currentSugar + " sugar and " + this.travelled + " travelled.";
        }
    }

    public static class House implements Comparable {

        int x;
        int sugar;

        public House(int x, int sugar) {
            this.x = x;
            this.sugar = sugar;
        }

        @Override
        public String toString() {
            return "X: " + this.x + " sugar: " + this.sugar;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof House) {
                House h = (House)o;
                return this.x - h.x;
            }
            return 0;
        }
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


