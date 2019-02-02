package _y2011._s4;


import java.io.IOException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Blood Distribution 15/15
The thing about blood types, is they can be ranked in accordance to rarity both for units, and patients.
The algorithms swaps units blood -> patients depending on rarity. Rarest patients go first (O-) and for each patient
rarest unit goes first (O-)

simple logic with swapping

Revisiting Dec. 6, 2017: I have no idea how I solved this last time :O
 */

public class Main {


    static List<Integer> info = new ArrayList<>(Arrays.asList( // each pair of blood to be swapped
            // Initial Swap
            0, 0, // O- O-
            1, 1, // O+ O+
            0, 1, // O- O+
            2, 2, // A- A-
            3, 3, // A+ A+
            2, 3, // A- A+
            4, 4, // B- B-
            5, 5, // B+ B+
            4, 5, // B- B+
            // More precise swap
            0, 2, // O- A-
            0, 4, // O- B-
            1, 3, // O+ A+
            1, 5, // O+ B+
            0, 3, // O- A+
            0, 5  // O- A+
            // At this point, all patients of O A B are done receiving in order of rarity from units.
            // Now calculations for AB begin
    ));

    static int[] units;
    static int[] patients;
    static int count;

    public static void main(String[] args) {
        try {
            FastReader reader = new FastReader();
            
            // Initialization
            units = reader.readLineAsIntArray(8);
            patients = reader.readLineAsIntArray(8);
            count = 0;

            // For each pair of swaps to be made
            for (int i = 0; i < info.size(); i += 2) {
                int unit = info.get(i);
                int patient = info.get(i + 1);
                swap(unit, patient);
            }

            // Final Logic regarding AB
            int negUnits = units[0] + units[2] + units[4] + units[6];
            int posUnits = units[1] + units[3] + units[5] + units[7];
            int negPats = patients[6];
            int posPats = patients[7];

            units[0] = negUnits;
            units[1] = posUnits;
            patients[0] = negPats;
            patients[1] = posPats;

            swap(0, 0);
            swap(1, 1);
            swap(0, 1);

            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void swap(int unit, int patient) {
        int unitNum = units[unit];
        int patNum = patients[patient];

        if (patNum == 0) { // no more patients needed
            // do nothing
        } else if (unitNum == 0) { // no more units to give
            // do nothing
        } else { // neither are 0
            if (unitNum > patNum) { // more available units
                count += patNum; // all patients are saved
                units[unit] -= patNum; // subtract patients from available units
                patients[patient] = 0;
            } else if (patNum > unitNum) { // more patients than units
                count += unitNum;
                units[unit] = 0;
                patients[patient] -= unitNum;
            } else { // same number above 0
                count += unitNum;
                units[unit] = 0;
                patients[patient] = 0;
            }
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
            byte[] buf = new byte[64]; // line length
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


