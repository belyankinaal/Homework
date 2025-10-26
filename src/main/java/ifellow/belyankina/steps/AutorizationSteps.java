package ifellow.belyankina.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Также;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class AutorizationSteps {

    private Response response;
    private String token;

    @Дано("^пользователь обращается к \"([^\"]*)\"$")
    public void userAccesses(String url) {
        RestAssured.baseURI = url;
    }

    @Step("Ввод невалидного имени")
    @И("^вводит невалидное имя$")
    public void entersInvalidUsername() {
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"invalid_user\", \"password\":\"somepassword\"}")
                .post();
    }

    @Step("Ввод валидного имени и невалидного пароля")
    @И("^вводит валидное имя и невалидный пароль$")
    public void entersValidUsernameAndInvalidPassword() {
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"valid_user\", \"password\":\"invalid_password\"}")
                .post();
    }

    @Step("Ввод валидных учетных данных")
    @И("^вводит валидное имя и пароль$")
    public void entersValidUsernameAndPassword() throws Exception {
        String userJson = new String(Files.readAllBytes(Paths.get("src/test/resources/user.json")));
        response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(userJson)
                .post();
    }

    @Step("Сохраняем токен из успешного ответа")
    @Также("^запоминаем токен$")
    public void saveToken() {
        token = response.jsonPath().getString("token");
        assertNotNull(token, "Токен не был получен");
        assertFalse(token.isEmpty(), "Токен пустой");
        Allure.addAttachment("Token", token);
    }

    @Step("Проверка ответа и статуса {expectedStatusCode}")
    @Тогда("^пользователь получает ответ \"([^\"]*)\" и статус (\\d+)$")
    public void userReceivesResponseAndStatus(String expectedMessage, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode(), "Неверный статус-код");
        assertTrue(response.getBody().asString().contains(expectedMessage),
                "Ответ не содержит ожидаемого сообщения: " + expectedMessage);
    }

    @Step("Проверка статуса 401 (невалидные данные)")
    @Тогда("^получает статус 401$")
    public void userReceives401ForInvalidPassword() {
        assertEquals(401, response.getStatusCode());
        String body = response.getBody().asString();
        assertTrue(body.contains("not found") || body.contains("Unauthorized"),
                "Ответ не содержит ожидаемого текста ошибки");
    }

    @Step("Проверка статуса 200 (успешная авторизация)")
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


