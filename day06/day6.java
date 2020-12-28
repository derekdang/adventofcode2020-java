import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://adventofcode.com/2020/day/5
 */
public class day6 {
    public static void main(String[] args) {
        String fileName = args[0];
        int sum = 0;
        int numOfPeople = 0;
        int sumPT2 = 0;

        StringBuilder sb = new StringBuilder();

        Set<Character> questionsAnswered = new HashSet<>();

        // Map is for PT2
        Map<Character, Integer> charCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                if (line.isEmpty()) {
                    sum += questionsAnswered.size();
                    questionsAnswered.clear();
                    sb.setLength(0);

                    for (Integer i : charCount.values()) {
                        if (i == numOfPeople)
                            sumPT2++;
                    }
                    numOfPeople = 0;
                    charCount.clear();
                } else {
                    for (char ch : line.toCharArray()) {
                        questionsAnswered.add(ch);
                        charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
                    }
                    numOfPeople++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Integer i : charCount.values()) {
            if (i == numOfPeople)
                sumPT2++;
        }

        sum += questionsAnswered.size();
        System.out.printf("Part 1 Sum to Yes Answers: %d\n", sum);

        System.out.printf("Part 2 Sum to Yes Answers: %d\n", sumPT2);
    }
}
