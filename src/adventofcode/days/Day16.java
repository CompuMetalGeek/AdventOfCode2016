package adventofcode.days;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Day16 extends ADay {

    String originalData, data, checksum;
    int fileLength;

    public Day16() {
        super(16);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            originalData = sc.nextLine();
            while (sc.hasNext()) {
                fileLength = sc.nextInt();
                generateChecksum();
            }

        }
    }

    private void init() {
        data = originalData;
        checksum = null;
    }

    private String invertBits(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                sb.append('0');
            } else {
                sb.append('1');
            }
        }
        return sb.toString();
    }

    private void allongate() {
        StringBuilder sb = new StringBuilder(invertBits(data));
        sb.reverse();
        sb.insert(0, '0');
        sb.insert(0, data);

        data = sb.toString();
    }

    private void checksum(String toChecksum, int n) {
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("[^1]");
        for (int i = 0; i < toChecksum.length(); i += n) {
            if (p.matcher(toChecksum.substring(i, i + n)).replaceAll("").length() % 2 == 0) {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }

        checksum = sb.toString();

    }

    private void checksum() {
        String toChecksum = checksum == null ? data.substring(0, fileLength) : checksum;
        int length = toChecksum.length();
        int i = 2;
        while (length % i == 0) {
            i *= 2;
        }
        i /= 2;
        if (i < 2) {
            throw new RuntimeException("Invalid length of a file");
        }
        checksum(toChecksum, i);
    }

    private void generateChecksum() {
        init();
        while (data.length() < fileLength) {
            allongate();
        }
        checksum();
        System.out.printf("The checksum of the generated data (length=%9d) is '%s'.\n", fileLength, checksum);
    }

}
