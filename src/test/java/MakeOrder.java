import dto.ForOrder;
import io.restassured.response.Response;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(JUnitParamsRunner.class)
public class MakeOrder {
    private final BaseApiClient apiClient = new BaseApiClient();
    @Test
    @Parameters({
            "BLACK",
            "GREY",
            "BLACK, GREY",
            ""

    })

    public void makeOrderWithDifferentColor(String [] color){
        ForOrder forOrder = new ForOrder("Aloha","Alohaaloha", "Nagornaya 10", 5,"+7 800 111 11 11", 10, "2022-07-29", "Kiska", color);
        Response response = apiClient.post("/api/v1/orders", forOrder);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

    }
}

