package HW3_Belyankina;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebHooks {

    @BeforeAll
    public static void globalSetup() {
        CustomProperties.loadProperties();

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @BeforeEach
    public void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30000;
        Configuration.browserSize = null;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Configuration.browserCapabilities = options;

        Selenide.open(CustomProperties.getWebUrl());

        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
}
