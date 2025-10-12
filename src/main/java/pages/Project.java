package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Project {

    private final SelenideElement projectsButton = $x("//*[@id='browse_link']");
    private final SelenideElement testProjectLink = $x("//*[@id='admin_main_proj_link_lnk']");

    public Project openProjectsMenu() {
        projectsButton.shouldBe(visible).click();
        return this;
    }

    public Project selectTestProject() {
        testProjectLink.shouldBe(visible).click();
        return this;
    }
}
