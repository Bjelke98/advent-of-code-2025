package local.bjelke.aoc25.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3A {
    static void main() {
        new Day3A().run();
    }

    File file = new File("day3.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            int result = 0;

            while (scanner.hasNextLine()) {
                var charArr = scanner.nextLine().toCharArray();
                ArrayList<Character> charList = new ArrayList<>();
                for (char c : charArr) {
                    charList.add(c);
                }

                List<Integer> intList = charList.stream().map(c -> c - '0').toList();

                int firstMaxIndex = 0;
                // Skip last
                for (int i = 1; i < intList.size() - 1; i++) {
                    if (intList.get(i) > intList.get(firstMaxIndex)) {
                        firstMaxIndex = i;
                    }
                }

                int secondMaxIndex = firstMaxIndex + 1;
                for (int i = firstMaxIndex + 1; i < intList.size(); i++) {
                    if (intList.get(i) > intList.get(secondMaxIndex)) {
                        secondMaxIndex = i;
                    }
                }

                int lineResult = intList.get(firstMaxIndex) * 10 + intList.get(secondMaxIndex);

                System.out.println(lineResult);

                result += lineResult;
            }

            System.out.println("R: " + result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
