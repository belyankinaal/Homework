package Task2;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {

    public Response createUser(String jsonBody) {
        return given()
                .body(jsonBody)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
    }

    public Response getUser(int id) {
        return given()
                .when()
                .get("/users/" + id)
                .then()
                .extract()
                .response();
    }
}