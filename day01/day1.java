import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://adventofcode.com/2020/day/1 Part 1: The two entries that sum to 2020
 * are: 1583, 437. Their product is: 691771 Part 2: The three entries that sum
 * to 2020 are: 335, 717, 968. Their product is: 232508760
 */
public class day1 {
    public static void main(String[] args) {
        String fileName = args[0];
        Set<Integer> set = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                int i = Integer.valueOf(line);
                int complement = 2020 - i;
                if (set.contains(complement)) {
                    System.out.printf("Part 1: The two entries that sum to 2020 are: %d, %d. Their product is: %d\n", i,
                            complement, i * complement);
                }
                set.add(i);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer[] arr = set.toArray(new Integer[0]);
        Arrays.sort(arr);

        for (int i = 0; i < arr.length - 3; i++) {
            int x = arr[i];
            int l = i + 1;
            int r = arr.length - 1;
            while (l <= r) {
                if (x + arr[l] + arr[r] == 2020) {
                    System.out.printf(
                            "Part 2: The three entries that sum to 2020 are: %d, %d, %d. Their product is: %d\n", x,
                            arr[l], arr[r], x * arr[l] * arr[r]);
                    break;
                } else if (x + arr[l] + arr[r] > 2020) {
                    r--;
                } else {
                    l++;
                }
            }
        }
    }
}