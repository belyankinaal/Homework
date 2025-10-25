package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPage {

    private final SelenideElement severitySelect = $x("//select[@id='customfield_10400']").as("Приоритет задачи");
    private final SelenideElement taskStatus = $x("//div[@class='wrap']//span[@id='status-val']/span").as("Статус задачи");
    private final SelenideElement fixVersion = $x("//span[@id='fixVersions-field']//a").as("Fix Version задачи");


    private final TaskLinksPage taskLinksPage = new TaskLinksPage();
    private final SprintPage sprintPages = new SprintPage();
    private final VersionPage versionPages = new VersionPage();


    public ProjectPage verifyTaskStatus(String status) {
        taskStatus.shouldBe(visible).shouldHave(text(status));
        return this;
    }

    public void verifyFixVersion(String version) {
        fixVersion.shouldBe(visible).shouldHave(text(version));
    }

    public void selectSeverityByValue(String value) {
        severitySelect.shouldBe(visible).selectOptionByValue(value);
    }

    public void enterIssueLinkAndPressEnter(String text) {
        taskLinksPage.enterTaskLinkAndSubmit(text);
    }

    public void enterSprintAndPressEnter(String text) {
        sprintPages.enterSprintAndPressEnter(text);
    }

    public void selectFixVersionByText(String version) {
        versionPages.selectFixVersionByText(version);
    }
}