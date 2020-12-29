public class day13 {
    final static String sampleInput = "939\n" + "7,13,x,x,59,x,31,19";
    final static String realInput = "1002618\n"
            + "19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,367,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,373,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23";

    public static void main(String[] args) {
        boolean isRealRun = Boolean.valueOf(args[0]);

        String data = isRealRun ? realInput : sampleInput;
        String[] split = data.split("\n");
        Integer earliestDeparture = Integer.valueOf(split[0]);
        String[] buses = split[1].split(",");

        int minMinsWaited = Integer.MAX_VALUE;
        int busPT1 = 0;
        for (int i = 0; i < buses.length; i++) {
            if (buses[i].equals("x"))
                continue;

            int busID = Integer.valueOf(buses[i]);
            int runningTimeStamp = 0;
            while (true) {
                runningTimeStamp += busID;
                if (runningTimeStamp >= earliestDeparture) {
                    if (runningTimeStamp - earliestDeparture < minMinsWaited) {
                        minMinsWaited = runningTimeStamp - earliestDeparture;
                        busPT1 = busID;
                    }
                    break;
                }
            }
        }
        System.out.printf("Part 1 Bus ID: %d with mins waited: %d product is %d\n", busPT1, minMinsWaited,
                busPT1 * minMinsWaited); //

        Long prevBus = Long.valueOf(buses[0]);
        long time = prevBus;
        long lcm = prevBus;
        for (int i = 1; i < buses.length; i++) {
            if (buses[i].equals("x"))
                continue;

            Long busID = Long.valueOf(buses[i]);
            while ((time + i) % busID != 0) {
                time += lcm;
            }
            prevBus = busID;
            lcm *= prevBus;
        }
        System.out.printf("Part 2 Earliest time stamp w/ new rules: %d\n", time); // 560214575859998
    }
}
