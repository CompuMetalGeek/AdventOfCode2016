package adventofcode.days;

import java.util.Scanner;

public class Day02 extends ADay {

    public final Character[][] numpad = {{null, null, null, null, null},
    {null, '1', '2', '3', null},
    {null, '4', '5', '6', null},
    {null, '7', '8', '9', null},
    {null, null, null, null, null}}; // numpad[vertical][horizontal]

    public final Character[][] numpad2 = {{null, null, null, null, null, null, null},
    {null, null, null, '1', null, null, null},
    {null, null, '2', '3', '4', null, null},
    {null, '5', '6', '7', '8', '9', null},
    {null, null, 'A', 'B', 'C', null, null},
    {null, null, null, 'D', null, null, null},
    {null, null, null, null, null, null, null}}; // numpad[vertical][horizontal]

    int horizontal, vertical;
    int horizontal2, vertical2;

    String code;
    String code2;

    public Day02() {
        super(2);
        init();
    }

    @Override
    public void runDay() {
        init();
        try (Scanner sc = getInput()) {
            while (sc.hasNext()) {
                char[] line = sc.next().toCharArray();
                for (int i = 0; i < line.length; i++) {
                    int verticalold = vertical;
                    int horizontalold = horizontal;
                    int verticalold2 = vertical2;
                    int horizontalold2 = horizontal2;
                    switch (line[i]) {
                        case 'U':
                            vertical--;
                            vertical2--;
                            break;
                        case 'R':
                            horizontal++;
                            horizontal2++;
                            break;
                        case 'D':
                            vertical++;
                            vertical2++;
                            break;
                        case 'L':
                            horizontal--;
                            horizontal2--;
                            break;
                    }
                    if (numpad[vertical][horizontal] == null) {
                        vertical = verticalold;
                        horizontal = horizontalold;
                    }
                    if (numpad2[vertical2][horizontal2] == null) {
                        vertical2 = verticalold2;
                        horizontal2 = horizontalold2;
                    }
                }
                code += numpad[vertical][horizontal];
                code2 += numpad2[vertical2][horizontal2];
            }
            System.out.println("First code is: " + code);
            System.out.println("second code is: " + code2);
        }
    }

    private void init() {
        horizontal = 2;
        vertical = 2;
        horizontal2 = 1;
        vertical2 = 3;
        code = "";
        code2 = "";
    }

}
