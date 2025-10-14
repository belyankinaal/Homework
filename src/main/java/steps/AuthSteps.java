package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import util.CustomProperties;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class AuthSteps {

    private final LoginPage loginPage = new LoginPage();

    private final String userName = CustomProperties.getProperty("user.name");
    private final String userPassword = CustomProperties.getProperty("user.password");

    @Step("Авторизация")
    public void login() {
        loginPage.login(userName, userPassword);
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }
}