package base;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import service.CourierClient;

public class BaseTest {

    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    protected CourierClient courierClient = new CourierClient();
    protected Integer courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @After
    public void tearDown() {
      if (courierId != null) {
          courierClient.delete(courierId);
      }
    }

}
