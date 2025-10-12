package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.LoginPage;
import util.CustomProperties;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class AutorizationSteps {
    private final LoginPage loginPage = new LoginPage();

    @Когда("^открыта страница сайта$")
    public void openLoginPage() {
    }

    @И("^пользователь вводит логин и пароль$")
    public void login() {
        String username = CustomProperties.getProperty("user.name");
        String password = CustomProperties.getProperty("user.password");
        loginPage.enterUsername(username)
                .enterPassword(password)
                .clickLogin();
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Тогда("^он видит домашнюю страницу$")
    public void checkHomePage() {
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Когда("^пользователь авторизован в системе$")
    public void userLoggedIn() {
        String username = CustomProperties.getProperty("user.name");
        String password = CustomProperties.getProperty("user.password");
        login();
    }
}
