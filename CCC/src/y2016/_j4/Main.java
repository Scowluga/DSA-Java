package y2016._j4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/* Arrival Time 15/15
Use custom time object called JTIME
Continuously decrease a counter called timeLeft
Output with Java Date object.
 */
public class Main {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    public static void main(String[] args) {
        try {
            int timeLeft = 12; // 'ticks' to get to work. Rush hour ticks 1, normal driving ticks 2.
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] startTime = in.readLine().split(":");

            JTIME total = new JTIME(Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]));

            while (true) { // While Fiona has not yet reached work.
                if (timeLeft > 1 || (total.taken() == 1 && timeLeft == 1)) { // Regular. Just move
                    timeLeft -= total.taken();
                    total.nextTime();
                } else if (timeLeft == 1) { // Only 1 counter left, and out of rush hour
                    total.nextHalfTime();
                    break;
                } else { // timeLeft == 0
                    break;
                }
            }

            Date date = format.parse(total.hour + ":" + total.minute);
            System.out.println(format.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class JTIME {
        int hour;
        int minute;
        public JTIME (int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
        }

        public int taken() { // if rush hour, return 1. Else return 2
            int hour = this.hour;
            if ((hour >= 7 && hour < 10) || (hour >= 15 && hour < 19)) {
                return 1;
            } else {
                return 2;
            }
        }

        public void nextTime() { // Move to the next time
            if (this.minute == 40) {
                if (this.hour == 23) {
                    this.hour = 0;
                } else {
                    this.hour += 1;
                }
                this.minute = 0;
            } else {
                this.minute += 20;
            }
        }
        public void nextHalfTime() { // If there is only 1 timeLeft, but no longer in rush hour.
            this.minute += 10;
        }
        @Override
        public String toString() {
            return "Current time: " + hour + ":" + minute;
        }
    }
}
