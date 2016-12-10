package adventofcode.days;

import java.util.Scanner;

public class Day03 extends ADay {

    public Day03() {
        super(3);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            int possibleTrianglesHorizontal = 0;
            int possibleTrianglesVertical = 0;
            int counter = 0;
            int[] triangle1 = new int[3];
            int[] triangle2 = new int[3];
            int[] triangle3 = new int[3];
            while (sc.hasNext()) {
                int[] triangle = {sc.nextInt(), sc.nextInt(), sc.nextInt()};
                if (validTriangle(triangle)) {
                    possibleTrianglesHorizontal++;
                }
                triangle1[counter] = triangle[0];
                triangle2[counter] = triangle[1];
                triangle3[counter] = triangle[2];
                counter++;
                if (counter == 3) {
                    counter = 0;
                    if (validTriangle(triangle1)) {
                        possibleTrianglesVertical++;
                    }
                    if (validTriangle(triangle2)) {
                        possibleTrianglesVertical++;
                    }
                    if (validTriangle(triangle3)) {
                        possibleTrianglesVertical++;
                    }
                }
            }
            System.out.println("Possible horizontal triangles: " + possibleTrianglesHorizontal);
            System.out.println("Possible vertical triangles: " + possibleTrianglesVertical);
        }
    }

    private boolean validTriangle(int[] triangle) {
        if (triangle[0] < triangle[1] + triangle[2] && Math.abs(triangle[2] - triangle[1]) < triangle[0]) {
            return true;
        }
        return false;
    }

}
