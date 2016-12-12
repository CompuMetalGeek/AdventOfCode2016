package adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Day11 extends ADay {

    List<List<String>> building;
    int elevatorLocation;

    public Day11() {
        super(11);
    }

    public void init() {
        building = new ArrayList<>();
        elevatorLocation = 1;
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            init();
            int level = 0;
            while (sc.hasNextLine()) {
                List<String> levelArray = new ArrayList<>();
                String[] line = sc.nextLine().split(" and a |^The .* contains a |, (and )*a |\\.");
                for (int i = 1; i < line.length; i++) {
                    levelArray.add(line[i].substring(0, 2) + "-" + line[i].substring(line[i].lastIndexOf(" ")+1,line[i].lastIndexOf(" ")+2).toUpperCase());
                }
                level++;
                building.add(levelArray);
            }
            printOverview();
        }
    }

    private void printOverview() {
        for (int i = building.size(); i > 0; i--) {
            List<String> level = building.get(i - 1);
            System.out.print("F" + i + " " + (elevatorLocation == i - 1 ? "E" : " ") + "  ");
            for (int j = 0; j < level.size(); j++) {
                System.out.print(level.get(j) + "  ");
            }
            System.out.println("");
        }
    }
    
}
