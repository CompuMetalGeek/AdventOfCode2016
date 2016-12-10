package adventofcode.days;

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day05 extends ADay {

    public Day05() {
        super(5);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            String input = sc.next();
            int index = 0;
            String password = "";
            char[] password2 = new char[8];
            MessageDigest md = MessageDigest.getInstance("MD5");
            int mod = 0;
            while (!passwordOk(password) || !passwordOk(password2)) {
                String nextTry = input + index;
                byte[] bytes = nextTry.getBytes();
                bytes = md.digest(bytes);
                if (bytesStartWithZero(bytes)) {
                    String hex = BytesToString(bytes);
                    if (!passwordOk(password)) {
                        password += hex.charAt(5);
                    }
                    try {
                        int location = Integer.parseInt(hex.charAt(5) + "");
                        if (0 <= location && location < 8 && password2[location] == '\0') {
                            password2[location] = hex.charAt(6);

                        }
                    } catch (NumberFormatException e) {
                    }
                }
                if (index % 10000 == 0) {
                    String dots = (mod < 40) ? "" : mod < 80 ? "." : mod < 120 ? ".." : "...";
                    System.out.printf("CRACKING%-3s [PASSWORD 1: %-8s] [PASSWORD 2: %-8s]\r", dots, formatStringPassword(password), formatCharArrayPassword(password2));
                    mod++;
                    mod %= 160;
                }
                index++;
            }
            System.out.printf("Password 1: %-50s\nPassword 2: %s\n", password, formatCharArrayPassword(password2));
        } catch (NoSuchAlgorithmException e) {

        }
    }

    private boolean passwordOk(String password) {
        return password.length() == 8;
    }

    private boolean passwordOk(char[] password) {
        if (password.length != 8) {
            return false;
        }
        for (char c : password) {
            if (c == '\0') {
                return false;
            }
        }
        return true;
    }

    private String formatCharArrayPassword(char[] array) {
        StringBuilder sb = new StringBuilder();
        for (char c : array) {
            if (c == '\0') {
                sb.append(String.format("%x", Math.round(Math.random() * 15)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String formatStringPassword(String string) {
        StringBuilder sb = new StringBuilder(string);
        while (sb.length() != 8) {
            sb.append(String.format("%x", Math.round(Math.random() * 15)));
        }
        return sb.toString();
    }

    private String BytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private boolean bytesStartWithZero(byte[] bytes) {
        if (bytes.length < 3) {
            return false;
        }
        return (bytes[0] == 0x0 && bytes[1] == 0x0 && bytes[2] >> 4 == 0x0);
    }
}
