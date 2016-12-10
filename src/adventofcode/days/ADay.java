package adventofcode.days;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class ADay {

    private final int dayNumber;
    private final String inputLocation;

    /**
     * Generates a day-object with the specified <b>dayNumber</b>.
     *
     * @param dayNumber
     */
    public ADay(int dayNumber) {
        this.dayNumber = dayNumber;
        this.inputLocation = "inputs/day" + dayNumber;
    }

    /**
     * Checks if input file for this day is available.
     *
     * @return the availability of the input file
     */
    public boolean hasInputFile() {
        if (this.getClass().getClassLoader().getResource(inputLocation) == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns a Scanner-object for the input with the given <b>delimiter</b>.
     *
     * @param delimiter
     * @return Scanner for the input
     */
    public Scanner getInput(String delimiter) {
        return (new Scanner(getInputData())).useDelimiter(delimiter);
    }

    /**
     * Returns a Scanner-object for the input with the default delimiter.
     *
     * @return Scanner for the input
     */
    public Scanner getInput() {
        return new Scanner(getInputData());
    }

    private InputStream getInputData() {
        return this.getClass().getClassLoader().getResourceAsStream(inputLocation);
    }

    /**
     * Must be overridden with actual task by a subclass of ADay.
     */
    protected abstract void runDay();

    /**
     * Executes the task for this day.
     *
     * @throws FileNotFoundException when the input could not be found.
     */
    public void run() throws FileNotFoundException {
        if (!hasInputFile()) {
            throw new FileNotFoundException();
        }
        runDay();
    }

    @Override
    public String toString() {
        return "Day " + dayNumber;
    }

}
