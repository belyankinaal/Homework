package localhost;

import ifellow.belyankina.service.AuthService;
import ifellow.belyankina.service.LogoutService;
import ifellow.belyankina.service.RegistrationService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

@Epic("Localhost АПИ Тест")
@Feature("Аутентификация localhost")
public class LocalHostTest extends BaseTest {

    private static final Logger log = Logger.getLogger(LocalHostTest.class.getName());

    private final RegistrationService registration = new RegistrationService();
    private final AuthService authorization = new AuthService();
    private final LogoutService userLogout = new LogoutService();

    @Test
    @DisplayName("Тест localhost")
    @Tag("Test_2")
    @Story("Полный сценарий аутентификации и выхода")
    @Description("Регистрация, неуспешная и успешная авторизация, запоминание токена и выход")
    public void testAuthFlow() {
        log.info("Начало теста localHostTest");

        step("Регистрация пользователя", registration::successRegistration);

        step("Неуспешная авторизация", () -> {
            authorization.unsuccessLoginAuth();
            authorization.unsuccessPassAuth();
        });

        step("Успешная авторизация", () -> {
            String token = authorization.successCredentialsAuth();
            attachToken(token);
            assertThat(token, not(emptyString()));
            log.info("Токен: " + token);

            userLogout.logoutUnsuccessTest();
            userLogout.logoutSuccessTest(token);
        });

        log.info("Тест завершен");
    }
}
