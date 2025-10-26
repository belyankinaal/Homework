package ifellow.belyankina.steps;

import ifellow.belyankina.util.Specification;
import io.cucumber.java.ru.Дано;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpecSteps {

    @Дано("^RestAssured настроен для работы с локальным API$")
    @Step("RestAssured настроен для работы с локальным API")
    public void setupSpecification() {
        RestAssured.requestSpecification = Specification.forLocalApi();
        log.info("RestAssured настроен для работы с локальным API");
    }
}
