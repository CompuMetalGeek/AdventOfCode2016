package adventofcode.days;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends ADay {

    String salt;
    Queue<String> hashes;
    Queue<String> extendedHashes;
    MessageDigest md;
    List<String> goodHashes;
    List<String> goodExtendedHashes;

    public Day14() {
        super(14);
    }

    private void init() {
        hashes = new ArrayDeque<>();
        extendedHashes = new ArrayDeque<>();
        goodHashes = new ArrayList<>();
        goodExtendedHashes = new ArrayList<>();
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            salt = sc.next();
            //salt = "abc";
            init();
            boolean objective1Completed = false;
            boolean objective2Completed = false;
            int i = 0;
            while (!objective1Completed || !objective2Completed) {
                while (!objective1Completed && hashes.size() < 1001) {
                    hashes.add(generateHash(i + hashes.size(), 1));
                }
                while (!objective2Completed && extendedHashes.size() < 1001) {
                    extendedHashes.add(generateHash(i + extendedHashes.size(), 2017));
                }
                String currentHash = "";
                if (!objective1Completed) {
                    currentHash = hashes.remove();
                    if (containsTripleSequence(currentHash)) {
                        boolean anotherOneHasFive = false;
                        String tripleChar = getTripleSequenceChar(currentHash);
                        for (String hash : hashes) {
                            if (containsQuintipleSequence(hash, tripleChar)) {
                                anotherOneHasFive = true;
                                break;
                            }
                        }
                        if (anotherOneHasFive) {
                            goodHashes.add(currentHash);
                            if (goodHashes.size() <= 64) {
                                //System.out.printf("DEF: %2d %6d %s\n", goodHashes.size(), i, currentHash);
                            }
                        }
                    }
                }
                String currentExtendedHash = "";
                if (!objective2Completed) {
                    currentExtendedHash = extendedHashes.remove();
                    if (!objective2Completed && containsTripleSequence(currentExtendedHash)) {
                        boolean anotherOneHasFive = false;
                        String tripleChar = getTripleSequenceChar(currentExtendedHash);
                        for (String hash : extendedHashes) {
                            if (containsQuintipleSequence(hash, tripleChar)) {
                                anotherOneHasFive = true;
                                break;
                            }
                        }
                        if (anotherOneHasFive) {
                            goodExtendedHashes.add(currentExtendedHash);
                            if (goodExtendedHashes.size() <= 64) {
                                //System.out.printf("EXT: %2d %6d %s\n", goodExtendedHashes.size(), i, currentExtendedHash);
                            }
                        }
                    }
                }
                if (goodHashes.size() == 64 && currentHash.equals(goodHashes.get(63))) {
                    //System.out.printf("Index %d generates the 64th hash.\n", i);
                }
                if (goodExtendedHashes.size() == 64 && currentExtendedHash.equals(goodExtendedHashes.get(63))) {
                    //System.out.printf("Index %d generates the 64th extended hash.\n", i);
                }
                if (!objective1Completed && goodHashes.size() == 64) {
                    objective1Completed = true;
                    System.out.printf("Index %d generates the 64th hash.\n", i);
                }
                if (!objective2Completed && goodExtendedHashes.size() == 64) {
                    objective2Completed = true;
                    System.out.printf("Index %d generates the 64th extended hash.\n", i);
                }
                i++;
                if (i % 1000 == 0) {
                    //System.out.println(i);
                }
            }
        }
    }

    private String generateHash(int i, int numberOfTimes) {
        String toHash = salt + i;
        for (int j = 0; j < numberOfTimes; j++) {
            toHash = BytesToString(md.digest(toHash.getBytes()));
        }
        return toHash;
    }

    private boolean containsTripleSequence(String hash) {
        String regex = "(\\w)\\1\\1";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(hash);
        return m.find();
    }

    private String getTripleSequenceChar(String hash) {
        String regex = "(\\w)\\1\\1";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(hash);
        m.find();
        return m.group(1);
    }

    private boolean containsQuintipleSequence(String hash, String tripleChar) {
        String regex = "(" + tripleChar + ")\\1\\1\\1\\1";
        Pattern p = Pattern.compile(regex);
        return p.matcher(hash).find();
    }

    private String BytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
