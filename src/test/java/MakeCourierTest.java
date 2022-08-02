import dto.CorrectCourierForAuth;
import dto.CourierAuthWithoutLogin;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class MakeCourierTest {
    //исправила название.
    protected ClassLoader classLoader = getClass().getClassLoader();
    private File correctCourier = new File(classLoader.getResource("correctCourier.json").getFile());
    private File courierWithoutLogin = new File(classLoader.getResource("courierwithoutlogin.json").getFile());
    private File courierWithoutName = new File(classLoader.getResource("courierwithoutname.json").getFile());
    private File courierWithoutPassword = new File(classLoader.getResource("courierwithoutpassword.json").getFile());
    private File courierWithLogin = new File(classLoader.getResource("courierwithlogin.json").getFile());
    private File courierWithSameLogin = new File(classLoader.getResource("courierwithsamelogin.json").getFile());
    private final BaseApiClient apiClient = new BaseApiClient();
    public int getId(){
        CorrectCourierForAuth correctCourierForAuth = new CorrectCourierForAuth("Pup12345", "12345");
        Response response = apiClient.post("/api/v1/courier/login", correctCourierForAuth);
        return response.then()
                .extract()
                .jsonPath()
                .getInt("id");
    }
    public void delete() {
        Map<String, Object> map = new HashMap<>();
        int id = getId();
        map.put("id", id);
        apiClient.delete("/api/v1/courier/{id}", map);
    }



    @Test
    @DisplayName("Check that status code equals 201")
    public void getStatusCourierHasBeenCreated() {
        Response response = apiClient.post("/api/v1/courier", correctCourier);
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_CREATED);
        getId();
        delete();
    }

    @Test
    @DisplayName("Check that status code equals 409")
    public void checkStatusCanNotBeSameCourier() {
        apiClient.post("/api/v1/courier", correctCourier);
        Response response = apiClient.post("/api/v1/courier", correctCourier);
        response.then().statusCode(HttpStatus.SC_CONFLICT);
        getId();
        delete();
    }

     @Test
     @DisplayName("Check that status code equals 201")
    public void canMakeCourierWithoutName(){
         Response response = apiClient.post("/api/v1/courier", courierWithoutName);
         response.then().assertThat().body("ok",equalTo(true))
                 .and()
                 .statusCode(HttpStatus.SC_CREATED);
         getId();
         delete();
     }

     @Test
     @DisplayName("Check that status code equals 400")
    public void makeCourierWithoutRequiredFieldLogin() {
         Response response = apiClient.post("/api/v1/courier", courierWithoutLogin);
         response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                 .and()
                 .statusCode(HttpStatus.SC_BAD_REQUEST);
     }

    @Test
    @DisplayName("Check that status code equals 400")
    public void makeCourierWithoutRequiredFieldPassword(){
        Response message = apiClient.post("/api/v1/courier", courierWithoutPassword);
        message.then().assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

@Test
@DisplayName("Check that status code equals 409")
   public void makeCourierWithSameLogin(){
        apiClient.post("/api/v1/courier", courierWithLogin);
        Response message = apiClient.post("/api/v1/courier",courierWithSameLogin);
    message.then().assertThat().body("message",equalTo("Этот логин уже используется. Попробуйте другой."))
            .and()
            .statusCode(HttpStatus.SC_CONFLICT);
}
     }


