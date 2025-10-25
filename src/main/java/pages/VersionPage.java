package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class VersionPage {
    private final SelenideElement fixVersionSelect = $x("//select[@id='fixVersions']").as("Fix Version");

    public void selectFixVersionByText(String version) {
        fixVersionSelect.shouldBe(visible).selectOption(version);
    }
}