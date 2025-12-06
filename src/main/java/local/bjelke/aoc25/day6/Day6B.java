package local.bjelke.aoc25.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

public class Day6B {
    static void main() {
        new Day6B().run();
    }

    File file = new File("day6.txt");

    void padMatrix(ArrayList<ArrayList<Character>> matrix) {
        OptionalInt maxSize = matrix.stream().map(List::size).mapToInt(value -> value).max();

        for (ArrayList<Character> line : matrix) {
            if (maxSize.getAsInt() == line.size()) continue;
            for (int j = 0; j < maxSize.getAsInt() - line.size() + 1; j++) {
                line.add(' ');
            }
        }
    }

    void run() {
        try(var scanner = new Scanner(file)) {
            var matrix = new ArrayList<ArrayList<Character>>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                var lineList = new ArrayList<Character>();
                try (var input = new Scanner(line)) {
                    input.useDelimiter("");
                    while (input.hasNext()) {
                        lineList.add(input.next().charAt(0));
                    }
                }
                matrix.add(lineList);
            }

            padMatrix(matrix);

            int len = matrix.getFirst().size();

            for (ArrayList<Character> characters : matrix) {
                System.out.println(characters);
            }

            Character lastSign = '-';
            long setResult = 0;
            long sumResult = 0;
            var signList = matrix.getLast();
            for (int i = 0; i < len; i++) {
                var sign = signList.get(i);
                if (sign.equals('*') || sign.equals('+')) {
                    System.out.println(setResult);
                    sumResult += setResult;
                    lastSign = sign;
                    setResult = 0;
                }

                long number = 0;
                for (int j = 0; j < matrix.size()-1; j++) {
                    var curr = matrix.get(j).get(i);
                    if (curr!=' ') {
                        int value = curr - '0';
                        if (number > 0) {
                            number = number * 10 + value;
                        } else {
                            number = value;
                        }
                    }
                }

                if (number > 0) {
                    if (lastSign.equals('*')) {
                        if (setResult == 0) {
                            setResult = number;
                        } else {
                            setResult *= number;
                        }
                    } else {
                        setResult += number;
                    }
                }

            }

            sumResult += setResult;

            System.out.println(sumResult);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
