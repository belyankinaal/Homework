package ifellow.belyankina.steps;

import ifellow.belyankina.service.LogoutService;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LogoutSteps {

    private static final Logger log = Logger.getLogger(LogoutSteps.class.getName());
    private final LogoutService logout = new LogoutService();

    private String token;
    private int lastStatus;
    private String lastResponse;

    // Установка токена из авторизации
    public void setToken(String token) {
        this.token = token;
    }

    @Когда("^выполняется выход с корректным токеном$")
    @Step("Выход с корректным токеном")
    public void logoutSuccess() {
        Response response = logout.logoutWithTokenForStep(token, true);
        lastStatus = response.statusCode();
        lastResponse = response.asString();
        log.info("Выход выполнен с токеном: " + token);
    }

    @Когда("^выполняется выход с неверным токеном$")
    @Step("Выход с неверным токеном")
    public void logoutWrongToken() {
        Response response = logout.logoutWithTokenForStep("invalid-token", false);
        lastStatus = response.statusCode();
        lastResponse = response.asString();
        log.info("Выход с неверным токеном выполнен");
    }

    @Тогда("^проверяем, что получен статус (\\d+) и текст \"([^\"]*)\"$")
    @Step("Проверка кода ответа и сообщения")
    public void checkStatusAndResponse(int expectedStatus, String expectedResponse) {
        assertThat(lastStatus, equalTo(expectedStatus));
        assertThat(lastResponse, equalTo(expectedResponse));
        log.info("Проверка успешна: статус = " + lastStatus + ", ответ = " + lastResponse);
    }
}
