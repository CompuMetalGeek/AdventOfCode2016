package adventofcode.days;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day12 extends ADay {

    Map<Integer, String> lines;
    Map<String, Integer> registers;
    int programCounter;

    public Day12() {
        super(12);
    }

    private void init() {
        lines = new HashMap<>();
        initRegisters(0, 0, 0, 0);
        programCounter = 0;
    }

    private void initRegisters(int a, int b, int c, int d) {
        registers = new HashMap<>();
        registers.put("a", a);
        registers.put("b", b);
        registers.put("c", c);
        registers.put("d", d);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            int i = 0;
            init();
            while (sc.hasNextLine()) {
                lines.put(i, sc.nextLine());
                i++;
            }
            runProgram(0, 0, 0, 0);
            runProgram(0, 0, 1, 0);

        }
    }

    private void copy(String from, String to) {
        try {
            int value = Integer.parseInt(from);
            registers.put(to, value);
        } catch (NumberFormatException e) {
            registers.put(to, registers.get(from));
        }
        programCounter++;
    }

    private void increase(String string) {
        registers.put(string, registers.get(string) + 1);
        programCounter++;
    }

    private void decrease(String string) {
        registers.put(string, registers.get(string) - 1);
        programCounter++;
    }

    private void jumpIfNotZero(String register, int distance) {
        int value;
        try {
            value = Integer.parseInt(register);
        } catch (NumberFormatException e) {
            value = registers.get(register);
        }
        if (value != 0) {
            programCounter += distance;
        } else {
            programCounter++;
        }
    }

    private void runProgram(int a, int b, int c, int d) {
        initRegisters(a, b, c, d);
        programCounter = 0;
        while (programCounter < lines.size()) {
            String line[] = lines.get(programCounter).split(" ");
            switch (line[0]) {
                case "cpy":
                    copy(line[1], line[2]);
                    break;
                case "inc":
                    increase(line[1]);
                    break;
                case "dec":
                    decrease(line[1]);
                    break;
                case "jnz":
                    jumpIfNotZero(line[1], Integer.parseInt(line[2]));
                    break;
            }

        }
        System.out.printf("Register 'a' contains %d.\n", registers.get("a"));
    }

}
