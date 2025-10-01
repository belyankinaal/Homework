package RickAndMortyTest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RickAndMortyClient {

    public Response getCharacterByName(String name) {
        return given()
                .queryParam("name", name)
                .when()
                .get("/character")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getCharacterByUrl(String url) {
        return given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getEpisodeByUrl(String url) {
        return given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract().response();
    }
}