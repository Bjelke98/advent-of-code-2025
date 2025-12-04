package local.bjelke.aoc25.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4B {
    static void main() {
        new Day4B().run();
    }

    File file = new File("day4.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            int result = 0;

            var matrix = new ArrayList<ArrayList<String>>();

            while (scanner.hasNextLine()) {
                var pointList = new ArrayList<>(List.of(scanner.nextLine().split("")));
                matrix.add(pointList);
            }

            int xLen = matrix.getFirst().size();

            int roundResult = -1;
            while (roundResult != 0) {
                roundResult = 0;

                // Line
                for (int i = 0; i < matrix.size(); i++) {
                    for (int j = 0; j < xLen; j++) {
                        if (hasAtt(j, i, matrix, xLen)) {
                            int attCount = 0;

                            for (int k = -1; k < 2; k++) {
                                for (int l = -1; l < 2; l++) {
                                    attCount += hasAtt(j + k, i + l, matrix, xLen) ? 1 : 0;
                                }
                            }

                            if (attCount <= 4) {
                                roundResult++;
                                matrix.get(i).set(j, "x");
                            }
                        }
                    }
                }

                // Cleanup
                for (ArrayList<String> strings : matrix) {
                    for (int j = 0; j < xLen; j++) {
                        if (strings.get(j).equals("x")) {
                            strings.set(j, ".");
                        }
                    }
                }

                System.out.println(roundResult);
                for (ArrayList<String> line : matrix) {
                    System.out.println(String.join("", line));
                }

                result += roundResult;
            }

            System.out.println(result);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    boolean hasAtt(int x, int y, ArrayList<ArrayList<String>> matrix, int xLen) {
        // Escape out of bounds
        if (x < 0 || x > xLen - 1 || y < 0 || y > matrix.size() - 1) {
            return false;
        }
        return matrix.get(y).get(x).equals("@") || matrix.get(y).get(x).equals("x");
    }
}
