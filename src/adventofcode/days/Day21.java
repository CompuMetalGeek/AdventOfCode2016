package adventofcode.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day21 extends ADay {

    String password;

    public Day21() {
        super(21);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            password = "abcdefgh";
            List<String> lines = new ArrayList<>();
            while (sc.hasNext()) {
                lines.add(sc.nextLine());
            }
            scramble(lines);
            Collections.reverse(lines);
            password = "fbgdceah";
            unscramble(lines);
        }
    }

    private void swap(int x, int y) {
        char[] string = password.toCharArray();
        char c = string[x];
        string[x] = string[y];
        string[y] = c;
        password = String.valueOf(string);
    }

    private void swapChars(char x, char y) {
        password = password.replace(x, '$');
        password = password.replace(y, x);
        password = password.replace('$', y);
    }

    private void rotate(boolean left, int count) {
        StringBuilder sb = new StringBuilder();
        if (!left) {
            count = -count;
        }
        int length = password.length();
        for (int i = 0; i < length; i++) {
            sb.append(password.charAt((i + count + length * 3) % length));
        }
        password = sb.toString();

    }

    private void rotate(char c) {
        int count = password.indexOf(c) + 1;
        if (password.indexOf(c) > 3) {
            count++;

        }
        rotate(false, count);
    }

    private void reverse(int x, int y) {
        if (x > y) {
            reverse(y, x);
        } else {
            StringBuilder sb = new StringBuilder(password.substring(x, y + 1));
            sb.reverse();
            for (int i = x - 1; -1 < i; i--) {
                sb.insert(0, password.charAt(i));
            }
            for (int i = y + 1; i < password.length(); i++) {
                sb.append(password.charAt(i));
            }
            password = sb.toString();
        }
    }

    private void move(int x, int y) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            if (i != x) {
                sb.append(password.charAt(i));
            }
        }
        sb.insert(y, password.charAt(x));
        password = sb.toString();
    }

    private void scramble(List<String> lines) {
        for (String l : lines) {
            String inputLine = l;
            String[] line = inputLine.split(" ");
            switch (line[0]) {
                case "swap":
                    if (line[1].equalsIgnoreCase("position")) {
                        swap(Integer.parseInt(line[2]), Integer.parseInt(line[5]));
                    } else {
                        swapChars(line[2].charAt(0), line[5].charAt(0));
                    }
                    break;
                case "rotate":
                    if (line[1].equalsIgnoreCase("based")) {
                        rotate(line[6].charAt(0));
                    } else {
                        rotate(line[1].equalsIgnoreCase("left"), Integer.parseInt(line[2]));
                    }
                    break;
                case "reverse":
                    reverse(Integer.parseInt(line[2]), Integer.parseInt(line[4]));
                    break;
                case "move":
                    move(Integer.parseInt(line[2]), Integer.parseInt(line[5]));
                    break;
                default:
                    System.out.println(line[0]);
            }
            //System.out.printf("%-40s changes the password to %s.\n", inputLine, password);
        }
        System.out.printf("The password is now '%s'.\n", password);
    }

    private void unscramble(List<String> lines) {
        for (String l : lines) {
            String inputLine = l;
            String[] line = inputLine.split(" ");
            switch (line[0]) {
                case "swap":
                    if (line[1].equalsIgnoreCase("position")) {
                        unswap(Integer.parseInt(line[2]), Integer.parseInt(line[5]));
                    } else {
                        unswapChars(line[2].charAt(0), line[5].charAt(0));
                    }
                    break;
                case "rotate":
                    if (line[1].equalsIgnoreCase("based")) {
                        unrotate(line[6].charAt(0));
                    } else {
                        unrotate(line[1].equalsIgnoreCase("left"), Integer.parseInt(line[2]));
                    }
                    break;
                case "reverse":
                    unreverse(Integer.parseInt(line[2]), Integer.parseInt(line[4]));
                    break;
                case "move":
                    unmove(Integer.parseInt(line[2]), Integer.parseInt(line[5]));
                    break;
                default:
                    System.out.println(line[0]);
            }
            //System.out.printf("%-40s changes the password to %s.\n", inputLine, password);
        }
        System.out.printf("The old password was '%s'.\n", password);
    }

    private void unswap(int x, int y) {
        swap(x, y);
    }

    private void unswapChars(char x, char y) {
        swapChars(x, y);
    }

    private void unrotate(char c) {
        int index = password.indexOf(c);
        int count = 0;
        switch (index) {
            case 0:
            case 1:
                count = 7;
                break;
            case 2:
                count = 2;
                break;
            case 3:
                count = 6;
                break;
            case 4:
                count = 1;
                break;
            case 5:
                count = 5;
                break;
            case 6:
                count = 0;
                break;
            case 7:
                count = 4;
                break;
        }
        rotate(false, count);
    }

    private void unrotate(boolean left, int count) {
        rotate(!left, count);
    }

    private void unreverse(int x, int y) {
        reverse(x, y);
    }

    private void unmove(int x, int y) {
        move(y, x);
    }
}
