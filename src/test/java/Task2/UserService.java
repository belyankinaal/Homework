package Task2;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {
    public Response createUser(String jsonBody) {
        return given()
                .body(jsonBody)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .post("/api/users")
                .then()
                .extract()
                .response();
    }

    public Response getUser(int id) {
        return given()
                .when()
                .get("/api/users/" + id)
                .then()
                .extract()
                .response();
    }
}