import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://adventofcode.com/2020/day/10
 */
public class day10 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        int[] countDifferences = new int[3];
        TreeSet<Integer> treeSet = new TreeSet<>();
        while (line != null) {
            Integer i = Integer.valueOf(line);
            treeSet.add(i);
            line = br.readLine();
        }
        br.close();

        Integer lastUsedAdapter = Integer.valueOf(0);
        Integer targetAdapter = treeSet.last() + 3;

        Set<Integer> hashSet = new HashSet<>();
        hashSet.addAll(treeSet);
        while (!treeSet.isEmpty()) {
            Integer adapter = treeSet.pollFirst();

            countDifferences[adapter - lastUsedAdapter - 1]++;
            lastUsedAdapter = adapter;
        }
        countDifferences[2]++; // my device adapter always 3 volts;

        System.out.printf("Part 1 Product of num 1-diffs and num 3-diffs: %d\n",
                countDifferences[0] * countDifferences[2]); // 2210

        long[] dp = new long[targetAdapter + 1];

        if (hashSet.contains(1))
            dp[1] = 1;
        if (hashSet.contains(2))
            dp[2] = 2;
        if (hashSet.contains(3))
            dp[3] = 4;
        for (int i = 4; i <= targetAdapter; i++) {
            if (hashSet.contains(i))
                dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        System.out.printf("Part 2 Number of distinct ways: %d\n", dp[targetAdapter - 3]); // 7086739046912
    }
}