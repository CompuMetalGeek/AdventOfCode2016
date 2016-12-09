package days;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day04 extends ADay {

    public Day04() {
        super(4);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            int totalIDs = 0;
            String storageName = "";
            int storageID = -1;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                int endOfName = line.lastIndexOf('-');
                int endOfID = line.indexOf('[');
                String name = line.substring(0, endOfName);
                int ID = Integer.parseInt(line.substring(endOfName + 1, endOfID));
                String checksum = line.substring(endOfID + 1, line.length() - 1);
                //System.out.printf("%-50s%-6d%-20s%-20s\n", name, ID, checksum, generateChecksum(name));
                if (isRealRoom(name, checksum)) {
                    totalIDs += ID;
                    //System.out.println(caesarDecrypt(name, ID));
                    if (caesarDecrypt(name, ID).contains("north")) {
                        storageName = caesarDecrypt(name, ID);
                        storageID = ID;
                    }
                }
            }
            System.out.println("Sum of all real room sector ID's: " + totalIDs);
            System.out.println("Location of the North Pole objects: " + storageName + " (ID = " + storageID + ")");
        }
    }

    public boolean isRealRoom(String room, String checksum) {
        String generatedChecksum = generateChecksum(room);
        if (generatedChecksum.equals(checksum)) {
            return true;
        }
        return false;
    }

    public String generateChecksum(String room) {
        char[] roomArray = room.toCharArray();
        Arrays.sort(roomArray);
        Map<Character, Integer> map = new HashMap<>();
        for (char c : roomArray) {
            if ('a' <= c && c <= 'z') {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }
        Set<Entry<Character, Integer>> checksumSet = new TreeSet<>(new Comparator<Entry<Character, Integer>>() {
                                                                        @Override
                                                                        public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
                                                                            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                                                                                return o1.getKey().compareTo(o2.getKey());
                                                                            }
                                                                            return o2.getValue().compareTo(o1.getValue());
                                                                        }
                                                                    });
        for (Entry<Character, Integer> e : map.entrySet()) {
            checksumSet.add(e);
        }
        Iterator<Entry<Character, Integer>> it = checksumSet.iterator();
        String checksum = "";
        for (int i = 0; i < 5; i++) {
            checksum += it.next().getKey();
        }
        //System.out.println(checksum);
        return checksum;
    }

    public String caesarDecrypt(String code, int shiftVal) {
        char[] codeArray = code.toCharArray();
        for (int i = 0; i < codeArray.length; i++) {
            if (codeArray[i] != '-') {
                codeArray[i] = (char) ((codeArray[i] - 'a' + shiftVal) % 26 + 'a');
            }
        }
        return String.valueOf(codeArray);
    }

}
