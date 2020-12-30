import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day15 {
    final static int[] SAMPLE_INPUT = new int[] { 0, 3, 6 };
    final static int[] REAL_INPUT = new int[] { 0, 13, 1, 8, 6, 15 };

    public static void main(String[] args) {
        boolean isRealInput = Boolean.valueOf(args[0]);
        List<Integer> order = new ArrayList<>();
        Map<Integer, int[]> lastUsed = new HashMap<>();

        int[] input = isRealInput ? REAL_INPUT : SAMPLE_INPUT;
        for (int i = 0; i < input.length; i++) {
            order.add(input[i]);
            lastUsed.put(input[i], new int[] { -1, i + 1 });
        }
        int[] lastSpoken = new int[] { -1, -1 };
        int nextNumber = 0;
        boolean isNewNumberAddedLast = true;
        for (int i = order.size(); i < 30000000; i++) {
            lastSpoken = lastUsed.get(order.get(i - 1));
            nextNumber = isNewNumberAddedLast ? 0 : lastSpoken[1] - lastSpoken[0];
            order.add(nextNumber);
            isNewNumberAddedLast = lastUsed.containsKey(nextNumber) ? false : true;
            if (lastUsed.containsKey(nextNumber)) {
                lastSpoken = lastUsed.get(nextNumber);
                lastSpoken[0] = lastSpoken[1];
                lastSpoken[1] = i + 1;
            } else {
                lastUsed.put(nextNumber, new int[] { -1, i + 1 });
            }
        }
        System.out.printf("Part 1 2020th number spoken: %d\n", order.get(2019)); // 1618
        System.out.printf("Part 2 30000000th number spoken: %d\n", order.get(30000000 - 1)); // 548531
    }
}
