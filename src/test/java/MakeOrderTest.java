import dto.ForOrder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.internal.support.CloseHTTPClientConnectionInputStreamWrapper;
import io.restassured.response.Response;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(JUnitParamsRunner.class)
public class MakeOrderTest {
    //исправила назвавние.
    private final BaseApiClient apiClient = new BaseApiClient();
    @Test
    @DisplayName("Check that status code equals 200")
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
                .statusCode(HttpStatus.SC_OK);

    }
}

