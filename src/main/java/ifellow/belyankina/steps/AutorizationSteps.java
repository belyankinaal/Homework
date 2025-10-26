package ifellow.belyankina.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Также;
import io.cucumber.java.ru.Тогда;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutorizationSteps {

    private Response response;
    private String token;

    @Дано("^пользователь обращается к \"([^\"]*)\"$")
    public void userAccesses(String url) {
        RestAssured.baseURI = url;
    }

    @И("^вводит невалидное имя$")
    public void entersInvalidUsername() {
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"invalid_user\", \"password\":\"somepassword\"}")
                .post();
    }

    @И("^вводит валидное имя и невалидный пароль$")
    public void entersValidUsernameAndInvalidPassword() {
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"valid_user\", \"password\":\"invalid_password\"}")
                .post();
    }

    @И("^вводит валидное имя и пароль$")
    public void entersValidUsernameAndPassword() throws Exception {
        String userJson = new String(Files.readAllBytes(Paths.get("src/test/resources/user.json")));
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(userJson)
                .post();
    }

    @Также("^запоминаем токен$")
    public void saveToken() {
        String body = response.getBody().asString();
        token = body.replace("token : ", "");
        assertTrue(token != null && !token.isEmpty(), "Токен не был получен");
    }

    @Тогда("^пользователь получает ответ \"([^\"]*)\" и статус (\\d+)$")
    public void userReceivesResponseAndStatus(String expectedMessage, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(expectedMessage),
                "Ответ не содержит ожидаемого сообщения: " + expectedMessage);
    }

    @Тогда("^получает статус 401$")
    public void userReceives401ForInvalidPassword() {
        assertEquals(401, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("not found") ||
                        response.getBody().asString().contains("Unauthorized"),
                "Ответ не содержит ожидаемого сообщения об ошибке");
    }

    @Тогда("^получает статус 200$")
    public void userReceives200Status() {
        assertEquals(200, response.getStatusCode());
    }

    public String getToken() {
        return token;
    }

    @Дано("^пользователь успешно авторизован и токен сохранен$")
    public void userSuccessfullyAuthorized() throws Exception {
        userAccesses("http://localhost:8080/api/login");
        entersValidUsernameAndPassword();
        saveToken();
        userReceives200Status();
    }
}
