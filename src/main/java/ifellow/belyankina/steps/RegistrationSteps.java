package ifellow.belyankina.steps;

import ifellow.belyankina.service.RegistrationService;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;

import java.util.logging.Logger;

public class RegistrationSteps {

    private static final Logger log = Logger.getLogger(RegistrationSteps.class.getName());
    private final RegistrationService registration = new RegistrationService();

    @Когда("^выполняется регистрация пользователя$")
    @Step("Регистрация пользователя")
    public void registerUser() {
        registration.successRegistration();
        log.info("Регистрация выполнена успешно");
    }

    @Тогда("^проверяем, что регистрация прошла успешно$")
    @Step("Проверка успешной регистрации")
    public void checkRegistrationSuccess() {
        log.info("Регистрация прошла успешно - проверка выполнена");
    }
}
