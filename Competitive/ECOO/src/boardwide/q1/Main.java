package boardwide.q1;

import java.io.*;

/* Good Luck


 */

public class Main {

    private static BufferedReader br;

    public static void main(String[] args) {
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\scowluga\\Documents\\GitHub\\DSA-Java\\ECOO\\src\\boardwide.q1\\DATA11.txt"));
            for (int i = 0; i < 10; i ++) {
                playGame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playGame() throws IOException {
        int cost = Integer.parseInt(br.readLine()); // 4000
        String[] percentages = br.readLine().split(" ");
        int students = Integer.parseInt(br.readLine()); // 400

        double[] perc = new double[4];
        perc[0] = Double.parseDouble(percentages[0]);
        perc[1] = Double.parseDouble(percentages[1]);
        perc[2] = Double.parseDouble(percentages[2]);
        perc[3] = Double.parseDouble(percentages[3]);

        int[] studentsNum = new int[4];

        int sumStudents = 0;
        int temp;

        int maxIndex = 0; // index of studentsNum to add (students - sumStudents) to
        double maxPercent = perc[0];

        for (int i = 0; i < 4; i ++) {
            temp = (int) Math.floor(perc[i] * students);
            studentsNum[i] = temp;
            sumStudents += temp;
            if (perc[i] > maxPercent) {
                maxIndex = i;
            }
        }
        studentsNum[maxIndex] += students - sumStudents;

        int sum = 0;
        sum += studentsNum[0] * 12 + studentsNum[1] * 10 + studentsNum[2] * 7 + studentsNum[3] * 5;
        if (sum * 0.5 >= cost) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
        }
    }
}