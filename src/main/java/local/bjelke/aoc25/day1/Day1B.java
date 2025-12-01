package local.bjelke.aoc25.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1B {

    static class LinkedDialNumber {
        int number;
        LinkedDialNumber left = null;
        LinkedDialNumber right = null;
        LinkedDialNumber(int number) {
            this.number = number;
        }
    }

    static void main() {
        new Day1B().run();
    }

    File file = new File("day1.txt");

    int sequenceCount = 0;

    LinkedDialNumber buildNumbers() {
        LinkedDialNumber start = new LinkedDialNumber(0);
        LinkedDialNumber pointer = start;
        LinkedDialNumber middle = null;
        for (int i = 1; i < 100; i++) {
            pointer.right = new LinkedDialNumber(i);
            LinkedDialNumber prev = pointer;
            pointer = pointer.right;
            pointer.left = prev;
            if (pointer.number == 50) {
                middle = pointer;
            }
        }
        pointer.right = start;
        start.left = pointer;
        if (middle == null) {
            throw new RuntimeException("Middle is null");
        }
        return middle;
    }

    void run() {
        LinkedDialNumber pointer = buildNumbers();

        try(var scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String direction = line.substring(0, 1);
                int offset = Integer.parseInt(line.substring(1));

                if (direction.equals("L")) {
                    for (int i = 0; i < offset; i++) {
                        pointer = pointer.left;
                        if (pointer.number == 0) {
                            sequenceCount++;
                            System.out.println("++");
                        }
                    }
                } else {
                    for (int i = 0; i < offset; i++) {
                        pointer = pointer.right;
                        if (pointer.number == 0) {
                            sequenceCount++;
                            System.out.println("++");
                        }
                    }
                }
            }
            System.out.println("Count: " + sequenceCount);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
