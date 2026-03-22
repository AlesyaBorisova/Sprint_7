package service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;


public class OrderClient {

    private static final String ORDER = "/api/v1/orders";

    @Step("Создать заказ: {order}")
    public Response create(Order order) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(ORDER);

    }

    @Step("Получить список всех заказов")
    public Response getOrders() {
        return RestAssured.given()
                .when()
                .get(ORDER);
    }

}
