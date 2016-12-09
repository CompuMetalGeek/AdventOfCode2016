package days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day06 extends ADay {

    List<Map<Character, Integer>> frequencies;

    public Day06() {
        super(6);
        init();
    }

    @Override
    public void runDay() {
        init();
        try (Scanner sc = getInput()) {

            while (sc.hasNext()) {
                char[] line = sc.nextLine().toCharArray();
                for (int i = 0; i < line.length; i++) {
                    if (frequencies.size() < i + 1) {
                        frequencies.add(new HashMap<>());
                    }
                    Map<Character, Integer> map = frequencies.get(i);
                    map.put(line[i], map.getOrDefault(line[i], 0) + 1);
                }
            }
            String version1 = "";
            String version2 = "";
            for (Map<Character, Integer> map : frequencies) {
                char c1 = '\0';
                char c2 = '\0';
                int maxFreq = 0;
                int minFreq = -1;
                for (Map.Entry<Character, Integer> e : map.entrySet()) {
                    if (e.getValue() > maxFreq) {
                        c1 = e.getKey();
                        maxFreq = e.getValue();
                    }
                    if (e.getValue() < minFreq || minFreq == -1) {
                        c2 = e.getKey();
                        minFreq = e.getValue();
                    }
                }
                version1 += c1;
                version2 += c2;
            }
            System.out.printf("Error-corrected version of most occuring letters is '%s'\n", version1);
            System.out.printf("Error-corrected version of least occuring letters is '%s'\n", version2);
        }
    }

    private void init() {
        frequencies = new ArrayList<>();
    }

}
