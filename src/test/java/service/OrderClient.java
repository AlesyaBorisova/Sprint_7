package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;


public class OrderClient {

    private static final String ORDER = "/api/v1/orders";

    public Response create(Order order) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(ORDER);

    }

    public Response getOrders() {
        return RestAssured.given()
                .when()
                .get(ORDER);
    }

}
