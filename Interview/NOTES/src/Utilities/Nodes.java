package Utilities;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*; 


public class Nodes {

    static class Node1 {

        int val;
        Node1 next;

        Node1(int v0) {
            this.val = v0;
        }
    }

    static class Node2 {

        int val;
        Node2 left;
        Node2 right;

        Node2(int v0) {
            this.val = v0;
        }
    }
}
