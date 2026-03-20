package utils;

import model.Order;

import java.util.List;
import java.util.Random;
import static utils.RandomGenerator.randomString;

public class OrderGenerator {

    private static final Random RANDOM = new Random();

    public static Order randomOrder() {
        return new Order()
                .withFirstName(randomString(20))
                .withLastName(randomString(20))
                .withAddress(randomString(40))
                .withMetroStation(String.valueOf(RANDOM.nextInt(10)))
                .withPhone(randomString(15))
                .withRentTime(RANDOM.nextInt(30))
                        .withDeliveryDate("2026-03-20")
                        .withComment(randomString(70));
    }

    public static Order randomOrderWithColor(List<String> color) {
        Order order = randomOrder();
        order.withColor(color);
        return order;
            }
}
