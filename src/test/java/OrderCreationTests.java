import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.OrderClient;
import utils.OrderGenerator;
import base.BaseTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreationTests extends BaseTest {

    private OrderClient orderClient = new OrderClient();
    private final Order order;
    private Integer track;

    public OrderCreationTests(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getOrderData() {
        return Arrays.asList(new Object[][]{
                {OrderGenerator.randomOrderWithColor(List.of("BLACK"))},
                {OrderGenerator.randomOrderWithColor(List.of("GREY"))},
                {OrderGenerator.randomOrderWithColor(List.of("BLACK", "GREY"))},
                {OrderGenerator.randomOrderWithColor(null)},
        });
    }

    @Test
    public void testCreateOrderWithColorOptions() {
        track = createOrder(order);
        checkTrackIsNotNull(track);
    }

    @Step("Создать заказ с цветами {order.color}")
    private Integer createOrder(Order order) {
        Response response = orderClient.create(order);
        Assert.assertEquals(201, response.getStatusCode());
        return response.jsonPath().getInt("track");
    }

    @Step("Проверить, что track заказа не пустой")
    private void checkTrackIsNotNull(Integer track) {
        Assert.assertNotNull("Ответ должен содержать track", track);
    }
}
