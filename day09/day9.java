import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/9
 */
public class day9 {
    public static void main(String[] args) {
        String fileName = args[0];

        LinkedList<Long> list = new LinkedList<>();
        List<Long> dataSet = new ArrayList<>();
        Long ans = Long.valueOf(0);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                Long value = Long.valueOf(line);
                boolean isValid = false;

                dataSet.add(value);
                if (list.size() < 25) {
                    list.add(value);
                } else {
                    for (Long i : list) {
                        if (list.contains(value - i)) {
                            list.removeFirst();
                            list.addLast(value);
                            isValid = true;
                            break;
                        }
                    }
                    if (!isValid) {
                        ans = value;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Part 1 First # that is not sum of 2 of previous 25: %d\n", ans); // 138879426

        Long small;
        Long large;
        Long ansPT2 = null;
        for (int i = 0; i < dataSet.size() - 1 && ansPT2 == null; i++) {
            Long sum = dataSet.get(i);
            small = dataSet.get(i);
            large = dataSet.get(i);

            for (int j = i + 1; j < dataSet.size(); j++) {
                if (sum > ans)
                    break;

                sum += dataSet.get(j);
                small = Math.min(small, dataSet.get(j));
                large = Math.max(large, dataSet.get(j));

                if (sum.equals(ans)) {
                    ansPT2 = small + large;
                    break;
                }
            }
        }

        System.out.printf("Part 2 Sum of largest+smallest in sequence that totals %d: %d\n", ans, ansPT2); // 23761694
    }
}
