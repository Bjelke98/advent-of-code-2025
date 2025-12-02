package local.bjelke.aoc25.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day2B {
    static void main() {
        new Day2B().run();
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
                    if (isInvalid(i)) {
                        invalidIdSum += i;
                    }
                }
                System.out.println(invalidIdSum);
            }
            System.out.println(invalidIdSum);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isInvalid(long number) {
        String longString = Long.toString(number);
        var digits = longString.toCharArray();
        if (digits.length == 1) {
            return false;
        }
        var set = new HashSet<Character>();
        for (char digit : digits) {
            set.add(digit);
        }
        if (set.size() == 1) {
            return true;
        }
        if (isPrime(digits.length)) {
            return false;
        }

        var divisibles = new ArrayList<Integer>();
        for (int i = digits.length/2; i > 1; i--) {
            if (digits.length % i == 0) {
                divisibles.add(i);
            }
        }

        for (Integer divisible : divisibles) {
            String start = longString.substring(0, divisible);
            var stateSet = new HashSet<Boolean>();
            for (int i = divisible; i <= digits.length-divisible; i+=divisible) {
                String end = longString.substring(i, i+divisible);
                if (start.equals(end)) {
                    stateSet.add(true);
                } else {
                    stateSet.add(false);
                }
            }
            if (stateSet.size() == 1 && stateSet.contains(true)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
