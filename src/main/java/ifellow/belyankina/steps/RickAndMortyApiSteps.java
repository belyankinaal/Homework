package ifellow.belyankina.steps;

import ifellow.belyankina.util.ConfigReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RickAndMortyApiSteps {
    private static final String BASE_URL = ConfigReader.getProperty("web.url");
    private static final String CHARACTER_ENDPOINT = ConfigReader.getProperty("character.endpoint");

    public Response getCharacterByName(String name) {
        return executeGet(
                given().baseUri(BASE_URL).queryParam("name", name),
                CHARACTER_ENDPOINT
        );
    }

    public Response getCharacterByUrl(String url) {
        return executeGet(given(), url);
    }

    public Response getEpisodeByUrl(String url) {
        return executeGet(given(), url);
    }

    private Response executeGet(RequestSpecification requestSpec, String url) {
        return requestSpec.get(url).then().statusCode(200).extract().response();
    }
}