package local.bjelke.aoc25.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7A {
    static void main() {
        new Day7A().run();
    }

    File file = new File("day7.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            var matrix = new ArrayList<ArrayList<Character>>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                var characters = new ArrayList<Character>();
                try (var input = new Scanner(line)) {
                    input.useDelimiter("");
                    while (input.hasNext()) {
                        characters.add(input.next().charAt(0));
                    }
                }
                matrix.add(characters);
            }

            for (ArrayList<Character> strings : matrix) {
                System.out.println(strings);
            }

            System.out.println();

            long splitCount = 0;

            for (int i = 1; i < matrix.size(); i++) {
                var lineAbove = matrix.get(i-1);
                var line = matrix.get(i);
                for (int j = 0; j < line.size(); j++) {
                    if (lineAbove.get(j).equals('S') || (lineAbove.get(j).equals('|') && line.get(j).equals('.'))) {
                        line.set(j, '|');
                    }
                    if (lineAbove.get(j).equals('|') && line.get(j).equals('^')) {
                        line.set(j-1, '|');
                        line.set(j+1, '|');
                        splitCount++;
                    }
                }
            }

            for (ArrayList<Character> characters : matrix) {
                System.out.println(characters);
            }

            System.out.println(splitCount);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
