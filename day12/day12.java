import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/12
 */
public class day12 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        List<String> instructions = new ArrayList<>();
        while (line != null) {
            instructions.add(line);
            line = br.readLine();
        }
        br.close();

        Ship ship = new Ship();
        Ship ship2 = new Ship();
        for (String s : instructions) {
            char instruction = s.charAt(0);
            int unit = Integer.valueOf(s.substring(1));
            if (instruction == 'F') {
                ship.travel(unit);
                ship2.travelToWayPoint(unit);
            } else if (instruction == 'L' || instruction == 'R') {
                ship.turn(instruction, unit);
                ship2.rotateWayPoint(instruction, unit);
            } else {
                ship.travel(instruction, unit);
                ship2.moveWayPoint(instruction, unit);
            }
        }

        int manhanttanDistance = Math.abs(ship.x) + Math.abs(ship.y);
        System.out.printf("Part 1: Manhattan distance from start to end position: %d\n", manhanttanDistance); // 938

        int manhanttanDistance2 = Math.abs(ship2.x) + Math.abs(ship2.y);
        System.out.printf("Part 2: Manhattan distance from start to end position w/ new logic: %d\n",
                manhanttanDistance2); // 54404
    }

    public static class Ship {
        char direction;
        int x;
        int y;

        int wayPointX;
        int wayPointY;

        public Ship() {
            this.x = 0;
            this.y = 0;
            this.direction = 'E';

            this.wayPointX = 10;
            this.wayPointY = 1;
        }

        public void travel(int unit) {
            if (this.direction == 'E') {
                x += unit;
            } else if (this.direction == 'W') {
                x -= unit;
            } else if (this.direction == 'N') {
                y += unit;
            } else {
                y -= unit;
            }
        }

        public void travel(char direction, int unit) {
            if (direction == 'E') {
                x += unit;
            } else if (direction == 'W') {
                x -= unit;
            } else if (direction == 'N') {
                y += unit;
            } else {
                y -= unit;
            }
        }

        // Ship Turning PT1
        public void turn(char direction, int unit) {
            for (int i = unit; i > 0; i -= 90) {
                if (direction == 'L')
                    turnLeft();
                else
                    turnRight();
            }
        }

        public void turnLeft() {
            if (this.direction == 'E') {
                this.direction = 'N';
            } else if (this.direction == 'W') {
                this.direction = 'S';
            } else if (this.direction == 'N') {
                this.direction = 'W';
            } else {
                this.direction = 'E';
            }
        }

        public void turnRight() {
            if (this.direction == 'E') {
                this.direction = 'S';
            } else if (this.direction == 'W') {
                this.direction = 'N';
            } else if (this.direction == 'N') {
                this.direction = 'E';
            } else {
                this.direction = 'W';
            }
        }

        /**
         * WayPoint Logic for PT2
         */

        public void moveWayPoint(char direction, int unit) {
            if (direction == 'E') {
                wayPointX += unit;
            } else if (direction == 'W') {
                wayPointX -= unit;
            } else if (direction == 'N') {
                wayPointY += unit;
            } else {
                wayPointY -= unit;
            }
        }

        public void rotateWayPoint(char direction, int unit) {
            int dX = wayPointX - x; // 10 east
            int dY = wayPointY - y; // 4 north

            int timesRotated = unit / 90; // can mod if > 270

            if (direction == 'R') {
                if (timesRotated == 1) {
                    wayPointX = x + dY;
                    wayPointY = y - dX;
                } else if (timesRotated == 2) {
                    wayPointX = x - dX;
                    wayPointY = y - dY;
                } else if (timesRotated == 3) {
                    wayPointX = x - dY;
                    wayPointY = y + dX;
                }
            } else {
                if (timesRotated == 1) {
                    wayPointX = x - dY;
                    wayPointY = y + dX;
                } else if (timesRotated == 2) {
                    wayPointX = x - dX;
                    wayPointY = y - dY;
                } else if (timesRotated == 3) {
                    wayPointX = x + dY;
                    wayPointY = y - dX;
                }
            }
        }

        public void travelToWayPoint(int timesForward) {
            int dX = wayPointX - x;
            int dY = wayPointY - y;
            for (int i = 1; i <= timesForward; i++) {
                x += dX;
                y += dY;
            }

            wayPointX = x + dX;
            wayPointY = y + dY;
        }

        public String toString() {
            return "Ship's direction: " + this.direction + '\t' + "Ship's x: " + this.x + '\t' + "Ship's y: " + this.y
                    + '\t' + "Waypoint x: " + this.wayPointX + '\t' + "Waypoint y: " + this.wayPointY;
        }
    }
}
