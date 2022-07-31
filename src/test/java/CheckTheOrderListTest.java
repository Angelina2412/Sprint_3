import dto.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;


public class CheckTheOrderListTest  {
    //исправила название.
    private final BaseApiClient apiClient = new BaseApiClient();

    @Test
    @DisplayName("Check status code")
    public void checkThatBodyHaveTheOrderList() { //поменять название
        Response response = apiClient.get("/api/v1/orders");
        OrderResponse r = response.then().assertThat().statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(OrderResponse.class);
        assertTrue(r.getOrders().size() > 0);
        assertTrue(r.getOrders().get(0).getId() != 0);
    }
}

