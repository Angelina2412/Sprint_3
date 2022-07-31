import dto.CorrectCourier;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

public class BaseApiClient {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public Response get(String endpoint){
        return request().get(endpoint);
    }
    public Response get(String endpoint, Map<String, Object> pathParams){
        return request(pathParams).get(endpoint);
    }
    public Response post(String endpoint, File body){
        return request(body).post(endpoint);
    }
    public Response post(String endpoint, Object body){
        return request(body).post(endpoint);
    }
    public Response post(String endpoint, Map<String, Object> pathParams, File body){
        return request(pathParams, body).post(endpoint);
    }
    public Response delete(String endpoint){
        return request().get(endpoint);
    }
    public Response delete(String endpoint, Map<String, Object> pathParams){
        return request().delete(endpoint, pathParams);
    }
    private RequestSpecification request(){return baseRequest();
    }
   private RequestSpecification request(File body){
        return baseRequest().body(body);
    }

    private RequestSpecification request(Object body){
        return baseRequest().body(body);
    }
    private RequestSpecification request(Map<String, Object> pathParams){
        return baseRequest().queryParams(pathParams);
    }
    private RequestSpecification request(Map<String, Object> pathParams, File body){
        return baseRequest().queryParams(pathParams).body(body);
    }

    private RequestSpecification baseRequest(){
        return RestAssured.given(config());
    }
    private RequestSpecification config(){
        RestAssured.useRelaxedHTTPSValidation();
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

}
