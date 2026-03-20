
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;
import org.junit.Assert;
import org.junit.Test;
import utils.CourierGenerator;
import base.BaseTest;

public class CourierAuthorizationTests extends BaseTest {

    @Test
    public void loginCourierTest() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        courierId = loginCourier(CourierCreds.getCredsFromCourier(courier));
        Assert.assertNotNull("Должен возвращаться id курьера", courierId);

    }

    @Test
    public void cannotLoginWithoutLoginField() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        Response response = loginCourierExpectingFailure(new CourierCreds("", courier.getPassword()));
        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("Недостаточно данных для входа", response.jsonPath().getString("message"));

    }

    @Test
    public void cannotLoginWithoutPasswordField() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        Response response = loginCourierExpectingFailure(new CourierCreds(courier.getLogin(), ""));
        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("Недостаточно данных для входа", response.jsonPath().getString("message"));

    }

    @Test
    public void cannotLoginWithWrongPassword() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        Response response = loginCourierExpectingFailure(new CourierCreds(courier.getLogin(), "wrong password"));
        Assert.assertEquals(404, response.getStatusCode());
        Assert.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }

    @Test

    public void cannotLoginWithWrongLogin() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        Response response = loginCourierExpectingFailure(new CourierCreds("wrong login", courier.getPassword()));
        Assert.assertEquals(404, response.getStatusCode());
        Assert.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }

    @Test

    public void cannotLoginWithWrongLoginAndPassword() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);

        Response response = loginCourierExpectingFailure(new CourierCreds("wrong login", "wrong password"));
        Assert.assertEquals(404, response.getStatusCode());
        Assert.assertEquals("Учетная запись не найдена", response.jsonPath().getString("message"));
    }

    @Step("Создать курьера")
    private void createCourier(Courier courier) {
        courierClient.create(courier);
    }

    @Step("Войти под курьером")
    private Integer loginCourier(CourierCreds creds) {
        Response response = courierClient.login(creds);
        Assert.assertEquals(200, response.getStatusCode());
        return response.jsonPath().getInt("id");
    }

    @Step("Войти под курьером, ожидая ошибку")
    private Response loginCourierExpectingFailure(CourierCreds creds) {
        return courierClient.login(creds);
    }
}
