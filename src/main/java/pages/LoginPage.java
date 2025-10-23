package pages;

import com.codeborne.selenide.SelenideElement;
import util.CustomProperties;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class LoginPage {

    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Поле ввода логина");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Поле ввода пароля");
    private final SelenideElement loginButton = $x("//input[@id='login']").as("Кнопка войти");

    public LoginPage enterUsername(String username) {
        usernameInput.setValue(username).shouldHave(value(username));
        return this;
    }

    public LoginPage enterPassword() {
        String password = CustomProperties.getProperty("user.password");
        executeJavaScript("arguments[0].value = arguments[1]", passwordInput, password);
        return this;
    }

    public void clickLogin() {
        loginButton.click();
    }
}