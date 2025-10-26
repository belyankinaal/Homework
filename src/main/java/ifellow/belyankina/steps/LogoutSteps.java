package ifellow.belyankina.steps;

import ifellow.belyankina.service.LogoutService;
import ifellow.belyankina.util.TestContext;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LogoutSteps {

    private static final Logger log = Logger.getLogger(LogoutSteps.class.getName());
    private final LogoutService logout = new LogoutService();

    private String token;
    private int lastStatus;
    private String lastResponse;

    @И("токен передан для выхода")
    @Step("Передача токена для выхода")
    public void receiveTokenFromAuth() {
        token = TestContext.getToken();
        log.info("Токен получен для выхода: " + token);
    }

    @Когда("выполняется выход с корректным токеном")
    @Step("Выход с корректным токеном")
    public void logoutSuccess() {
        Response response = logout.logoutWithTokenForStep(token);
        lastStatus = response.statusCode();
        lastResponse = response.asString();
        log.info("Выход выполнен с корректным токеном: " + token);
    }

    @Когда("выполняется выход с неверным токеном")
    @Step("Выход с неверным токеном")
    public void logoutWrongToken() {
        Response response = logout.logoutWithTokenForStep("invalid-token");
        lastStatus = response.statusCode();
        lastResponse = response.asString();
        log.info("Выход с неверным токеном выполнен");
    }

    @Тогда("проверяем, что получен статус {int} и текст {string}")
    @Step("Проверка статуса и текста ответа")
    public void checkStatusAndResponse(int expectedStatus, String expectedResponse) {
        assertThat(lastStatus, equalTo(expectedStatus));
        assertThat(lastResponse, equalTo(expectedResponse));
        log.info("Проверка успешна: статус = " + lastStatus + ", ответ = " + lastResponse);
    }

    @Тогда("проверяем, что ответ содержит текст {string}")
    @Step("Проверка, что ответ содержит текст")
    public void checkResponseContainsText(String expectedText) {
        assertThat(lastResponse, containsString(expectedText));
        log.info("Проверка успешна: ответ содержит текст = " + expectedText);
    }

}
