package adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 extends ADay {

    public Day07() {
        super(7);
    }

    @Override
    public void runDay() {
        try (Scanner sc = getInput()) {
            int countTLS = 0;
            int countSSL = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] lineparts = line.split("\\[|\\]");

                boolean TLSoutsideBracketsOK = false;
                boolean TLSinsideBracketsOK = true;
                Set<String> aba = new TreeSet<>();
                Set<String> bab = new TreeSet<>();
                for (int i = 0; i < lineparts.length; i++) {
                    if (!TLSoutsideBracketsOK && i % 2 == 0) {
                        TLSoutsideBracketsOK = containsABBA(lineparts[i]);
                    }
                    if (TLSinsideBracketsOK && i % 2 == 1) {
                        TLSinsideBracketsOK = !containsABBA(lineparts[i]);
                    }
                    try {
                        if (i % 2 == 0) {
                            aba.addAll(containsABA(lineparts[i]));
                        }
                        if (i % 2 == 1) {
                            bab.addAll(containsABA(lineparts[i]));
                        }
                    } catch (Exception e) {
                    }
                }
                if (TLSoutsideBracketsOK && TLSinsideBracketsOK) {
                    countTLS++;
                }
                for (String s : aba) {
                    String babVersion = "" + s.charAt(1) + s.charAt(0) + s.charAt(1);
                    if (bab.contains(babVersion)) {
                        countSSL++;
                        break;
                    }
                }

            }
            System.out.printf("Number of addresses that supports TLS is %d.\n", countTLS);
            System.out.printf("Number of addresses that supports SSL is %d.\n", countSSL);
        }
    }

    private boolean containsABBA(String s) {
        Pattern p = Pattern.compile("([a-z])([a-z])\\2\\1");
        Matcher m = p.matcher(s);
        return m.find() && !m.group(1).equalsIgnoreCase(m.group(2));
    }

    private List<String> containsABA(String s) throws Exception {
        List<String> matches = new ArrayList<>();
        Pattern p = Pattern.compile("([a-z])([a-z])\\1");
        Matcher m = p.matcher(s);
        for (int i = 0; i < s.length(); i++) {
            if (m.find(i) && !m.group(1).equalsIgnoreCase(m.group(2))) {
                matches.add(m.group(1) + m.group(2) + m.group(1));
            }
        }
        return matches;
    }

}
