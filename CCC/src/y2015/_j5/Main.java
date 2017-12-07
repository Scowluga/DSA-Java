package y2015._j5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* Pi Day 15/15
Recursive approach with storage of all visited calls.
13/15 without visited checks (pure recursion)
15/15 final
 */
public class Main {

    public static Map<Case, Integer> knownCases = new HashMap<>(); // Visited

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int n = Integer.parseInt(in.readLine());
            int k = Integer.parseInt(in.readLine());
            System.out.println(pi_day(n, k, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int pi_day(int pies, int people, int minPi) {
        if (pies == people || people == 1) { // base case to stop recursion
            return 1;
        } else {
            Case c = new Case(pies, people, minPi);
            if (knownCases.containsKey(c)) { // check if visited
                return knownCases.get(c);
            } else {
                int sum = 0;
                for (int i = minPi; i <= (Math.floor(pies / people)); i ++) {
                    sum += pi_day(pies - i, people - 1, i);
                }
                knownCases.put(c, sum); // add to visited
                return sum;
            }
        }
    }

    public static class Case {
        Integer pies;
        int people;
        int minPi;

        public Case(int pies, int people, int minPi) {
            this.pies = pies;
            this.people = people;
            this.minPi = minPi;
        }

        @Override
        public String toString() {
            return "Pies: " + this.pies + " people: " + this.people;
        }

        @Override
        public int hashCode() {
            return pies.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Case)) {
                return false;
            }
            Case rhs = (Case)obj;
            return this.pies == rhs.pies
                    && this.people == rhs.people
                    && this.minPi == rhs.minPi;
        }
    }
}

