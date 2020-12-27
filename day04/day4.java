import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/4
 */
public class day4 {
    public static void main(String[] args) {
        String fileName = args[0];
        int numValidPassports = 0;
        int numValidPasswordsPT2 = 0;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (String line; (line = br.readLine()) != null;) {
                sb.append(line + " ");

                if (line.isEmpty()) {
                    String passportTxt = sb.toString();
                    numValidPassports = validatePassport(passportTxt) ? numValidPassports + 1 : numValidPassports;
                    numValidPasswordsPT2 = validatePassportAndFields(passportTxt) ? numValidPasswordsPT2 + 1
                            : numValidPasswordsPT2;
                    sb.setLength(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Handle the last passport since no newline for EOF.
        numValidPassports = validatePassport(sb.toString()) ? numValidPassports + 1 : numValidPassports;
        numValidPasswordsPT2 = validatePassportAndFields(sb.toString()) ? numValidPasswordsPT2 + 1
                : numValidPasswordsPT2;

        System.out.printf("Part 1 Number of valid passports: %d\n", numValidPassports); // 210
        System.out.printf("Part 2 Number of valid passports w/ field validation: %d\n", numValidPasswordsPT2); // 131
    }

    private static boolean validatePassport(String passportTxt) {
        String[] fields = passportTxt.split(" ");
        boolean isValid = false;

        Map<String, String> map = new HashMap<>();
        for (String field : fields) {
            String[] split = field.split(":");
            map.put(split[0], split[1]);
        }

        if (map.size() == 7 && !map.containsKey("cid"))
            isValid = true;
        else if (map.size() == 8)
            isValid = true;

        return isValid;
    }

    private static boolean validatePassportAndFields(String passportTxt) {
        String[] fields = passportTxt.split(" ");
        boolean isValid = false;

        Map<String, String> map = new HashMap<>();
        for (String field : fields) {
            String[] split = field.split(":");
            map.put(split[0], split[1]);
        }

        if (map.size() == 7 && !map.containsKey("cid")) {
            isValid = validateFields(map);
        } else if (map.size() == 8) {
            isValid = validateFields(map);
        }

        return isValid;
    }

    // individual fields can be extracted to own validation later
    private static boolean validateFields(Map<String, String> passport) {
        Integer birthYear = Integer.valueOf(passport.get("byr"));
        Integer issYear = Integer.valueOf(passport.get("iyr"));
        Integer expYear = Integer.valueOf(passport.get("eyr"));
        Set<String> eyeColors = new HashSet<>();
        eyeColors.add("amb");
        eyeColors.add("blu");
        eyeColors.add("brn");
        eyeColors.add("gry");
        eyeColors.add("grn");
        eyeColors.add("hzl");
        eyeColors.add("oth");

        Pattern p = Pattern.compile("^#([a-f0-9]{6})");
        Matcher m = p.matcher(passport.get("hcl"));

        if (birthYear < 1920 || birthYear > 2002)
            return false;

        if (issYear < 2010 || issYear > 2020)
            return false;

        if (expYear < 2020 || expYear > 2030)
            return false;
        if (!eyeColors.contains(passport.get("ecl")))
            return false;
        if (passport.get("pid").length() != 9)
            return false;
        if (!m.matches())
            return false;

        String hgt = passport.get("hgt");
        int value = Integer.valueOf(hgt.replaceAll("[^0-9]", ""));
        if (hgt.contains("in")) {
            if (value < 59 || value > 76)
                return false;
        } else if (hgt.contains("cm")) {
            if (value < 150 || value > 193)
                return false;
        } else {
            return false;
        }
        return true;
    }
}
