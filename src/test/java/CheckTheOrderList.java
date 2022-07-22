import dto.*;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;


public class CheckTheOrderList {
    private final BaseApiClient apiClient = new BaseApiClient();

    @Test
    public void getOrder() {
        Response response = apiClient.get("/api/v1/orders");
        response.then().assertThat().statusCode(200)
                .and()
                .body("orders", notNullValue());
    }

    @Test
    public void getOrderSecond() {
        Response response = apiClient.get("/api/v1/orders");
        response.then().assertThat().statusCode(200)
                .and()
                .extract()
                .as(OrderList.class);
        assertTrue(OrderList.getOrders().getClass() == ArrayList.class);
        assertTrue(OrderList.getOrders().size() > 0);
    }
}

