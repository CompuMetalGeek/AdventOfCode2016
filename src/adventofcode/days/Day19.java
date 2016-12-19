package adventofcode.days;

import java.util.Scanner;

public class Day19 extends ADay {

    public Day19() {
        super(19);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            while (sc.hasNext()) {
                int number = sc.nextInt();

                int winner = calculateWinner(number);
                System.out.printf("With %d elves, elf %d gets all the presents.\n", number, winner);

                int winner2 = calculateWinner2(number);
                System.out.printf("With %d elves and the new system, elf %d gets all the presents.\n", number, winner2);
            }
        }
    }

    private int calculateWinner(int number) {
        int powerOfTwo = 1;
        while (powerOfTwo <= number) {
            powerOfTwo *= 2;
        }
        powerOfTwo /= 2;
        return (number - powerOfTwo) * 2 + 1;
    }

    private int calculateWinner2(int number) {
        int number2 = 1;
        int winner2 = 1;
        boolean goDouble = false;
        while (number2 < number) {
            if (number2 == winner2) {
                winner2 = 1;
                goDouble = false;
            } else if (winner2 == number2 * 1.0 / 2) {
                goDouble = true;
                winner2 += 2;
            } else if (goDouble) {
                winner2 += 2;
            } else {
                winner2++;
            }
            number2++;
        }
        return winner2;
    }

}
