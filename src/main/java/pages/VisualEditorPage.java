package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.webdriver;

public class VisualEditorPage {

    private final int editorIndex;

    private final SelenideElement visualTab = $x("//li[@data-mode='wysiwyg']//button[text()='Визуальный']");
    private final SelenideElement textTab = $x("//li[@data-mode='source']//button[text()='Текст']");

    public VisualEditorPage(int editorIndex) {
        this.editorIndex = editorIndex;
    }

    public VisualEditorPage ensureVisualEditorSelected() {
        if ("false".equalsIgnoreCase(visualTab.getAttribute("aria-pressed")) &&
                "true".equalsIgnoreCase(textTab.getAttribute("aria-pressed"))) {
            visualTab.shouldBe(visible).click();
        }
        return this;
    }

    public VisualEditorPage setContent(String text) {
        ((JavascriptExecutor) webdriver().object())
                .executeScript("tinymce.get(arguments[0]).setContent(arguments[1]);", editorIndex, text);
        return this;
    }
}