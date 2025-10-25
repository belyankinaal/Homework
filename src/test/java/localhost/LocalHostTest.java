package localhost;

import ifellow.belyankina.service.AuthService;
import ifellow.belyankina.service.LogoutService;
import ifellow.belyankina.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

public class LocalHostTest extends BaseTest {

    private static final Logger log = Logger.getLogger(LocalHostTest.class.getName());

    private final RegistrationService registration = new RegistrationService();
    private final AuthService authorization = new AuthService();
    private final LogoutService userLogout = new LogoutService();

    @Test
    @DisplayName("Тест localhost")
    @Tag("Test_2")
    public void testAuthFlow() {
        log.info("Начало теста");

        registration.successRegistration();
        authorization.unsuccessLoginAuth();
        authorization.unsuccessPassAuth();

        String token = authorization.successCredentialsAuth();
        assertThat(token, not(emptyString()));
        log.info("Токен: " + token);

        userLogout.logoutUnsuccessTest();
        userLogout.logoutSuccessTest(token);

        log.info("Тест завершен");
    }
}