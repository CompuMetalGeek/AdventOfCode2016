package adventofcode.days;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Day11 extends ADay {

    Map<String, Integer> indexOf;
    int[] generatorLocations;
    int[] chipLocations;
    int elevatorLocation;

    public Day11() {
        super(11);
    }

    public void init(int capacity) {
        indexOf = new HashMap<>();
        generatorLocations = new int[capacity];
        chipLocations = new int[capacity];
        elevatorLocation = 1;
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            init(5);
            int freeIndex = 0;
            int level = 1;
            while (sc.hasNextLine()) {

                String[] line = sc.nextLine().split(" and a |^The .* contains a |, (and )*a |\\.");
                for (int i = 1; i < line.length; i++) {
                    String element = line[i].substring(0, 2);
                    // check if element has been assigned index
                    if (!indexOf.containsKey(element)) {
                        indexOf.put(element, freeIndex);
                        freeIndex++;
                    }
                    // store the current level of the object
                    if (line[i].endsWith("generator")) {
                        generatorLocations[indexOf.get(element)] = level;
                    } else {
                        chipLocations[indexOf.get(element)] = level;
                    }
                }
                level++;
            }
            printOverview();
        }
    }

    private void printOverview() {
        for (int i = 4; i >0; i--) {
            System.out.printf("F%d  %s  ", i, elevatorLocation == i ? "E" : ".");
            for (Entry<String, Integer> entry : indexOf.entrySet()) {
                System.out.printf("%s  %s  ", generatorLocations[entry.getValue()] == i ? entry.getKey()+"G" : " . ", chipLocations[entry.getValue()] == i ? entry.getKey()+"M" : " . ");
            }
            System.out.println("");
        }
    }
}
