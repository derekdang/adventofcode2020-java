import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://adventofcode.com/2020/day/7
 */
public class day7 {
    public static void main(String[] args) {
        String fileName = args[0];

        int sum = 0;
        Map<String, List<String>> mapping = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                String[] rule = line.split("contain");
                if (rule[1].contains("no other bags")) {
                    mapping.put(rule[0].substring(0, rule[0].lastIndexOf("bag")).trim(), new ArrayList<>());
                    continue;
                }
                String[] bags = rule[1].split(",");
                List<String> contents = new ArrayList<>();
                for (String bag : bags) {
                    String s = bag.substring(0, bag.lastIndexOf("bag")).trim();
                    contents.add(s);
                }

                String key = rule[0].substring(0, rule[0].lastIndexOf("bag")).trim();
                mapping.put(key, contents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String key : mapping.keySet()) {
            if (checkCanHold(key, mapping))
                sum++;
        }

        int sumPT2 = 0;
        sumPT2 = countNestedBags(mapping, sumPT2, "shiny gold");

        System.out.printf("Part 1 Bag Colors that can eventually contain at least one shiny gold: %d\n", sum);

        System.out.printf("Part 2 Bags required inside 1 shiny gold: %d\n", sumPT2);
    }

    private static int countNestedBags(Map<String, List<String>> mapping, int sumPT2, String key) {
        int coefficient = 1;

        if (mapping.get(key).isEmpty())
            return 1;

        for (String s : mapping.get(key)) {
            coefficient = Integer.valueOf(s.charAt(0) + "");
            sumPT2 += countNestedBags(mapping, 1, s.substring(s.indexOf(" "), s.length()).trim()) * coefficient;
        }

        return sumPT2;
    }

    private static boolean checkCanHold(String key, Map<String, List<String>> mapping) {
        List<String> bags = mapping.get(key);

        boolean canHold = false;
        if (bags.isEmpty())
            return canHold;
        for (String s : bags) {
            if (canHold)
                break;
            if (s.contains("shiny gold")) {
                canHold = true;
            } else {
                canHold = checkCanHold(s.substring(s.indexOf(" "), s.length()).trim(), mapping);
            }
        }
        return canHold;
    }
}
