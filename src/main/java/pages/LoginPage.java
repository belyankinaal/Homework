package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Поле ввода логина");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Поле ввода пароля");
    private final SelenideElement loginButton = $x("//input[@id='login']").as("Кнопка войти");


    public LoginPage enterUsername(String username) {
        usernameInput.setValue(username).shouldHave(value(username));
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password).shouldHave(value(password));
        return this;
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}