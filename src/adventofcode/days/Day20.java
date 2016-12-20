package adventofcode.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day20 extends ADay {

    final long MAX_VAL = 4294967295L;

    class Pair {

        long x, y;

        public Pair(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("{%10d,%10d}", x, y);
        }

    }

    class PairComparator implements Comparator<Pair> {

        @Override
        public int compare(Pair o1, Pair o2) {
            return Long.compare(o1.x, o2.x);
        }

    }

    public Day20() {
        super(20);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput("-|\n")) {
            System.out.println(MAX_VAL);
            List<Pair> ranges = new ArrayList<>();
            while (sc.hasNext()) {
                long start = sc.nextLong();
                long end = sc.nextLong();
                ranges.add(new Pair(start, end));
            }
            ranges.sort(new PairComparator());
            int pairIndex = 0;
            long index = 0;
            boolean foundLowestNumber = false;
            while (!foundLowestNumber && index <= MAX_VAL) {
                Pair p = ranges.get(pairIndex);
                if (p.x <= index && index <= p.y) {
                    index = p.y + 1;
                } else if (p.y < index) {
                    pairIndex++;
                } else {
                    foundLowestNumber = true;
                }
            }
            System.out.printf("The lowest available number is %d.\n", index);

            long count = 0;
            if(ranges.get(0).x>0){
                count+=ranges.get(0).x;
            }
            long current_max = ranges.get(0).y;
            for(Pair p:ranges){
                if(p.x+1>current_max){
                    count+=p.x-current_max-1;
                }
                if(p.y>current_max){
                    current_max=p.y;
                }
            }
            if(current_max<MAX_VAL){
                count+=MAX_VAL-current_max;
            }
            System.out.printf("There are %d available numbers.\n", count);
        }
    }

}
