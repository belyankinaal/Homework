package util;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebHooks {

    @BeforeAll
    static void globalSetup() {
        CustomProperties.loadProperties();
    }

    @BeforeEach
    void initBrowser() {

        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");


        Configuration.browserCapabilities = options;


        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30000;

        Selenide.open(CustomProperties.getProperty("web.url"));

        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }
}
