package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class SprintPage {

    private final SelenideElement sprintInput = $x("//input[@id='customfield_10104-field']");

    public SprintPage enterSprintAndPressEnter(String text) {
        sprintInput.shouldBe(visible).click();
        sprintInput.setValue(text);
        actions().sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        return this;
    }
}