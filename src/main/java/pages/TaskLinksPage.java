package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class TaskLinksPage {

    private final SelenideElement issueLinksTextarea = $x("//textarea[@id='issuelinks-issues-textarea']").as("Ссылка на задачу");

    public TaskLinksPage enterIssueLinkAndPressEnter(String text) {
        issueLinksTextarea.shouldBe(visible).click();
        actions().sendKeys(text).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        return this;
    }
}