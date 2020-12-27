import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2020/day/2
 */
public class day2 {
    public static void main(String[] args) {
        String fileName = args[0];
        int numValidPasswords = 0;
        int numValidPasswordsPT2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                String[] split = line.split(" ");

                String[] bounds = split[0].split("-");
                int lowerBound = Integer.valueOf(bounds[0]);
                int upperBound = Integer.valueOf(bounds[1]);
                char ch = split[1].charAt(0);

                Map<Character, Integer> count = new HashMap<>();
                char[] password = split[2].toCharArray();
                for (char c : password) {
                    count.put(c, count.getOrDefault(c, 0) + 1);
                }

                if (count.containsKey(ch) && count.get(ch) >= lowerBound && count.get(ch) <= upperBound) {
                    numValidPasswords++;
                }

                // Part 2
                if (upperBound > password.length) {
                    if (password[lowerBound - 1] == ch) {
                        numValidPasswordsPT2++;
                    }
                } else {
                    if (password[lowerBound - 1] == ch && password[upperBound - 1] == ch) {
                        continue;
                    } else if (password[lowerBound - 1] == ch || password[upperBound - 1] == ch) {
                        numValidPasswordsPT2++;
                    } else {
                        continue;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Part 1: Number of valid passwords: %d\n", numValidPasswords); // 620
        System.out.printf("Part 2: Number of valid passwords: %d\n", numValidPasswordsPT2); // 727
    }
}
