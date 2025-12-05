package local.bjelke.aoc25.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day5B {
    static void main() {
        new Day5B().run();
    }

    record Range(long start, long end) {
        boolean canCombine(Range r2) {
            return (end >= r2.start - 1) && (r2.end >= start - 1);
        }

        static Range combine(Range r1, Range r2) {
            return new Range(Math.min(r1.start, r2.start), Math.max(r1.end, r2.end));
        }
    }

    List<Range> compressRanges(List<Range> ranges) {
        // Modifiable copy
        var modRanges = new ArrayList<>(ranges);
        int combineCount;
        do {
            combineCount = 0;
            var removeIndexes = new ArrayList<Integer>();
            for (int i = 0; i < modRanges.size(); i++) {
                // Skip if index marked for removal
                if (removeIndexes.contains(i)) {
                    continue;
                }
                Range r = modRanges.get(i);
                for (int j = i+1; j < modRanges.size(); j++) {
                    if (!removeIndexes.contains(j) && r.canCombine(modRanges.get(j))) {
                        r = Range.combine(r, modRanges.get(j));
                        removeIndexes.add(j);
                    }
                }
                modRanges.set(i, r);
            }
            combineCount += removeIndexes.size();

            // Cleanup
            removeIndexes.sort(Comparator.reverseOrder());
            for (Integer removeIndex : removeIndexes) {
                modRanges.remove(removeIndex.intValue());
            }
            removeIndexes.clear();
            System.out.println(combineCount);
        } while (combineCount != 0);

        return modRanges;
    }

    File file = new File("day5.txt");

    void run() {
        try(var scanner = new Scanner(file)) {
            boolean rangeMode = true;
            var ranges = new ArrayList<Range>();

            while (scanner.hasNextLine() && rangeMode) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    rangeMode = false;
                }

                if (rangeMode) {
                    String[] strings = line.split("-");
                    ranges.add(new Range(Long.parseLong(strings[0]), Long.parseLong(strings[1])));
                }
            }

            var compressed = compressRanges(ranges);
            compressed.sort(Comparator.comparingLong(r -> r.start));

            for (Range range : compressed) {
                System.out.println(range);
            }

            compressed.stream().map(r -> (r.end - r.start) + 1)
                    .reduce(Long::sum)
                    .ifPresent(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
