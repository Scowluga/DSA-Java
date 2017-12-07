package y2015._j4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/* Wait Time 15/15
Friends stored in TreeMap<Their number, their wait time>
If they have sent a message, that has not yet been responded to, their wait time is a positive incrementing number.
Upon receiving a reply, their wait time is multiplied by -1, and stops incrementing.
At the end, only negative wait times are displayed (have been replied to).
 */
public class Main {

    private static Map<Integer, Integer> storage;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            storage = new TreeMap<>(); // Storage of friends
            int inputs = Integer.parseInt(in.readLine());
            boolean isW = false; // For the incrementing algorithm. If last entry was a wait.
            for (int i = 0; i < inputs; i ++) { // For all inputs
                String[] message = in.readLine().split(" ");
                String type = message[0];
                int num = Integer.parseInt(message[1]);
                switch(type) {
                    case "R":
                        // If exists, find it's current wait time and make it positive again (so it can increment)
                        // If it doesn't exist, start it at 1
                        int value = storage.containsKey(num) ? storage.get(num) : 0;
                        storage.put(num, value * -1);
                        if (isW) {
                            isW = false;
                        }
                        incrementMap(1);
                        break;
                    case "W":
                        incrementMap(num - 1);
                        isW = true;
                        break;
                    case "S":
                        storage.put(num, storage.get(num) * -1);
                        if (isW) {
                            isW = false;
                        }
                        incrementMap(1);
                        break;

                }
            }
            if (!isW) { // If the last entry was not wait, then we have incremented an extra number.
                incrementMap(-1);
            }
            for (int key : storage.keySet()) {
                Integer val = storage.get(key);
                if (val < 0) { // Response sent
                    System.out.println(key + " " + val * -1);
                } else { // val >= 0. Incrementing, thus not replied to
                    System.out.println(key + " -1");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void incrementMap(Integer amount) {
        for(Map.Entry<Integer, Integer> entry : storage.entrySet()) {
            Integer key = entry.getKey();
            Integer val = entry.getValue();
            if (val >= 0) { // Only if the current value is positive, it is incrementing (thus waiting)
                storage.put(key, val + amount);
            }
        }
    }
}

