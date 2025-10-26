package ifellow.belyankina.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationSteps {

    private Response response;

    @Дано("^пользователь на локалхост \"([^\"]*)\"$")
    public void userIsOnLocalhost(String url) {
        RestAssured.baseURI = url;
    }

    @И("^отправляет POST запрос$")
    public void sendsPostRequest() {
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"user\", \"password\":\"pass\"}")
                .post();
    }

    @Тогда("^пользователь получает статус (\\d+)$")
    public void userReceivesStatus(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}
