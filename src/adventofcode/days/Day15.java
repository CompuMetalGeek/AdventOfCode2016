package adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15 extends ADay {

    class Disk {

        private final int size;
        private final int diskNumber;
        private final int startPosition;

        public Disk(int size, int diskNumber, int startPosition) {
            this.size = size;
            this.diskNumber = diskNumber;
            this.startPosition = startPosition;
        }

        public int getPosition(int time) {
            return (startPosition + time) % size;
        }

        public boolean isOpen(int time) {
            return getPosition(time) == 0;
        }

        public int getDiskNumber() {
            return diskNumber;
        }

        @Override
        public String toString() {
            return "Disk{" + "size=" + size + ", diskNumber=" + diskNumber + ", startPosition=" + startPosition + '}';
        }

    }

    List<Disk> disks;

    public Day15() {
        super(15);
    }

    private void init() {
        disks = new ArrayList<>();
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            init();
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(" ");
                int size = Integer.parseInt(line[3]);
                int diskNumber = Integer.parseInt(line[1].substring(1));
                int startPos = Integer.parseInt(line[11].substring(0, line[11].length() - 1));
                disks.add(new Disk(size, diskNumber, startPos));
            }
            int time = getPassableTime(-1);
            System.out.printf("De eerste keer dat een capsule volledig door kan vallen is bij time=%d.\n", time);

            disks.add(new Disk(11, disks.size() + 1, 0));
            time = getPassableTime(-1);
            System.out.printf("Met de extra Disk duurt het tot time=%d.\n", time);

        }

    }

    private int getPassableTime(int time) {
        boolean capsuleCanPass = false;
        while (!capsuleCanPass) {
            time++;
            capsuleCanPass = true;
            for (Disk d : disks) {
                if (!d.isOpen(time + d.getDiskNumber())) {
                    capsuleCanPass = false;
                }
            }
        }
        return time;
    }

}
