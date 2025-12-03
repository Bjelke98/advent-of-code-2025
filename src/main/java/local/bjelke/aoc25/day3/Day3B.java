package local.bjelke.aoc25.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day3B {
    static void main() {
        new Day3B().run();
    }

    File file = new File("day3.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            long result = 0;

            while (scanner.hasNextLine()) {
                var charArr = scanner.nextLine().toCharArray();
                ArrayList<Character> charList = new ArrayList<>();
                for (char c : charArr) {
                    charList.add(c);
                }

                List<Integer> intList = charList.stream().map(c -> c - '0').toList();

                int numberCount = 12;
                int[] indexArr = IntStream.range(0, numberCount).toArray();

                int openSlots = numberCount-1;
                int prevHighIndex = 0;
                for (int i = 0; i < numberCount; i++) {
                    prevHighIndex = i == 0 ? 0 : prevHighIndex + 1;
                    indexArr[i] = -1;
                    for (int j = prevHighIndex; j < intList.size() - openSlots; j++) {
                        if (intList.get(j) > indexArr[i]) {
                            indexArr[i] = intList.get(j);
                            prevHighIndex = j;
                        }
                    }
                    openSlots--;
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (int i : indexArr) {
                    stringBuilder.append(i);
                }

                var longValue = Long.parseLong(stringBuilder.toString());

                System.out.println(longValue);

                result += longValue;
            }

            System.out.println("R: " + result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
