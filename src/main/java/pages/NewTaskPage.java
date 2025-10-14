package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NewTaskPage {

    private final SelenideElement summaryInput = $x("//input[@id='summary']").as("Поле для ввода заголовка задачи");
    private final SelenideElement submitButton = $x("//input[@id='create-issue-submit']").as("Кнопка отправить заполненную задачу");
    private final SelenideElement successMessage = $x("//div[contains(@class,'aui-message-success')]").as("Уведомление об успешном создании");
    private final SelenideElement successMessageLink = $x("//div[contains(@class,'aui-message-success')]//a[contains(@href,'browse/')]").as("Ссылка на созданную задачу");


    public NewTaskPage enterSummary(String text) {
        summaryInput.shouldBe(visible).setValue(text);
        return this;
    }

    public NewTaskPage submitIssue() {
        submitButton.shouldBe(visible).click();
        successMessage.shouldBe(visible);
        return this;
    }

    public NewTaskPage clickSubmitAndWaitForSuccessAndOpenIssue() {
        submitButton.shouldBe(visible).click();
        successMessage.shouldBe(visible);
        successMessageLink.shouldBe(visible).click();
        return this;
    }
}