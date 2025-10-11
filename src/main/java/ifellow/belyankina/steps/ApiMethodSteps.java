package ifellow.belyankina.steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiMethodSteps {

    public Response sendRequest(String method, String endpoint, String jsonBody, String token) {
        RequestSpecification request = prepareRequest(jsonBody, token);

        return switch (method.toUpperCase()) {
            case "POST" -> request.post(endpoint);
            case "GET" -> request.get(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }

    private RequestSpecification prepareRequest(String jsonBody, String token) {
        var request = given();
        if (jsonBody != null) {
            request.body(jsonBody);
        }
        if (token != null) {
            request.header("Authorization", token);
        }
        return request;
    }
}