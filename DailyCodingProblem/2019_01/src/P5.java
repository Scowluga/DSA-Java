package SUNNYWAANG;

import javafx.util.Pair;

/* --- Problem ---  
 * Topics: Generic Types

cons(a, b) constructs a pair,
car(pair) and cdr(pair) returns the first and last element of that pair.

For example, car(cons(3, 4)) returns 3, and cdr(cons(3, 4)) returns 4.

Given this implementation of cons:

def cons(a, b):
    def pair(f):
        return f(a, b)
    return pair
Implement car and cdr.


 */
 
/* --- Solution ---  

Umm I'm not sure if this is what the question meant

But I guess it's 2 lines?

Implemented with Java's generic types since it's static typing

Generic types are really important. You can specify variable
Or you can say like ? extends/super Integer

This supports OOP with like Liskov Substitution Principle
So that's pretty cool :)

Oh also the signature:

Functions: static <generic> [return type] [name] {}
    Makes sense you need to declare it before potentially returning it

Classes: class [name] <generic> {}
 
 */

public class P5 {

    static <K, V> Pair<K, V> cons(K a, V b) {
        return new Pair<>(a, b);
    }

    static <K, V> K car(Pair<K, V> pair) {
        return pair.getKey();
    }

    static <K, V> V cdr(Pair<K, V> pair) {
        return pair.getValue();
    }

    public static void main(String[] args) {

        System.out.println(car(cons(3, 4)));
        System.out.println(cdr(cons(3, 4)));

    }

}
