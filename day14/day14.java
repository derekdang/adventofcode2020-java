import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * https://adventofcode.com/2020/day/14
 */
public class day14 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        List<String> input = new ArrayList<>();
        while (line != null) {
            input.add(line);
            line = br.readLine();
        }

        Map<Integer, String> memory = new HashMap<>();
        String mask = "";
        for (String s : input) {
            String[] split = s.split("=");
            if (s.contains("mask")) {
                mask = split[1].trim();
            } else {
                int l = split[0].indexOf('[');
                int r = split[0].indexOf(']');
                Integer val = Integer.valueOf(split[1].trim());
                String binary = Integer.toBinaryString(val);
                Integer address = Integer.valueOf(split[0].substring(l + 1, r));

                StringBuilder sb = new StringBuilder(binary);
                sb = sb.reverse();
                while (sb.length() < 36) {
                    sb.append(0);
                }
                binary = sb.reverse().toString();

                sb.setLength(0);
                for (int i = 0; i < mask.length(); i++) {
                    if (mask.charAt(i) == 'X') {
                        sb.append(binary.charAt(i));
                    } else {
                        sb.append(mask.charAt(i));
                    }
                }
                memory.put(address, sb.toString());
            }
        }
        long sum = 0;
        for (String s : memory.values()) {
            sum += Long.valueOf(s, 2);
        }

        System.out.printf("Part 1 Sum of Values left in memory: %d\n", sum); // 10885823581193

        Map<String, Integer> memory2 = new HashMap<>();
        for (String s : input) {
            String[] split = s.split("=");
            if (s.contains("mask")) {
                mask = split[1].trim();
            } else {
                int l = split[0].indexOf('[');
                int r = split[0].indexOf(']');
                Integer val = Integer.valueOf(split[1].trim());
                Integer address = Integer.valueOf(split[0].substring(l + 1, r));
                String binary = Integer.toBinaryString(address);

                StringBuilder sb = new StringBuilder(binary);
                sb = sb.reverse();
                while (sb.length() < 36) {
                    sb.append(0);
                }
                binary = sb.reverse().toString();

                sb.setLength(0);
                int xCount = 0;
                for (int i = 0; i < mask.length(); i++) {
                    if (mask.charAt(i) == 'X') {
                        sb.append('X');
                        xCount++;
                    } else if (mask.charAt(i) == '0') {
                        sb.append(binary.charAt(i));
                    } else {
                        sb.append('1');
                    }
                }

                Queue<String> permutations = new ArrayDeque<>();
                permutations.add(sb.toString());
                while (permutations.size() < Math.pow(2, xCount)) {
                    String permutation = permutations.poll();
                    String v1 = permutation.replaceFirst("X", "1");
                    String v0 = permutation.replaceFirst("X", "0");
                    permutations.add(v1);
                    permutations.add(v0);
                }

                for (String permutation : permutations) {
                    memory2.put(permutation, val);
                }
            }
        }
        long sum2 = 0;
        for (Integer i : memory2.values()) {
            sum2 += i;
        }

        // 3816594901962
        System.out.printf("Part 2 Sum all values in memory for mem addr decoder: %d\n", sum2);
    }
}
