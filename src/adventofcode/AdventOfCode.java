package adventofcode;

import adventofcode.days.ADay;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCode {

    private List<ADay> classes;

    public AdventOfCode() {
        loadClasses();
    }

    /**
     * Loads all Day-classes that have a valid input file.
     */
    private void loadClasses() {
        classes = new ArrayList<>();
            for (int i = 1; i < 32; i++) {
                try {
                    ADay day = (ADay) Class.forName("adventofcode.days.Day" + (i < 10 ? "0" + i : i)).getConstructor().newInstance(); // get instance of class
                    if (day.hasInputFile()) { // class has input file
                        classes.add(day);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) { // problem with creation of class
                    System.out.println("Could not create an instance of class 'adventofcode.days.Day" + i + "'.");
                    System.out.println(e.toString());
                }
            }
    }

    /**
     * Runs a specified day or throws an <b>ArrayIndexOutOfBoundsException</b>
     * when day does not exist.
     *
     * @param i number of the day
     * @throws ArrayIndexOutOfBoundsException number of the day is not a valid
     * number
     */
    public void runDay(int i) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || classes.size() <= i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        try {
            ADay day = classes.get(i);
            System.out.println("--- " + day.toString() + " ---");
            day.run();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input File Was Not Found.");
        }

    }

    /**
     * Runs the latest day that is available.
     */
    public void runLatestDay() {
        if (classes.size() > 0) {
            runDay(classes.size() - 1);
        }
    }

    /**
     * Runs all available days.
     */
    public void runAllDays() {
        for (int i = 0; i < classes.size(); i++) {
            runDay(i);
        }
    }

    /**
     * Returns the number of days are available, starting from Day 1
     */
    public int workingDays() {
        return classes.size();
    }

    public static void main(String[] args) {
        AdventOfCode aoc = new AdventOfCode();
        if (args.length == 0) {
            System.out.println("NO ARGUMENTS FOUND: running the latest available day. Run this program with the argument '-h' for more info.");
            aoc.runLatestDay();
        } else if (args.length > 0) {
            int i = 0;
            try {
                while (i < args.length) {
                    switch (args[i]) {
                        case "-a":
                        case "--all":
                            aoc.runAllDays();
                            break;
                        case "-l":
                        case "--latest":
                            aoc.runLatestDay();
                            break;
                        case "-d":
                        case "--day":
                            int day = Integer.parseInt(args[i + 1]) - 1;
                            i++;
                            aoc.runDay(day);
                            break;
                        case "-h":
                        case "/?":
                        case "--help":
                            aoc.showHelp();
                            break;
                        default:
                            throw new UnsupportedOperationException(args[i]);
                    }
                    i++;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: argument after '-d' should be a number.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("This day does not exist. You must enter a number between 1 and " + aoc.workingDays() + " after '-d'.");
            } catch (UnsupportedOperationException e) {
                System.out.printf("ERROR: Invalid syntax, '%s' is not a valid argument.\n", e.getMessage());
                System.out.println("Please run this program with one or more of the following arguments:");
                aoc.showHelp();
            }
        }
    }

    /**
     * Prints instructions to the standard output channel.
     */
    private void showHelp() {
        System.out.println("");
        System.out.println("'/?'");
        System.out.println("--help");
        System.out.printf("%-20s%s\n\n", "'-h'", "Show this help page.");
        System.out.println("'--all'");
        System.out.printf("%-20s%s\n\n", "'-a'", "Run all possible days.");
        System.out.println("'--latest'");
        System.out.printf("%-20s%s\n\n", "'-l'", "Run the last created day without the hint.");
        System.out.println("'--day number'");
        System.out.printf("%-20s%s\n\n", "'-d number'", "Run day 'number'.");
    }
}
