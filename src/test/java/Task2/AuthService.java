package Task2;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthService {

    public Response register(String jsonBody) {
        return given()
                .body(jsonBody)
                .when()
                .post("/api/register")
                .then()
                .extract()
                .response();
    }

    public Response login(String jsonBody) {
        return given()
                .body(jsonBody)
                .when()
                .post("/api/login")
                .then()
                .extract()
                .response();
    }

    public Response logout(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .get("/api/logout")
                .then()
                .extract()
                .response();
    }
}

