package model;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Project {

    private SelenideElement projectsButton = $x("//*[@id='browse_link']");
    private SelenideElement testProjectLink = $x("//*[@id='admin_main_proj_link_lnk']");

    private SelenideElement issuesButton = $x("//span[@class='aui-nav-item-label' and @title='Задачи']/ancestor::a[1]");

    public void openProjectsMenu() {
        projectsButton.shouldBe(visible).click();
    }

    public void selectTestProject() {
        testProjectLink.shouldBe(visible).click();
    }

    public void openIssuesPage() {
        issuesButton.shouldBe(visible).click();
    }
}
