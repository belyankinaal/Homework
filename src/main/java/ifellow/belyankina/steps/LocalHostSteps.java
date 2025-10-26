package ifellow.belyankina.steps;

import ifellow.belyankina.service.AuthService;
import ifellow.belyankina.service.LogoutService;
import ifellow.belyankina.service.RegistrationService;
import ifellow.belyankina.util.Specification;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

public class LocalHostSteps {

    private static final Logger log = Logger.getLogger(LocalHostSteps.class.getName());

    private final RegistrationService registration = new RegistrationService();
    private final AuthService authorization = new AuthService();
    private final LogoutService logout = new LogoutService();

    private String token;

    @Дано("^установлена спецификация localhost API$")
    public void setupSpecification() {
        RestAssured.requestSpecification = Specification.forLocalApi();
        log.info("Спецификация localhost установлена");
    }

    @Когда("^выполняется регистрация пользователя$")
    public void registerUser() {
        performRegistration();
    }

    @И("^выполняется неуспешная авторизация с неверным логином и паролем$")
    public void unsuccessfulAuth() {
        performUnsuccessfulAuth();
    }

    @И("^выполняется успешная авторизация и получение токена$")
    public void successfulAuth() {
        performSuccessfulAuth();
    }

    @Тогда("^выполняется проверка выхода пользователя$")
    public void logoutUser() {
        performLogout();
    }

    @Step("Регистрация пользователя")
    private void performRegistration() {
        registration.successRegistration();
    }

    @Step("Неуспешная авторизация с неверным логином и паролем")
    private void performUnsuccessfulAuth() {
        authorization.unsuccessLoginAuth();
        authorization.unsuccessPassAuth();
    }

    @Step("Успешная авторизация и получение токена")
    private void performSuccessfulAuth() {
        token = authorization.successCredentialsAuth();
        attachToken(token);
        assertThat(token, not(emptyString()));
        log.info("Токен: " + token);
    }

    @Step("Выход пользователя")
    private void performLogout() {
        logout.logoutUnsuccessTest();
        logout.logoutSuccessTest(token);
        log.info("Тест сценария аутентификации завершен");
    }

    @Step("Сохранение токена")
    private void attachToken(String token) {
        io.qameta.allure.Allure.addAttachment("Token", token);
    }
}
