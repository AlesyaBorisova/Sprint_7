import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCreds;
import org.junit.Assert;
import org.junit.Test;
import utils.CourierGenerator;
import base.BaseTest;


public class CourierCreationTests extends BaseTest {


    @Test
    public void createCourierTest() {
        Courier courier = CourierGenerator.randomCourier();
        Response response = createCourier(courier);
        Assert.assertTrue("Поле 'ok' должно быть true", response.jsonPath().getBoolean("ok"));

        courierId = loginCourier(CourierCreds.getCredsFromCourier(courier)).jsonPath().getInt("id");
        Assert.assertNotNull("Должен возвращаться id курьера", courierId);
    }

    @Test
    public void createDuplicateCourierWithSameLoginTest() {
        Courier courier = CourierGenerator.randomCourier();
        createCourier(courier);
        courierId = loginCourier(CourierCreds.getCredsFromCourier(courier)).jsonPath().getInt("id");

        Courier duplicate = new Courier()
                .withLogin(courier.getLogin())
                .withPassword(CourierGenerator.randomPassword())
                .withFirstName("Another Name");

        Response secondResponse = createCourierWithDuplicateLogin(duplicate);
        Assert.assertEquals(409, secondResponse.getStatusCode());
        Assert.assertEquals("Этот логин уже используется. Попробуйте другой.", secondResponse.jsonPath().getString("message"));
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier noLoginCourier = new Courier()
                .withPassword(CourierGenerator.randomPassword())
                .withFirstName("Alex");

        Response response = createCourierWithoutLoginOrPassword(noLoginCourier);
        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        Courier noPasswordCourier = new Courier()
                .withLogin(CourierGenerator.randomLogin())
                .withFirstName("Alex");
        Response response = createCourierWithoutLoginOrPassword(noPasswordCourier);
        Assert.assertEquals(400, response.getStatusCode());
        Assert.assertEquals("Недостаточно данных для создания учетной записи", response.jsonPath().getString("message"));
    }

    @Step("Создать курьера")
    private Response createCourier(Courier courier) {
        Response response = courierClient.create(courier);
        Assert.assertEquals(201, response.getStatusCode());
        return response;
    }

    @Step("Создать курьера, ожидая ошибку 400 (отсутствие обязательных данных)")
    private  Response createCourierWithoutLoginOrPassword(Courier courier) {
        Response response = courierClient.create(courier);
        Assert.assertEquals(400, response.getStatusCode());
        return response;
    }

    @Step("Создать курьера с повторяющимся логином, ожидая ошибку 409")
    private Response createCourierWithDuplicateLogin(Courier courier) {
        Response response = courierClient.create(courier);
        Assert.assertEquals(409, response.getStatusCode());
        return response;
    }

    @Step("Войти в систему под курьером")
    private Response loginCourier(CourierCreds creds) {
        Response response = courierClient.login(creds);
        Assert.assertEquals(200, response.getStatusCode());
        return response;
    }
}

