package util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebHooks {

    @BeforeAll
    public static void globalSetup() {
        CustomProperties.loadProperties();
    }

    @BeforeEach
    public void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30000;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Configuration.browserCapabilities = options;

        // Заменено по замечанию преподавателя
        Selenide.open(CustomProperties.getProperty("web.url"));
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }
}
