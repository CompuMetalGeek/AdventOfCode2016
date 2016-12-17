package adventofcode.days;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day17 extends ADay {

    class Directions {

        List<Character> representations;
        List<Integer> values;
        List<Character> oppositeDirections;

        public Directions() {
            this.representations = new ArrayList<>();
            this.oppositeDirections = new ArrayList<>();
        }

        public void AddDirection(char representation, char oppositeDirection) {
            representations.add(representation);
            oppositeDirections.add(oppositeDirection);
        }

        public char getRepresentation(int value) {
            return representations.get(value);
        }

        public int getValue(char representation) {
            return representations.indexOf(representation);
        }

        public char getOppositeDirection(int value) {
            return oppositeDirections.get(value);
        }

        public char getOppositeDirection(char direction) {
            return oppositeDirections.get(representations.indexOf(direction));
        }

    }

    private int xStart, yStart, xFinish, yFinish, x, y, xMax, yMax, xMin, yMin;
    private StringBuilder input;
    private MessageDigest md;
    private Directions directions;
    private List<Character> openDoorValues;
    private String bestPath;
    private String worstPath;

    public Day17() {
        super(17);
    }

    private void init() {
        xMax = 4;
        yMax = 4;
        xMin = 1;
        yMin = 1;
        xStart = 1;
        yStart = 1;
        xFinish = 4;
        yFinish = 4;
        x = xStart;
        y = yStart;

        input = new StringBuilder();

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        directions = new Directions();
        directions.AddDirection('U', 'D');
        directions.AddDirection('D', 'U');
        directions.AddDirection('L', 'R');
        directions.AddDirection('R', 'L');

        openDoorValues = new ArrayList<>();
        openDoorValues.addAll(Arrays.asList('b', 'c', 'd', 'e', 'f'));
        bestPath = "";
        worstPath = "";
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            while (sc.hasNext()) {
                init();
                input.append(sc.nextLine()); // get hashSeed
                chooseMove();
                String bestPart = bestPath.replaceAll("[^UDLR]", "");
                int maxLength = 15;
                bestPart = bestPart.length() < maxLength ? bestPart : bestPart.substring(0, maxLength - 2) + "..";
                System.out.printf("The shortest path with input '%-10s' is '%-15s'(length = %-4d).\n", bestPath.replaceAll("[UDLR]", ""), bestPart, bestPath.replaceAll("[^UDLR]", "").length());
                String worstPart = worstPath.replaceAll("[^UDLR]", "");
                worstPart = worstPart.length() < maxLength ? worstPart : worstPart.substring(0, maxLength - 2) + "..";
                System.out.printf("The longest  path with input '%-10s' is '%-15s'(length = %-4d).\n", worstPath.replaceAll("[UDLR]", ""), worstPart, worstPath.replaceAll("[^UDLR]", "").length());
            }
        }
    }

    private String generateHash() {
        byte[] bytes = md.digest(input.toString().getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.substring(0, 4);
    }

    private List<Character> getDirections() {
        List<Character> l = new ArrayList<>();
        String hash = generateHash();
        if (x < xMax && doorIsOpen(hash, directions.getValue('R'))) {
            l.add('R');
        }
        if (x > xMin && doorIsOpen(hash, directions.getValue('L'))) {
            l.add('L');
        }
        if (y < yMax && doorIsOpen(hash, directions.getValue('D'))) {
            l.add('D');
        }
        if (y > yMin && doorIsOpen(hash, directions.getValue('U'))) {
            l.add('U');
        }

        return l;
    }

    private boolean doorIsOpen(String hash, int i) {
        return openDoorValues.contains(hash.charAt(i));
    }

    private void move(char direction) {
        switch (direction) {
            case 'U':
                y--;
                break;
            case 'D':
                y++;
                break;
            case 'L':
                x--;
                break;
            case 'R':
                x++;
                break;
        }

    }

    private void chooseMove() {
        if (x == xFinish && y == yFinish) {
            if (bestPath.length() > input.length() || bestPath.length() == 0) {
                bestPath = input.toString();
            }
            if (worstPath.length() < input.length() || bestPath.length() == 0) {
                worstPath = input.toString();
            }
        } else {
            List<Character> possibleDirections = getDirections();
            for (char direction : possibleDirections) {
                move(direction);
                input.append(direction);
                chooseMove();
                move(directions.getOppositeDirection(direction));
                input.deleteCharAt(input.length() - 1);
            }
        }
    }

}
