import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://adventofcode.com/2020/day/8
 */
public class day8 {
    public static void main(String[] args) {
        String fileName = args[0];
        final String ACC = "acc";
        final String JMP = "jmp";
        final String NOP = "nop";

        int accumulator = 0;
        List<String> instructions = new ArrayList<>();
        Set<Integer> indicesVisited = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                instructions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; !indicesVisited.contains(i);) {
            indicesVisited.add(i);
            String instruction = instructions.get(i);
            String[] split = instruction.split(" ");
            String action = split[0];
            int value = Integer.valueOf(split[1]);

            if (action.equals(NOP)) {
                i++;
            } else if (action.equals(ACC)) {
                accumulator += value;
                i++;
            } else { // JMP
                i += value;
            }
        }

        // PART 2
        indicesVisited.clear();
        Set<Integer> attemptedModified = new HashSet<>();
        int accumulatorPT2 = 0;
        boolean isModified = false;
        for (int i = 0; i < instructions.size();) {
            if (indicesVisited.contains(i) || i < 0) {
                indicesVisited.clear();
                i = 0;
                accumulatorPT2 = 0;
                isModified = false;
            }

            indicesVisited.add(i);
            String instruction = instructions.get(i);
            String[] split = instruction.split(" ");
            String action = split[0];
            int value = Integer.valueOf(split[1]);

            // Brute Force && always attempt to modify if we haven't done so already
            if (!attemptedModified.contains(i) && !isModified) {
                if (!action.equals(ACC)) {
                    action = action.equals(NOP) ? JMP : NOP;
                    attemptedModified.add(i);
                    isModified = true;
                }
            }

            if (action.equals(NOP)) {
                i++;
            } else if (action.equals(ACC)) {
                accumulatorPT2 += value;
                i++;
            } else { // JMP
                i += value;
            }

        }

        System.out.printf("Part 1 Value of Accumulator before first repeated instruction: %d\n", accumulator);

        System.out.printf("Part 2 Value of Accumulator after fixing: %d\n", accumulatorPT2);
    }
}
