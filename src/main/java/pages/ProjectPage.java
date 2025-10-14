package pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProjectPage {

    private final SelenideElement searchInput = $x("//input[@id='searcher-query']").as("Поле поиск задач");
    private final SelenideElement firstFoundTask = $x("//a[contains(@class,'issue-link') and contains(@data-issue-key,'TEST-')]").as("Нашли задачу по поиску");
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

    public ProjectPage verifyFixVersion(String version) {
        fixVersion.shouldBe(visible).shouldHave(text(version));
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

    public TaskSearchPage searchForTask(String text) {
        searchInput.shouldBe(visible).setValue(text).pressEnter();
        firstFoundTask.scrollTo().shouldBe(visible, Duration.ofSeconds(10));
        return new TaskSearchPage();
    }
}