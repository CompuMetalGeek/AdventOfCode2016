package adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18 extends ADay {

    private final char SAFE = '.';
    private final char TRAP = '^';
    private int numberOfRows;
    List<String> field;

    public Day18() {
        super(18);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            while (sc.hasNext()) {
                field = new ArrayList<>();
                field.add(sc.nextLine());
                numberOfRows = sc.nextInt();
                sc.nextLine();
                while (field.size() < numberOfRows) {
                    field.add(determineNextRow());
                }
                int count = 0;
                for (String s : field) {
                    count += s.replaceAll("\\" + TRAP + "", "").length();
                }
                System.out.printf("After %d rows, there are %d safe tiles.\n", numberOfRows, count);
            }

        }
    }

    private void printField() {
        for (String s : field) {
            System.out.println(s);
        }
    }

    private boolean isTrap(int i, String line) {
        return line.charAt(i) == TRAP;
    }

    private String determineNextRow() {
        StringBuilder newLine = new StringBuilder();
        String previousLine = field.get(field.size() - 1);
        for (int i = 0; i < previousLine.length(); i++) {
            if (newIsTrap(i, previousLine)) {
                newLine.append(TRAP);
            } else {
                newLine.append(SAFE);
            }
        }
        return newLine.toString();
    }

    private boolean newIsTrap(int i, String previousLine) {

        return conditionCheck(i, previousLine, true, true, false)
                || conditionCheck(i, previousLine, true, false, false)
                || conditionCheck(i, previousLine, false, true, true)
                || conditionCheck(i, previousLine, false, false, true);
    }

    private boolean conditionCheck(int i, String previousLine, boolean leftMustBeTrap, boolean centerMustBeTrap, boolean rightMustBeTrap) { // trap-trap-safe
        try {
            if (leftMustBeTrap != isTrap(i - 1, previousLine)) {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            if (leftMustBeTrap) {
                return false;
            }
        }
        if (centerMustBeTrap != isTrap(i, previousLine)) {
            return false;
        }
        try {
            if (rightMustBeTrap != isTrap(i + 1, previousLine)) {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            if (rightMustBeTrap) {
                return false;
            }
        }
        return true;
    }
}
