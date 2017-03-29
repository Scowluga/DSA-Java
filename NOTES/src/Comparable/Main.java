package Comparable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/* Comparable
For organization in the PriorityQueue, TreeSet, TreeMap

returns 2 objects on a number line
    positive -> greater
    0 -> same
    negative -> less than

So like subtract an integer value
    this.height - other.height;

 */

public class Main {
    public static void main(String[] args) {
        try {
            Reader.init(System.in);

            // COMPARATOR
            Map<Integer, Integer> whee = new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return 0;
                }
            });

            List<Person> people = new ArrayList<>(Arrays.asList( // List of unordered people
                    new Person("David", 20),
                    new Person("Emily", 10),
                    new Person("Yinuo", 30)));

            List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 2, 5)); // List of unordered numbers

            System.out.println("Not sorted"); // print lists as they are (unsorted)
            System.out.println(people);
            System.out.println(numbers);

            System.out.println("Sorted");
            Collections.sort(people); // Sort
            Collections.sort(numbers);

            System.out.println(people); // Print
            System.out.println(numbers);

            System.out.println("--------------"); // Sets
            TreeSet<Person> grade = new TreeSet<>(); // Create treeset of people
            grade.add(new Person("David", 20));
            grade.add(new Person("Emily", 10));
            grade.add(new Person("Yinuo", 30));

            System.out.println("Organized set of people");
            System.out.println(grade);
            System.out.println(grade.last());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Person implements Comparable {

        String name;
        int height;

        public Person(String name, int height) {
            this.name = name;
            this.height = height;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Person) {
                Person p = (Person)o;
                return this.height - p.height;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return this.name + " and " + this.height + " tall";
        }
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


