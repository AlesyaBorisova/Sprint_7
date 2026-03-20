package utils;

import model.Courier;

import static utils.RandomGenerator.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier().withLogin(randomString(10))
                .withPassword(randomString(12))
                .withFirstName(randomString(20));
    }

    public static String randomLogin() {
        return randomString(10);
    }

    public static String randomPassword() {
        return randomString(12);
    }
}
