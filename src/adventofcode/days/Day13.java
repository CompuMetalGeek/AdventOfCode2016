package adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 extends ADay {

    List<List<Integer>> building;
    int polynomialConstant;

    public Day13() {
        super(13);
    }

    private void init() {
        building = new ArrayList<>();
        polynomialConstant = 0;
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            init();
            polynomialConstant = sc.nextInt();
            int startX = 1, startY = 1;
            int finalX = 31, finalY = 39;
            int maxCount = 50;
            resizeBuilding(finalX + 10, finalY + 10);
            System.out.printf("You can reach (%d,%d) in %d steps.\n", finalX, finalY, floodUntilReached(startX, startY, finalX, finalY, maxCount));
            int locations = 0;
            for (List<Integer> line : building) {
                for (int location : line) {
                    if (0 <= location && location < maxCount + 1) {
                        locations++;
                    }
                }
            }

            System.out.printf("you can reach %d locations in at most %d steps.\n", locations, maxCount);
        }

    }

    private boolean isWall(int x, int y) {
        int value = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + polynomialConstant;
        return Integer.bitCount(value) % 2 == 1;
    }

    private void resizeBuilding(int x, int y) {
        if (x < building.size() && y < building.get(0).size()) {
            return;
        }
        if (x >= building.size()) {
            while (x > building.size()) {
                building.add(new ArrayList<>());
            }
        }
        for (int i = 0; i < building.size(); i++) {
            List<Integer> row = building.get(i);
            while (y > row.size()) {
                row.add(isWall(row.size(), i) ? -2 : -1);
            }
        }
    }

    private void printBuilding(int srcX, int srcY, int goalX, int goalY) {
        for (int i = 0; i < building.size(); i++) {
            for (int j = 0; j < building.get(i).size(); j++) {
                int location = building.get(i).get(j);
                String representation;
                switch (location) {
                    case -2:
                        representation = "#";
                        break;
                    case -1:
                        representation = ".";
                        break;
                    default:
                        representation = location + "";
                        break;
                }
                if (j == srcX && i == srcY) {
                    representation = "O";
                } else if (j == goalX && i == goalY) {
                    representation = "X";

                }
                System.out.printf("%2s", representation);
            }
            System.out.println("");
        }
    }

    private int floodUntilReached(int startX, int startY, int finalX, int finalY, int maxCount) {
        int counter = 0;
        boolean hasMaxCount = false;
        while (!hasMaxCount || building.get(finalY).get(finalX) == -1) {
            if (building.get(startY).get(startX) == -1) {
                building.get(startY).set(startX, 0);
            } else {
                int iSize = building.size();
                int jSize = building.get(0).size();
                for (int i = 0; i < iSize; i++) {
                    List<Integer> row = building.get(i);
                    for (int j = 0; j < jSize; j++) {
                        if (row.get(j) == -2) {
                            continue;
                        }
                        row.set(j, copyHighesNeighbour(j, i, counter));
                        if (!hasMaxCount) {
                            hasMaxCount = maxCount == row.get(j);
                        }

                    }
                }
                counter++;
            }
            //printBuilding();
        }
        return building.get(finalY).get(finalX);
    }

    private int copyHighesNeighbour(int x, int y, int max) {
        if (building.get(y).get(x) != -1) {
            return building.get(y).get(x);
        }
        int value = -1;
        try {
            if (building.get(y + 1).get(x) > value && building.get(y + 1).get(x) < max) {
                value = building.get(y + 1).get(x) + 1;
            }
        } catch (IndexOutOfBoundsException e) {
            resizeBuilding(x + 5, y + 5);
        }
        try {
            if (building.get(y).get(x + 1) > value && building.get(y).get(x + 1) < max) {
                value = building.get(y).get(x + 1) + 1;
            }
        } catch (IndexOutOfBoundsException e) {
            resizeBuilding(x + 5, y + 5);
        }
        try {
            if (building.get(y - 1).get(x) > value && building.get(y - 1).get(x) < max) {
                value = building.get(y - 1).get(x) + 1;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (building.get(y).get(x - 1) > value && building.get(y).get(x - 1) < max) {
                value = building.get(y).get(x - 1) + 1;
            }
        } catch (IndexOutOfBoundsException e) {
            resizeBuilding(x + 10, y + 10);
        }
        return value;

    }

}
