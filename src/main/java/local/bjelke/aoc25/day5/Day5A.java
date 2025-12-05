package local.bjelke.aoc25.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day5A {
    static void main() {
        new Day5A().run();
    }

    record Range(long start, long end) {}

    File file = new File("day5.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            boolean rangeMode = true;

            var ranges = new ArrayList<Range>();
            var ids = new ArrayList<Long>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    rangeMode = false;
                    line = scanner.nextLine();
                }

                if (rangeMode) {
                    String[] strings = line.split("-");
                    ranges.add(new  Range(Long.parseLong(strings[0]), Long.parseLong(strings[1])));
                } else {
                    ids.add(Long.parseLong(line));
                }
            }

            long count = ids.stream().filter(id -> isFresh(id, ranges)).count();

            System.out.println(count);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isFresh(Long id, ArrayList<Range> ranges) {
        return ranges.stream().anyMatch(r -> id >= r.start() && id <= r.end());
    }
}
