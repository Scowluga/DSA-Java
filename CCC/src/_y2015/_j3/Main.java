package _y2015._j3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Rovarspraket 15/15
 */
public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String wordIn = in.readLine();
            String word = "";
            for (int i = 0; i < wordIn.length(); i ++) {
                String c = wordIn.substring(i, i + 1);
                word += result(c);
            }

            System.out.println(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String result(String s) {
        List<String> alphabet = new ArrayList<>(Arrays.asList("a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",")));
        List<String> vowels = new ArrayList<>(Arrays.asList("a,e,i,o,u".split(",")));
        if (vowels.contains(s)) {
            return s;
        } else { // Consonant
            String ret = s; // First letter is itself
            int position = alphabet.indexOf(s); // position in alphabet
            // Finding closest vowel
            String letter;
            for (int i = 1; i < 26; i ++) {
                if (position - i >= 0) {
                    letter = alphabet.get(position - i); // before
                    if (vowels.contains(letter)) { // is a vowel
                        ret += letter;
                        break;
                    }
                }
                if (position + i < 26) {
                    letter = alphabet.get(position + i); // after
                    if (vowels.contains(letter)) { // is a vowel
                        ret += letter;
                        break;
                    }
                }
            }
            // Finding next consonant
            if (position == 25) {
                ret += s;
            } else {
                for (int i = position + 1; i < 26; i ++) {
                    letter = alphabet.get(i);
                    if (!vowels.contains(letter)) {
                        ret += letter;
                        break;
                    }
                }
            }
            return ret;
        }
    }
}

