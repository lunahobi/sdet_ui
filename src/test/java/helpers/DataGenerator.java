package helpers;

import java.util.Random;

public class DataGenerator {
    public static String generatePostCode() {
        Random random = new Random();
        StringBuilder postCode = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            postCode.append(random.nextInt(10));
        }
        return postCode.toString();
    }

    public static String generateFirstName(String postCode) {
        StringBuilder firstName = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            int num = Integer.parseInt(postCode.substring(i, Math.min(i + 2, postCode.length())));
            char letter = (char) ('a' + (num % 26));
            firstName.append(letter);
        }
        return firstName.toString();
    }
}