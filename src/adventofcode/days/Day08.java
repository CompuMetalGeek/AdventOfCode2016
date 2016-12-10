package adventofcode.days;

import java.util.Scanner;

public class Day08 extends ADay {

    boolean[][] console;
    final int consoleX = 50, consoleY = 6;

    public Day08() {
        super(8);
        init();
    }

    @Override
    public void runDay() {
        init();
        try (Scanner sc = getInput()) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                switch (line[0]) {
                    case "rect":
                        fillRect(line[1]);
                        break;
                    case "rotate":
                        rotate(line[2], line[4]);
                        break;
                    case "default":
                        break;
                }
            }
            System.out.println("There are " + countSquares() + " squares.");
            printConsole();
        }
    }

    private void init() {
        console = new boolean[consoleY][consoleX];
    }

    private void fillRect(String dim) {
        String[] dims = dim.split("x");
        int x = Integer.parseInt(dims[0]), y = Integer.parseInt(dims[1]);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                console[i][j] = true;
            }
        }
    }

    private void rotate(String colRow, String distance) {
        String[] colRows = colRow.split("=");
        int dist = Integer.parseInt(distance);
        int number = Integer.parseInt(colRows[1]);
        if (colRows[0].equals("y")) { // houd kolom vast en roteer over x
            boolean[] newRow = new boolean[console[number].length];
            for (int i = 0; i < console[number].length; i++) {
                newRow[(i + dist) % console[number].length] = console[number][i];
            }
            for (int i = 0; i < console[number].length; i++) {
                console[number][i] = newRow[i];
            }
        } else { // houd kolom vast en roteer over y
            boolean[] newRow = new boolean[console.length];
            for (int i = 0; i < console.length; i++) {
                newRow[(i + dist) % console.length] = console[i][number];
            }
            for (int i = 0; i < console.length; i++) {
                console[i][number] = newRow[i];
            }

        }
    }

    private int countSquares() {
        int count = 0;
        for (int i = 0; i < console.length; i++) {
            for (int j = 0; j < console[i].length; j++) {
                if (console[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void printConsole() {
        for (int i = 0; i < console.length; i++) {
            for (int j = 0; j < console[i].length; j++) {
                System.out.print(console[i][j] ? "X" : "\u00A0");
            }
            System.out.println("");
        }
    }
}
