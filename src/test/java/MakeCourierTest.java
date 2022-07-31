import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import org.junit.Test;
import java.io.File;
import static org.hamcrest.Matchers.equalTo;

public class MakeCourierTest {
    protected ClassLoader classLoader = getClass().getClassLoader();
    private File correctCourier = new File(classLoader.getResource("correctCourier.json").getFile());
    private File courierWithoutLogin = new File(classLoader.getResource("courierwithoutlogin.json").getFile());
    private File courierWithoutName = new File(classLoader.getResource("courierwithoutname.json").getFile());
    private File courierWithoutPassword = new File(classLoader.getResource("courierwithoutpassword.json").getFile());
    private File courierWithLogin = new File(classLoader.getResource("courierwithlogin.json").getFile());
    private File courierWithSameLogin = new File(classLoader.getResource("courierwithsamelogin.json").getFile());
    private final BaseApiClient apiClient = new BaseApiClient();

    @Test
    @DisplayName("Check status code")
    public void getStatusCourierHasBeenCreated() {
        Response response = apiClient.post("/api/v1/courier", correctCourier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
    @Test
    @DisplayName("Check status code")
    public void checkStatusCanNotBeSameCourier() {
        Response response = apiClient.post("/api/v1/courier", correctCourier);
        response.then().statusCode(409);
    }

     @Test
     @DisplayName("Check status code")
    public void canMakeCourierWithoutName(){
         Response response = apiClient.post("/api/v1/courier", courierWithoutName);
         response.then().assertThat().body("ok",equalTo(true))
                 .and()
                 .statusCode(201);
     }

     @Test
     @DisplayName("Check status code")
    public void makeCourierWithoutRequiredFieldLogin() {
         Response response = apiClient.post("/api/v1/courier", courierWithoutLogin);
         response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                 .and()
                 .statusCode(400);
     }

    @Test
    @DisplayName("Check status code")
    public void makeCourierWithoutRequiredFieldPassword(){
        Response message = apiClient.post("/api/v1/courier", courierWithoutPassword);
        message.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

@Test
@DisplayName("Check status code")
   public void makeCourierWithSameLogin(){
        apiClient.post("/api/v1/courier", courierWithLogin);
        Response message = apiClient.post("/api/v1/courier",courierWithSameLogin);
    message.then().assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."))
            .and()
            .statusCode(409);
}
     }


