package service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;

public class CourierClient {

    private static final String CREATE_ENDPOINT = "/api/v1/courier";
    private static final String LOGIN_ENDPOINT = "/api/v1/courier/login";
    private static final String DELETE_ENDPOINT = "/api/v1/courier/:id";

    @Step("Создать курьера с логином: {courier.login}")
    public Response create(Courier courier) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_ENDPOINT);
    }

    @Step("Войти под курьером с логином: {courierCreds.login}")
    public Response login(CourierCreds courierCreds) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(courierCreds)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("Удалить курьера с ID: {courierId}")
    public Response delete(int courierId) {
        return RestAssured.given()
                .when()
                .delete(DELETE_ENDPOINT + courierId);
    }
}

