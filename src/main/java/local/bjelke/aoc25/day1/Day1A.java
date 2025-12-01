package local.bjelke.aoc25.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1A {

    static void main() {
        new Day1A().run();
    }

    File file = new File("day1.txt");

    int dialStart = 50;

    int dialPointer = dialStart;

    int sequenceCount = 0;

    void run() {
        try(var scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String direction = line.substring(0, 1);
                int offset = Integer.parseInt(line.substring(1));

                if (direction.equals("L")) {
                    dialPointer = (dialPointer - offset) % 100;
                }
                if (direction.equals("R")) {
                    dialPointer = (dialPointer + offset) % 100;
                }
                if (dialPointer == 0) {
                    sequenceCount++;
                }
            }
            System.out.println("Count: " + sequenceCount);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
