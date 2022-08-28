package ru.netology.util;

import com.github.javafaker.Faker;
import lombok.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker((new Locale(locale)));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }


}