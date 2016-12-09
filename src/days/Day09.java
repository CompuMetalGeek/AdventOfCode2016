package days;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day09 extends ADay {

    private boolean insideMarker, insideCompressed;
    private int numberOfCharacters, numberOfCopies;
    StringBuilder sb;

    public Day09() {
        super(9);
        init();
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput("")) {
            while (sc.hasNext()) {
                String next = sc.next();
                switch (next) {
                    case "(":
                        processOpenBracket(sc);
                        break;
                    default:
                        if(next.matches("[a-zA-Z0-9\\(\\)]+"))
                        sb.append(next);
                }
            }

            System.out.printf("%d characters in %s\n",sb.length(),sb.toString());
        }
    }

    private void init() {
        sb = new StringBuilder();
        insideCompressed = false;
        insideMarker = false;
        numberOfCharacters = 0;
        numberOfCopies = 0;
    }

    private void processOpenBracket(Scanner sc) {
        if (insideCompressed) {
            sb.append('(');
        } else {
            getMarkerData(sc);
            String toCopy = parseData(sc);
            for (int i = 0; i < numberOfCopies; i++) {
                sb.append(toCopy);
            }
        }
    }

    private void getMarkerData(Scanner sc) {
        Pattern p = Pattern.compile("\\d*x\\d*\\)");
        String[] markerData = sc.findInLine(p).split("x|\\)");
        numberOfCharacters = Integer.parseInt(markerData[0]);
        numberOfCopies = Integer.parseInt(markerData[1]);

    }

    private String parseData(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharacters; i++) {
            sb.append(sc.next());
        }
        return sb.toString();
    }

}
