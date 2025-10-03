package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private SelenideElement usernameInput = $x("//input[@id='login-form-username']");
    private SelenideElement passwordInput = $x("//input[@id='login-form-password']");
    private SelenideElement loginButton = $x("//input[@id='login']");

    public void enterUsername(String username) {
        usernameInput.setValue(username);
    }

    public void enterPassword(String password) {
        passwordInput.setValue(password);
    }

    public void clickLogin() {
        loginButton.click();
    }


    public void login(String username, String password) {
        enterUsername(username);
        getUsernameInput().shouldHave(value(username));

        enterPassword(password);
        getPasswordInput().shouldHave(value(password));

        clickLogin();

    }

    public SelenideElement getUsernameInput() {
        return usernameInput;
    }

    public SelenideElement getPasswordInput() {
        return passwordInput;
    }
}
