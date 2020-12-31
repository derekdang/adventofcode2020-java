import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class Day16 {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();

        int delimiter = 0;
        boolean[] validValues = new boolean[1000];
        int sumInvalidFields = 0;
        List<Integer> myTicket = new ArrayList<>();
        List<List<Integer>> nearbyTickets = new ArrayList<>();
        Map<String, List<Integer>> mapping = new HashMap<>();
        Map<String, Set<Integer>> potentialSpots = new HashMap<>(); // FOR PT2

        while (line != null) {
            if (line.isEmpty()) {
                delimiter++;
                line = br.readLine();
                continue;
            }

            if (delimiter == 0) {
                String[] split = line.split(":");
                List<Integer> list = new ArrayList<>();
                Set<Integer> spots = new HashSet<>();
                mapping.put(split[0], list);

                String[] ranges = split[1].split("or");
                for (String s : ranges) {
                    String[] values = s.split("-");
                    Integer l = Integer.valueOf(values[0].trim());
                    Integer r = Integer.valueOf(values[1].trim());
                    list.add(l);
                    list.add(r);
                    while (l <= r) {
                        validValues[l] = true;
                        l++;
                    }
                }
            } else if (delimiter == 1) {
                if (line.contains("ticket")) {
                    line = br.readLine();
                    continue;
                }
                for (String s : line.split(",")) {
                    s = s.trim();
                    myTicket.add(Integer.valueOf(s));
                }
            } else if (delimiter == 2) {
                if (line.contains("ticket")) {
                    line = br.readLine();
                    continue;
                }
                List<Integer> ticket = new LinkedList<>();
                for (String s : line.split(",")) {
                    s = s.trim();
                    Integer value = Integer.valueOf(s);
                    ticket.add(value);
                    if (!validValues[value]) {
                        sumInvalidFields += value;
                    }
                }
                nearbyTickets.add(ticket);
            }
            line = br.readLine();
        }
        br.close();

        System.out.printf("Part 1 Ticket Error Scanning Rate: %d\n", sumInvalidFields);

        nearbyTickets.removeIf(l -> {
            for (Integer i : l) {
                if (validValues[i] == false)
                    return true;
            }
            return false;
        });

        mapping.forEach((k, v) -> {
            int i = 0;
            int l1 = v.get(0), l2 = v.get(1), r1 = v.get(2), r2 = v.get(3);
            Set<Integer> set = potentialSpots.get(k);
            System.out.printf("\n%s:", k);
            while (i < nearbyTickets.get(0).size()) {
                boolean validForAllTickets = true;
                for (List<Integer> ticket : nearbyTickets) {
                    int val = ticket.get(i);
                    if (!((l1 <= val && val <= l2) || (r1 <= val && val <= r2))) {
                        validForAllTickets = false;
                        break;
                    }
                }

                if (validForAllTickets) {
                    set.add(i);
                    System.out.printf("%d ", i);
                }
                i++;
            }
        });

        long ansPT2 = (long) myTicket.get(0) * myTicket.get(1) * myTicket.get(3) * myTicket.get(5) * myTicket.get(7)
                * myTicket.get(18);
        System.out.printf("\nPart 2: product of 6 departure fields: %d\n", ansPT2); // 2564529489989
    }
}