import dto.*;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class LoginTheCourier {
    private final BaseApiClient apiClient = new BaseApiClient();
    public void makeCourier() {
        CorrectCourier correctCourier = new CorrectCourier("Pupsik2", "12345", "Alolo");
                apiClient.post("/api/v1/courier", correctCourier);
    }
    public int getId() {
        CorrectCourierForAuth correctCourierForAuth = new CorrectCourierForAuth("Pupsik2", "12345");
        Response response = apiClient.post("/api/v1/courier/login", correctCourierForAuth);
        return response.then()
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Before
    public void setUp() {
        makeCourier();
    }
     @After
     public void delete() {
        Map<String, Object> map = new HashMap<>();
        int id = getId();
        map.put("id", id);
        apiClient.delete("/api/v1/courier/{id}", map);
     }

    @Test
    public void courierCanAuthAndIdIsCorrect(){
        CorrectCourierForAuth correctCourierForAuth = new CorrectCourierForAuth("Pupsik2", "12345");
        Response response = apiClient.post("/api/v1/courier/login", correctCourierForAuth);
        int id = response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Test
    public void courierAuthWithoutLogin(){
        CourierAuthWithoutLogin courierAuthWithoutLogin = new CourierAuthWithoutLogin("12345");
        Response response = apiClient.post("/api/v1/courier/login", courierAuthWithoutLogin);
        response.then().assertThat().body("message",equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
//    @Test
//    public void courierAuthWithoutPassword() {
//        CourierAuthWithoutPassword courierAuthWithoutPassword = new CourierAuthWithoutPassword("Pupsik1");
//       Response response = apiClient.post("/api/v1/courier/login", courierAuthWithoutPassword)
//      response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
//                .and()
//                .statusCode(400);
//    }
    @Test
    public void courierAuthWithWrongLogin(){
        CourierAuthWithWrongLogin courierAuthWithWrongLogin = new CourierAuthWithWrongLogin ("Pupsiiik", "12345");
        Response response = apiClient.post("/api/v1/courier/login", courierAuthWithWrongLogin);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    public void courierAuthWithWrongPassword(){
        CourierAuthWithWrongPassword courierAuthWithWrongPassword = new CourierAuthWithWrongPassword ("Pupsik2", "11234");
        Response response = apiClient.post("/api/v1/courier/login", courierAuthWithWrongPassword);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    public void authNonExistentCourier(){
        NonExistentCourier nonExistentCourier = new NonExistentCourier ("Kispup2", "54321");
        Response response = apiClient.post("/api/v1/courier/login", nonExistentCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

}
