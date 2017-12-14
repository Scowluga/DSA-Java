package Utilities;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*

*/
public class MathLibrary {

    public static void main(String[] args) {

    }


    static void MathExamples() {
        double a = 1.0;
        double b = 2.0;

        Math.abs(a);

        Math.max(a, b);
        Math.min(a, b);

        Math.sin(a);
        Math.sin(a);
        Math.tan(a);

        Math.toDegrees(a);
        Math.toRadians(a);

        Math.pow(a, 2);
        Math.sqrt(a);
        Math.round(a);

        Math.exp(a);
        Math.log(a);
        Math.log1p(a);
        Math.log10(a);

        Math.ceil(a);
        Math.floor(a);

        double c = Math.E;
        double d = Math.PI;
    }

    // --- Logic
    static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i*i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // greatest common factor
    static int gcf(int x, int y) {
        if (y == 0) return x;
        else return (gcf (y, x % y));
    }

    // lowest common multiple
    static int lcm(int x, int y) {
        return x * y / gcf(x, y);
    }

    // --- Geometry

    // slope of two points
    static double slope(P p1, P p2) {
        if (p1.x == p2.x) {
             // horizontal
        } else if (p1.y == p2.y) {
            // vertical
        }

        return ((p2.y - p1.y * 1.0) / (p2.x - p1.x));
    }

    // distance between points
    static double dist(P p1, P p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    // middle of two values
    static double middle (int v1, int v2) {
        return (v1 + v2 * 1.0) / 2.0;
    }

    // midpoint
    static P midPoint (P p1, P p2) {
        return new P((p2.x + p1.x) / 2.0, (p2.y + p1.y) / 2.0);
    }

    // point
    static class P {
        double x;
        double y;

        P() {
            this.x = 0;
            this.y = 0;
        }

        P(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            P p = (P)obj;
            return p.x == this.x && p.y == this.y;
        }

        @Override
        public String toString() {
            return "Point (" + this.x + ", " + this.y + ").";
        }
    }
}
