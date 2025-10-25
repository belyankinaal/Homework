package HW3_Belyankina;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.WebHooks;

public class BaseTest extends WebHooks {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    public static void before() {
        logger.info("Запуск настроек для BaseTest");
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
        );
    }
}