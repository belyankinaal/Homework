package ifellow.belyankina.steps;

import ifellow.belyankina.util.Specification;
import io.cucumber.java.ru.Дано;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.logging.Logger;

public class SpecSteps {

    private static final Logger log = Logger.getLogger(SpecSteps.class.getName());

    @Дано("^установлена спецификация localhost API$")
    @Step("Установка спецификации localhost API")
    public void setupSpecification() {
        RestAssured.requestSpecification = Specification.forLocalApi();
        log.info("Спецификация localhost установлена");
    }
}
