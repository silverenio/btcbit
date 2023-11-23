package helpers;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static Random random = new Random();

    public static String getFirstName() {
        return new Faker().name().firstName();
    }

    public static String getLastName(Locale locale) {
        return new Faker(locale).name().lastName();
    }


    public static String getRandomMail() {
        String name = getFirstName();
        String lastname = getLastName(Locale.ENGLISH);
        String fdomain = getRandomText(3);
        String sdomain = getRandomText(6);

        String firstPart = String.format("test_%s%s", lastname.toLowerCase().charAt(0), name.toLowerCase());
        String lastPart = String.format("@%s.%s", sdomain, fdomain);

        return firstPart + DateHelper.getDateForPrefix(true, false, false) + lastPart;
    }

    public static String getRandomText(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = alphabet.charAt(random.nextInt(alphabet.length()));
            text.append(c);
        }
        return text.toString();
    }

    public static String getRandomPassword(int minLength, int maxLength, boolean withUppercase, boolean withSpecial,
                                           boolean withDigits) {
        return new Faker().internet().password(minLength, maxLength, withUppercase, withSpecial, withDigits);
    }

    public static String getStrongRandomPassword() {
        return getRandomPassword(8, 15, true, true, true);
    }
}
