package adventofcode.days;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day10 extends ADay {

    Map<Integer, Bot> bots; // positive keys are bots, negative keys are outputs

    public Day10() {
        super(10);
    }

    @Override
    public void runDay() {
        init();
        try (Scanner sc = getInput()) {
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(" ");
                if (line[0].equalsIgnoreCase("bot")) {
                    parseLineAsBot(line);
                } else {
                    parseLineAsInput(line);
                }
            }
            int valueOut0 = bots.get(-1).input1, valueOut1 = bots.get(-2).input1, valueOut2 = bots.get(-3).input1;
            System.out.printf("The product of outputs 0 (val=%d), 1 (val=%d) and 2 (val=%d) is %d.\n",valueOut0 , valueOut1 , valueOut2, valueOut0 * valueOut1 * valueOut2);

        }
    }

    private void init() {
        bots = new HashMap<>();
    }

    private void parseLineAsBot(String[] line) {
        int number = Integer.parseInt(line[1]);
        bots.putIfAbsent(number, new Bot(number));
        Bot thisBot = bots.get(number);

        int lowBotNumber = Integer.parseInt(line[6]);
        if (!line[5].equalsIgnoreCase("bot")) {
            lowBotNumber = -lowBotNumber - 1;
        }

        bots.putIfAbsent(lowBotNumber, new Bot(lowBotNumber));
        Bot lowBot = bots.get(lowBotNumber);
        thisBot.setLowOutputBot(lowBot);

        int highBotNumber = Integer.parseInt(line[11]);
        if (!line[10].equalsIgnoreCase("bot")) {
            highBotNumber = -highBotNumber - 1;
        }
        bots.putIfAbsent(highBotNumber, new Bot(highBotNumber));
        Bot highBot = bots.get(highBotNumber);
        thisBot.setHighOutputBot(highBot);
    }

    private void parseLineAsInput(String[] line) {
        int value = Integer.parseInt(line[1]);
        int botNumber = Integer.parseInt(line[5]);
        bots.putIfAbsent(botNumber, new Bot(botNumber));
        Bot b = bots.get(botNumber);
        b.setInput(value);
    }

    class Bot {

        private final int botNumber;
        private int input1, input2;
        private Bot lowOutputBot, highOutputBot;
        private boolean hasRun;

        public Bot(int botNumber) {
            this.botNumber = botNumber;
            input1 = -1;
            input2 = -1;
            lowOutputBot = null;
            highOutputBot = null;
            hasRun = false;
        }

        public boolean hasNeededMicroChips(int i, int j) {
            try {
                if ((getLowOutput() == i && getHighOutput() == j) || (getLowOutput() == j && getHighOutput() == i)) {
                    return true;
                }
            } catch (RuntimeException e) {
            }
            return false;
        }

        public void setLowOutputBot(Bot lowOutputBot) {
            this.lowOutputBot = lowOutputBot;
            runBot();

        }

        public void setHighOutputBot(Bot highOutputBot) {
            this.highOutputBot = highOutputBot;
            runBot();

        }

        private void setInput(int value) {
            if (this.botNumber < 0) {
                //System.out.printf("Output %d: value %d\n", -getNumber() - 1, value);
            }
            if (input1 == -1) {
                input1 = value;
            } else if (input2 == -1) {
                input2 = value;
            } else {
                //throw new java.lang.UnsupportedOperationException(String.format("Bot %-3d already has 2 inputs set (%-3d,%-3d).\n", getNumber(), input1, input2));
            }
            runBot();

        }

        public int getNumber() {
            return botNumber;
        }

        public int getLowOutput() throws RuntimeException {
            if (input1 == 0 || input2 == 0) {
                throw new RuntimeException();
            }
            return input1 < input2 ? input1 : input2;
        }

        public int getHighOutput() throws RuntimeException {
            if (input1 == 0 || input2 == 0) {
                throw new RuntimeException();
            }
            return input1 < input2 ? input2 : input1;
        }

        private boolean runBot() {
            if (!hasRun && input1 != -1 && input2 != -1 && highOutputBot != null && lowOutputBot != null) {
                hasRun = true;
                highOutputBot.setInput(getHighOutput());
                lowOutputBot.setInput(getLowOutput());
                if (this.hasNeededMicroChips(61, 17)) {
                    System.out.printf("Bot %d has inputs %d and %d.\n", getNumber(), getLowOutput(), getHighOutput(), lowOutputBot.botNumber, highOutputBot.botNumber);
                }

            }
            return hasRun;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.botNumber;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Bot other = (Bot) obj;
            if (this.botNumber != other.botNumber) {
                return false;
            }
            return true;
        }
    }

}
