package y2012.j5_s4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/* A Coin Game


 */

public class Main {
    public static void main(String[] args) {
//        List<Integer> beluga = new ArrayList<>(Arrays.asList(1, 2, 3));
//        List<Integer> melon = new ArrayList<>(beluga);
//        System.out.println("Before change");
//        System.out.println(beluga);
//        System.out.println(melon);
//        System.out.println("Change first list");
//        beluga.add(4);

        try {
            Reader.init(System.in);
            while(true) {
                int coins = Reader.nextInt();
                if (coins == 0) { // Exit
                    break;
                } else if (coins == 1) { // 1 coin. Always in ascending
                    System.out.println("0");
                    break;
                }
                String[] inputs = Reader.nextLine().split(" ");
                System.out.println(playGame(coins, inputs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<List<Stack<Integer>>, Integer> visited;
    private static LinkedList<List<Stack<Integer>>> positions; // or PriorityQueue

    private static int number;

    private static String playGame(int num, String[] inputs) {

        visited = new HashMap<>();
        positions = new LinkedList<>();
        number = num;

        List<Stack<Integer>> current = new ArrayList<>(num + 1);

        Stack<Integer> tempStack;
        for (int i = 0; i < num; i ++) {
            tempStack = new Stack<>();
            tempStack.push(Integer.parseInt(inputs[i]));
            current.add(tempStack);
        }
        visited.put(current, 0);

        tempStack = new Stack<>();
        tempStack.push(0);
        current.add(tempStack);

        positions.addFirst(current);

        if (isFinal(current)) {
            return "0";
        }
        Integer y;
        while (positions.size() > 0) {
            current = positions.removeLast();
            // -------- Check First index ----------
            y = exchange(0, 1, current);
            if (y >= 0) {return y.toString();};
            // --------- Check Last index -----------
            y = exchange(num - 1, -1, current);
            if (y >= 0) {return y.toString();};

            // -------- Checking all intermediate indices -----
            for (int i = 1; i < num - 1; i ++) {
                y = exchange(i, 1, current);
                if (y >= 0) {return y.toString();};
                y = exchange(i, -1, current);
                if (y >= 0) {return y.toString();};
            }
        }
        return "IMPOSSIBLE";
    }

    private static int exchange(int pos1, int change, List<Stack<Integer>> current) {
        List<Stack<Integer>> temp;
        if (!current.get(pos1).isEmpty()) { // first index isn't empty)
            if (current.get(pos1 + change).isEmpty()) { // second index is empty
                temp = new ArrayList<>();
                deepCopyStack(temp, current);
                temp.get(pos1 + change).push(temp.get(pos1).pop()); // move first to second
                if (addPosition(temp)) {
                    return checkTurn(temp);
                }
            } else { // both first and second index aren't empty
                if ((Integer)current.get(pos1 + change).peek() > (Integer)current.get(pos1).peek()) { // second is greater than first
                    temp = new ArrayList<>();
                    deepCopyStack(temp, current);
                    temp.get(pos1 + change).push(temp.get(pos1).pop()); // move first to secondif (addPosition(temp)) {
                    if (addPosition(temp)) {
                        return checkTurn(temp);
                    }
                }
            }
        }
        return -1; // not successful in completing the game
    }

    private static void deepCopyStack(List<Stack<Integer>> temp, List<Stack<Integer>> current) {
        // copies all current into temp
        for (int i = 0; i < current.size(); i ++) {
            Stack<Integer> stack = current.get(i);
            temp.add(new Stack());
            for (int coin : stack) {
                temp.get(i).push(coin);
            }
        }
    }

    private static boolean addPosition(List<Stack<Integer>> tempo) {
        List<Stack<Integer>> stack = new Stack<>();
        deepCopyStack(stack, tempo.subList(0, number));
        if (isFinal(tempo)) { // is in ascending order (COMPLETE)
            return true;
        } else if (!visited.containsKey(stack)){ // not visited before
            incrementTurn(tempo); // increment one turn
            visited.put(stack, checkTurn(tempo)); // add to visited
            positions.addFirst(tempo); // add to positions

        }
        return false;
    }

    private static boolean isFinal(List<Stack<Integer>> current) {
        if (current.get(0).isEmpty()) { // The first one is empty
            return false;
        }
        for (int i = 1; i < number; i ++) {
            if (current.get(i).isEmpty()) { // it's empty
                return false;
            } else { // it's not empty

                Integer x = (Integer)current.get(i - 1).peek();
                if (x > (Integer)current.get(i).peek()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int checkTurn(List<Stack<Integer>> tempo) {
        return (Integer)tempo.get(number).pop();
    }

    private static void incrementTurn(List<Stack<Integer>> tempo) {
        int x = (Integer)tempo.get(number).pop();
        tempo.get(number).push(x + 1);
    }

    static class Reader {

        static BufferedReader reader;

        static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader((new InputStreamReader(input)));
            tokenizer = new StringTokenizer("");
        }

        static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        static String nextLine() throws IOException {
            return reader.readLine();
        }

        static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

    }
}


