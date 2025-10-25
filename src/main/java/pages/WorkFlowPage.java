package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;


public class WorkFlowPage {

    private final SelenideElement transitionsMoreButton = $x("//a[@id='opsbar-transitions_more']");
    private final ElementsCollection triggerLabels = $$x("//span[@class='trigger-label']");
    private final SelenideElement successMessage = $x("//div[contains(@class,'aui-message-success')]");

    private SelenideElement submitButton(String actionText) {
        return $x("//input[@id='issue-workflow-transition-submit' and @value='" + actionText + "']");
    }

    public void openBusinessProcessAndSelect(String actionText) {
        transitionsMoreButton.shouldBe(visible, Duration.ofSeconds(10)).click();

        triggerLabels.findBy(text(actionText))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        submitButton(actionText).shouldBe(visible, Duration.ofSeconds(10)).click();

        successMessage.shouldBe(visible, Duration.ofSeconds(10));
        successMessage.shouldNotBe(visible, Duration.ofSeconds(15));
    }
}