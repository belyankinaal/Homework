package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class TaskLinksPage {

    private final SelenideElement taskLinksTextarea = $x("//textarea[@id='issuelinks-issues-textarea']").as("Поле для ввода ссылки на задачу");

    public void enterTaskLinkAndSubmit(String text) {
        taskLinksTextarea.shouldBe(visible).click();
        actions().sendKeys(text).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
    }
}
