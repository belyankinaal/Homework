package ifellow.belyankina.steps;

import ifellow.belyankina.service.AuthService;
import ifellow.belyankina.util.ConfigReader;
import ifellow.belyankina.util.TestContext;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutorizationSteps {

    private static final Logger log = Logger.getLogger(AutorizationSteps.class.getName());
    private final AuthService authorization = new AuthService();
    private String token;
    private int lastStatus;
    private String lastResponse;

    @Когда("выполняется авторизация с валидными учетными данными")
    @Step("Успешная авторизация")
    public void authSuccess() {
        token = authorization.successCredentialsAuth();
        TestContext.setToken(token);
        attachToken(token);
        assertThat("Токен не должен быть пустым", token, not(emptyString()));
        log.info("Токен успешно получен: " + token);
    }

    @Когда("вводится невалидный логин при авторизации")
    @Step("Авторизация с невалидным логином")
    public void authWrongLogin() {
        String invalidUsername = ConfigReader.getProperty("test.invalid.username");
        var response = authorization.wrongLoginAuth(invalidUsername);
        lastResponse = response.getBody().asString();
        lastStatus = response.getStatusCode();
        log.info("Ответ при невалидном логине: " + lastResponse);
    }

    @Когда("вводится невалидный пароль при авторизации")
    @Step("Авторизация с невалидным паролем")
    public void authWrongPassword() {
        String invalidPassword = ConfigReader.getProperty("test.invalid.password");
        var response = authorization.wrongPasswordAuth(invalidPassword);
        lastResponse = response.getBody().asString();
        lastStatus = response.getStatusCode();
        log.info("Ответ при невалидном пароле: " + lastResponse);
    }

    @Тогда("токен получен и сохранен")
    @Step("Проверка получения токена")
    public void checkTokenSaved() {
        assertThat("Токен не должен быть пустым", token, not(emptyString()));
        log.info("Токен сохранен: " + token);
    }

    @Тогда("проверяем, что статус {int} и ответ {string}")
    @Step("Проверка статуса и ответа")
    public void checkStatusAndResponse(int expectedStatus, String expectedResponse) {
        assertThat(lastStatus, equalTo(expectedStatus));
        assertThat(lastResponse, equalTo(expectedResponse));
        log.info("Проверка успешна: статус = " + lastStatus + ", ответ = " + lastResponse);
    }

    @Step("Сохранение токена")
    private void attachToken(String token) {
        io.qameta.allure.Allure.addAttachment("Token", token);
    }
}
