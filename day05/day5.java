import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * https://adventofcode.com/2020/day/5
 */
public class day5 {
    public static void main(String[] args) {
        String fileName = args[0];
        char FRONT = 'F';
        char BACK = 'B';
        char LEFT = 'L';
        char RIGHT = 'R';

        int maxSeatID = 0;
        Set<Integer> seatIDs = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                char[] pass = line.toCharArray();

                int leftPointer = 0;
                int rightPointer = 127;
                int increment = 64;
                for (int i = 0; i < 7; i++) {
                    if (pass[i] == FRONT) { // take lower half, move right pointer
                        rightPointer -= increment / Math.pow(2, i);
                    } else if (pass[i] == BACK) { // take upper half move left pointer
                        leftPointer += increment / Math.pow(2, i);
                    }
                }

                int row = pass[6] == FRONT ? leftPointer : rightPointer;

                leftPointer = 0;
                rightPointer = 7;
                increment = 4;
                for (int i = 7; i < pass.length; i++) {
                    if (pass[i] == LEFT) { // take lower half, move right pointer
                        rightPointer -= increment / Math.pow(2, i - 7);
                    } else if (pass[i] == RIGHT) { // take upper half move left pointer
                        leftPointer += increment / Math.pow(2, i - 7);
                    }
                }
                int col = pass[9] == LEFT ? leftPointer : rightPointer;
                int currSeatID = row * 8 + col;
                maxSeatID = Math.max(maxSeatID, currSeatID);
                seatIDs.add(currSeatID);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Part 1 Highest Seat ID on a boarding pass: %d\n", maxSeatID); // 818

        int i = 48;
        for (; i <= 818; i++) {
            if (!seatIDs.contains(i))
                break;
        }
        System.out.printf("Part 2 My Seat ID: %s\n", i); // 559
    }
}
