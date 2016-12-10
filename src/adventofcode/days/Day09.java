package adventofcode.days;

import java.util.Scanner;

public class Day09 extends ADay {

    private boolean insideCompressed;
    private int numberOfCharacters, numberOfCopies, i;
    StringBuilder sb;

    public Day09() {
        super(9);
        init();
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            String input = sc.nextLine();
            part1(input);
            part2(input);
        }
    }

    private void init() {
        sb = new StringBuilder();
        insideCompressed = false;
        numberOfCharacters = 0;
        numberOfCopies = 0;
        i = 0;
    }

    private String part1(String input) {
        init();
        while (i < input.length()) {
            char next = input.charAt(i);
            i++;
            switch (next) {
                case '(':
                    processOpenBracket(input);
                    break;
                default:
                    if ((next + "").matches("[a-zA-Z0-9\\(\\)]+")) {
                        sb.append(next);
                    }
            }
        }
        System.out.printf("Decompressed size with first method is %d.\n", sb.length()); // 152851
        return sb.toString();
    }

    private void processOpenBracket(String input) {
        if (insideCompressed) {
            sb.append('(');
        } else {
            getMarkerData(input);
            String toCopy = parseData(input);
            for (int j = 0; j < numberOfCopies; j++) {
                sb.append(toCopy);
            }
        }
    }

    private void getMarkerData(String input) {
        String[] markerData = input.substring(i, input.indexOf(')', i)).split("x|\\)");
        i += input.indexOf(')', i) - i + 1;
        numberOfCharacters = Integer.parseInt(markerData[0]);
        numberOfCopies = Integer.parseInt(markerData[1]);

    }

    private String parseData(String input) {
        StringBuilder sbData = new StringBuilder();
        for (int j = 0; j < numberOfCharacters; j++) {
            sbData.append(input.charAt(i++));
        }
        return sbData.toString();
    }

    private void part2(String input) {
        long count = getCount(input);
        System.out.printf("Decompressed size with second method is %d.\n", count); // 152851

    }

    private long getCount(String input) {
        long count = 0;
        int indexOfBracket = input.indexOf('(');
        if (indexOfBracket == -1) {
            return input.length();
        }
        String[] marker = input.substring(indexOfBracket + 1, input.indexOf(')')).split("x");
        if (indexOfBracket != 0) {
            count += getCount(input.substring(0, indexOfBracket));
        }
        count += Integer.parseInt(marker[1]) * getCount(input.substring(input.indexOf(')') + 1, input.indexOf(')') + 1 + Integer.parseInt(marker[0])));
        if (input.indexOf(')') + 1 + Integer.parseInt(marker[0]) < input.length()) {
            count += getCount(input.substring(input.indexOf(')') + 1 + Integer.parseInt(marker[0])));
        }
        return count;
    }
}
