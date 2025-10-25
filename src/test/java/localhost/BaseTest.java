package localhost;

import ifellow.belyankina.util.Specification;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    public static void setUp() {
        logger.info("Запуск настроек BaseTest для API-тестов");
        RestAssured.requestSpecification = Specification.forLocalApi();
    }

    protected void step(String name, Runnable action) {
        Allure.step(name, () -> {
            action.run();
            return null;
        });
    }

    protected void attach(String name, String content) {
        Allure.addAttachment(name, content);
        logger.info("{}: {}", name, content);
    }
}
