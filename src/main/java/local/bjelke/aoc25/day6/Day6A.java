package local.bjelke.aoc25.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6A {
    static void main() {
        new Day6A().run();
    }

    File file = new File("day6.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            var matrix = new ArrayList<ArrayList<String>>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                var lineList = new ArrayList<String>();
                try (var input = new Scanner(line)) {
                    while (input.hasNext()) {
                        lineList.add(input.next());
                    }
                }
                matrix.add(lineList);
            }

            for (ArrayList<String> strings : matrix) {
                System.out.println(strings);
            }

            long sumResult = 0;

            int len = matrix.getFirst().size();
            for (int i = 0; i < len; i++) {
                String sign = matrix.getLast().get(i);
                long result = Long.parseLong(matrix.getFirst().get(i));
                for (int j = 1; j < matrix.size()-1; j++) {
                    if (sign.equals("*")) {
                        result *= Long.parseLong(matrix.get(j).get(i));
                    } else {
                        result += Long.parseLong(matrix.get(j).get(i));
                    }
                }
                sumResult += result;
            }

            System.out.println(sumResult);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
