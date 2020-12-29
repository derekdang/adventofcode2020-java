import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/11
 */
public class day11 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        List<char[]> dataSet = new ArrayList<>();
        while (line != null) {
            dataSet.add(line.toCharArray());
            line = br.readLine();
        }
        br.close();
        char[][] grid = dataSet.toArray(new char[dataSet.size()][]);

        boolean sameState = false;
        do {
            char[][] tmp = new char[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                tmp[i] = Arrays.copyOf(grid[i], grid[i].length);
            }
            for (int i = 0; i < grid.length; i++) {
                char[] gridRow = grid[i];
                char[] tmpRow = tmp[i];

                for (int j = 0; j < gridRow.length; j++) {
                    if (gridRow[j] == 'L') {
                        tmpRow[j] = shouldFlipToOccupied(grid, i, j) ? '#' : 'L';
                    } else if (gridRow[j] == '#') {
                        tmpRow[j] = shouldFlipToEmpty(grid, i, j) ? 'L' : '#';
                    }
                }
            }

            for (int i = 0; i < grid.length; i++) {
                char[] gridRow = grid[i];
                char[] tmpRow = tmp[i];

                if (!Arrays.equals(gridRow, tmpRow)) {
                    grid = tmp;
                    break;
                }
                if (i == grid.length - 1)
                    sameState = true;
            }
        } while (!sameState);

        int occupiedSeats = 0;
        for (char[] row : grid) {
            for (char ch : row) {
                if (ch == '#')
                    occupiedSeats++;
            }
        }

        System.out.printf("Part 1 # of Occupied Seats: %d\n", occupiedSeats); // 2273

        /**
         * Part 2 ugliness Possibly N queens? seems like it a variant of it, a little
         * sleep deprived towards the end :)
         */
        char[][] grid2 = dataSet.toArray(new char[dataSet.size()][]);
        sameState = false;
        do {
            char[][] tmp = new char[grid2.length][grid2[0].length];
            for (int i = 0; i < grid2.length; i++) {
                tmp[i] = Arrays.copyOf(grid2[i], grid2[i].length);
            }
            for (int i = 0; i < grid2.length; i++) {
                char[] gridRow = grid2[i];
                char[] tmpRow = tmp[i];

                for (int j = 0; j < gridRow.length; j++) {
                    if (gridRow[j] == 'L') {
                        tmpRow[j] = flipToOccupy2(grid2, i, j) ? '#' : 'L';
                    } else if (gridRow[j] == '#') {
                        tmpRow[j] = flipToEmpty2(grid2, i, j) ? 'L' : '#';
                    }
                }
            }

            for (int i = 0; i < grid2.length; i++) {
                char[] gridRow = grid2[i];
                char[] tmpRow = tmp[i];

                if (!Arrays.equals(gridRow, tmpRow)) {
                    grid2 = tmp;
                    break;
                }
                if (i == grid2.length - 1)
                    sameState = true;
            }
        } while (!sameState);

        int occupiedSeats2 = 0;
        for (char[] row : grid2) {
            for (char ch : row) {
                if (ch == '#')
                    occupiedSeats2++;
            }
        }

        System.out.printf("Part 2 # of Occupied Seats w/ new vision: %d\n", occupiedSeats2); // 2064
    }

    final static int[][] DIRS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 },
            { 1, 1 } };

    private static boolean shouldFlipToEmpty(char[][] grid, int i, int j) {
        int count = 0;

        for (int[] dir : DIRS) {
            int a = i + dir[0];
            int b = j + dir[1];

            if (isOccupied(grid, a, b))
                count++;
        }
        return count >= 4;
    }

    private static boolean shouldFlipToOccupied(char[][] grid, int i, int j) {
        for (int[] dir : DIRS) {
            int a = i + dir[0];
            int b = j + dir[1];

            if (isOccupied(grid, a, b))
                return false;
        }
        return true;
    }

    private static boolean isOccupied(char[][] grid, int a, int b) {
        if (a < 0 || a >= grid.length || b < 0 || b >= grid[0].length)
            return false;
        return grid[a][b] == '#';
    }

    private static boolean flipToOccupy2(char[][] grid, int i, int j) {
        for (int[] dir : DIRS) {
            int a = i;
            int b = j;

            do {
                a += dir[0];
                b += dir[1];

                if (a < 0 || a >= grid.length || b < 0 || b >= grid[0].length)
                    break;
                if (grid[a][b] == 'L') {
                    break;
                } else if (grid[a][b] == '.') {
                    continue;
                } else {
                    return false;
                }
            } while (true);
        }
        return true;
    }

    private static boolean flipToEmpty2(char[][] grid, int i, int j) {
        int count = 0;
        for (int[] dir : DIRS) {
            int a = i;
            int b = j;

            do {
                a += dir[0];
                b += dir[1];
                if (a < 0 || a >= grid.length || b < 0 || b >= grid[0].length)
                    break;
                if (grid[a][b] == 'L') {
                    break;
                } else if (grid[a][b] == '.') {
                    continue;
                } else {
                    count++;
                    break;
                }
            } while (true);

        }
        return count >= 5;
    }

    private static void printGrid(char[][] grid) {
        for (char[] line : grid) {
            System.out.printf("%s\n", Arrays.toString(line));
        }
    }
}
