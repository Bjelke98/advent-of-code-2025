package local.bjelke.aoc25.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7B {
    static void main() {
        new Day7B().run();
    }

    File file = new File("day7.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            var matrix = new ArrayList<ArrayList<String>>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                var characters = new ArrayList<String>();
                try (var input = new Scanner(line)) {
                    input.useDelimiter("");
                    while (input.hasNext()) {
                        characters.add(input.next());
                    }
                }
                matrix.add(characters);
            }

            for (int i = 1; i < matrix.size(); i++) {
                var lineAbove = matrix.get(i-1);
                var line = matrix.get(i);
                for (int j = 0; j < line.size(); j++) {
                    if (lineAbove.get(j).equals("S") || (lineAbove.get(j).equals("|") && line.get(j).equals("."))) {
                        line.set(j, "|");
                    }
                    if (lineAbove.get(j).equals("|") && line.get(j).equals("^")) {
                        line.set(j-1, "|");
                        line.set(j+1, "|");
                    }
                }
            }

            for (ArrayList<String> strings : matrix) {
                System.out.println(strings);
            }

            var last = matrix.getLast();

            for (int i = 0; i < last.size(); i++) {
                if (last.get(i).equals("|")) {
                    last.set(i, "1");
                }
            }

            for (int i = matrix.size()-2; i > 0; i--) {
                var line = matrix.get(i);
                var lineBelow = matrix.get(i+1);

                for (int j = 0; j < line.size(); j++) {
                    // move counter
                    if (line.get(j).equals("|") && !lineBelow.get(j).equals("^")) {
                        line.set(j, lineBelow.get(j));
                    }
                    // Combine counter
                    if (line.get(j).equals("|") && lineBelow.get(j).equals("^")) {
                        var bl = lineBelow.get(j-1);
                        var br = lineBelow.get(j+1);
                        line.set(j, Long.parseLong(bl) + Long.parseLong(br) + "");
                    }
                }
            }

            for (ArrayList<String> strings : matrix) {
                System.out.println(strings);
            }

            int sIndex = matrix.getFirst().lastIndexOf("S");

            System.out.println(matrix.get(1).get(sIndex));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
