package ifellow.belyankina.util;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;

@Slf4j
public class BaseTest {

    @BeforeAll
    public static void setUp() {
        log.info("Запуск настроек BaseTest для API-тестов");
        RestAssured.requestSpecification = Specification.forLocalApi();
    }
}
