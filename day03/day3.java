import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/3
 */
public class day3 {
    public static void main(String[] args) {
        String fileName = args[0];

        List<String> grid = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                grid.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int treeCount1 = 0, treeCount2 = 0, treeCount3 = 0, treeCount4 = 0, treeCount5 = 0;
        int slope1 = 0, slope2 = 0, slope3 = 0, slope4 = 0, slope5 = 0;

        for (int i = 0; i < grid.size(); i++) {
            if (i == 0) {
                continue;
            }

            slope1 += 3;
            slope2++;
            slope3 += 5;
            slope4 += 7;
            slope5++;

            String line = grid.get(i);
            int length = line.length();
            if (slope1 >= length) slope1 = slope1 % length;
            if (slope2 >= length) slope2 = slope2 % length;
            if (slope3 >= length) slope3 = slope3 % length;
            if (slope4 >= length) slope4 = slope4 % length;
            if (slope5 >= length) slope5 = slope5 % length;

            if (line.charAt(slope1) == '#') treeCount1++;
            if (line.charAt(slope2) == '#') treeCount2++;
            if (line.charAt(slope3) == '#') treeCount3++;
            if (line.charAt(slope4) == '#') treeCount4++;
            if (line.charAt(slope5) == '#' && i % 2 == 0) treeCount5++;
        }
        long product = (long) treeCount1 * treeCount2 * treeCount3 * treeCount4 * treeCount5;
        System.out.printf("Part 1 Number of Trees Encountered: %d\n", treeCount1); // 205
        System.out.printf( // 3952146825
                "Part 2 Number of Trees Encountered Respectively: %d,%d,%d,%d,%d. The product of all 5 is %d.\n",
                treeCount1, treeCount2, treeCount3, treeCount4, treeCount5, product);
    }
}
