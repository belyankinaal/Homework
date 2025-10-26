package ifellow.belyankina.steps;

import ifellow.belyankina.service.AuthService;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutorizationSteps {
    private static final Logger log = Logger.getLogger(AutorizationSteps.class.getName());
    private final AuthService authorization = new AuthService();
    private String token;
    private int lastStatus;
    private String lastResponse;
    private final Properties prop = new Properties();

    public AutorizationSteps() {
        try {
            prop.load(new FileInputStream("src/test/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Когда("выполняется авторизация с корректными учетными данными")
    @Step("Успешная авторизация")
    public void authSuccess() {
        token = authorization.successCredentialsAuth();
        attachToken(token);
        assertThat(token, not(emptyString()));
        log.info("Токен: " + token);
    }

    @Когда("выполняется авторизация с неверным логином")
    @Step("Авторизация с неверным логином")
    public void authWrongLogin() {
        String invalidUsername = prop.getProperty("test.invalid.username");
        var response = authorization.wrongLoginAuth(invalidUsername); // Response
        lastResponse = response.getBody().asString();                 // String
        lastStatus = response.getStatusCode();                        // int
        log.info("Ответ при неверном логине: " + lastResponse);
    }

    @Когда("выполняется авторизация с неверным паролем")
    @Step("Авторизация с неверным паролем")
    public void authWrongPassword() {
        String invalidPassword = prop.getProperty("test.invalid.password");
        var response = authorization.wrongPasswordAuth(invalidPassword); // Response
        lastResponse = response.getBody().asString();                     // String
        lastStatus = response.getStatusCode();                             // int
        log.info("Ответ при неверном пароле: " + lastResponse);
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
        log.info("Статус: " + lastStatus + ", Ответ: " + lastResponse);
    }

    public String getToken() {
        return token;
    }

    @Step("Сохранение токена")
    private void attachToken(String token) {
        io.qameta.allure.Allure.addAttachment("Token", token);
    }
}
