package ru.stellarburgers.utils;

import com.github.javafaker.Faker;

import java.util.Locale;

public class TestDataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String generateName() {
        return faker.name().firstName();
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateValidPassword() {
        return faker.internet().password(6, 12, true, true);
    }

    public static String generateShortPassword() {
        // Пароль меньше 6 символов — некорректный
        return faker.internet().password(1, 5, false, false);
    }
}
