package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NavigationPage {

    private final SelenideElement projectsMenu = $x("//a[@id='projects-menu']");
    private final SelenideElement testProjectLink = $x("//a[contains(@href, '/projects/TEST')]");

    public NavigationPage openProjectsMenu() {
        projectsMenu.shouldBe(visible).click();
        return this;
    }

    public NavigationPage selectTestProject() {
        testProjectLink.shouldBe(visible).click();
        return this;
    }
}
