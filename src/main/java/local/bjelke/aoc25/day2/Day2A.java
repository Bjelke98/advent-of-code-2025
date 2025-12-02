package local.bjelke.aoc25.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2A {
    static void main() {
        new Day2A().run();
    }

    File file = new File("day2.txt");

    record Range(long start, long end) {
        static Range from(String str) {
            var list = str.split("-");
            return new Range(Long.parseLong(list[0]), Long.parseLong(list[1]));
        }
    }

    void run() {
        long invalidIdSum = 0;

        try(var scanner = new Scanner(file)) {
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                Range range = Range.from(scanner.next());
                System.out.println(range);
                for (long i = range.start; i <= range.end; i++) {
                    String intString = Long.toString(i);
                    int len = intString.length();
                    if (len % 2 == 0) {
                        if (intString.substring(0, len/2).equals(intString.substring(len/2))) {
                            invalidIdSum += i;
                        }
                    }
                }
                System.out.println(invalidIdSum);
            }
            System.out.println(invalidIdSum);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
