package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.LoginPage;
import util.CustomProperties;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

@Epic("Авторизация на сайте")
public class AuthSteps {

    private final LoginPage loginPage = new LoginPage();

    private final String userName = CustomProperties.getProperty("user.name");
    private final String userPassword = CustomProperties.getProperty("user.password");

    @Step("Авторизация пользователя с логином {userName}")
    @Story("Ввод логина и пароля для авторизации")
    public void login() {
        loginPage.login(userName, userPassword);
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }


}