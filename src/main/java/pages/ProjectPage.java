package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPage {

    private final SelenideElement projectsButton = $x("//*[@id='browse_link']");
    private final SelenideElement testProjectLink = $x("//*[@id='admin_main_proj_link_lnk']");

    private final SelenideElement severitySelect = $x("//select[@id='customfield_10400']");
    private final TaskLinksPage taskLinksPage = new TaskLinksPage();
    private final SprintPage sprintPages = new SprintPage();
    private final VersionPage versionPages = new VersionPage();

    public ProjectPage openProjectsMenu() {
        projectsButton.shouldBe(visible).click();
        return this;
    }

    public ProjectPage selectTestProject() {
        testProjectLink.shouldBe(visible).click();
        return this;
    }

    // Методы из ProjectPage
    public ProjectPage verifyTaskStatus(String status) {
        $x("//div[@class='wrap']//span[@id='status-val']/span[normalize-space(text())='" + status + "']")
                .shouldBe(visible);
        return this;
    }

    public ProjectPage verifyFixVersion(String version) {
        $x("//span[@id='fixVersions-field']//a[normalize-space(text())='" + version + "']")
                .shouldBe(visible);
        return this;
    }

    public ProjectPage selectSeverityByValue(String value) {
        severitySelect.shouldBe(visible).selectOptionByValue(value);
        return this;
    }

    public ProjectPage enterIssueLinkAndPressEnter(String text) {
        taskLinksPage.enterIssueLinkAndPressEnter(text);
        return this;
    }

    public ProjectPage enterSprintAndPressEnter(String text) {
        sprintPages.enterSprintAndPressEnter(text);
        return this;
    }

    public ProjectPage selectFixVersionByText(String version) {
        versionPages.selectFixVersionByText(version);
        return this;
    }
}
