package adventofcode.days;

import java.util.Scanner;
import java.util.TreeSet;

public class Day01 extends ADay {

    int x, y;
    TreeSet<String> locations;

    public Day01() {
        super(1);
        init();
    }

    @Override
    public void runDay() {
        init();
        try (Scanner sc = getInput(", |\n")) {

            int direction = 0;
            boolean firstDoubleLocationFound = false;
            String doubleVisitedLocation = "";
            while (sc.hasNext()) {
                String opdracht = sc.next();

                if (opdracht.startsWith("R")) {
                    direction++;
                } else {
                    direction--;
                }
                direction = Math.floorMod(direction, 4);
                int distance = Integer.parseInt(opdracht.substring(1));
                for (int i = 0; i < distance; i++) {
                    switch (direction) {
                        case 0:
                            y++;
                            break;
                        case 1:
                            x++;
                            break;
                        case 2:
                            y--;
                            break;
                        case 3:
                            x--;
                            break;
                    }
                    String currentLocation = "" + x + "," + y;
                    if (!firstDoubleLocationFound && locations.contains(currentLocation)) {
                        doubleVisitedLocation = "Distance to first doubled location (" + currentLocation + ") is " + (Math.abs(x) + Math.abs(y));
                        firstDoubleLocationFound = true;
                    }
                    locations.add(currentLocation);
                }
            }

            System.out.println("Distance to final location (" + x + "," + y + ") is " + (Math.abs(x) + Math.abs(y)));
            System.out.println(doubleVisitedLocation);
        }
    }

    private void init() {
        x = 0;
        y = 0;
        locations = new TreeSet<>();
    }

}
