package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import service.OrderClient;

public class OrderListTest extends BaseTest {

    private OrderClient orderClient = new OrderClient();

    @Test
    @Step("Проверка получения списка заказов")
    public void getOrderListTest() {
        Response response = orderClient.getOrders();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue("Список заказов не может быть пустым", response.jsonPath().getList("orders").size() > 0);

    }

}
