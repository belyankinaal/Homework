package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPage {

    private final SelenideElement createIssueButton = $x("//a[@id='create_link']");
    private final SelenideElement summaryInput = $x("//input[@id='summary']");
    private final SelenideElement submitButton = $x("//input[@id='create-issue-submit']");
    private final SelenideElement issuesMenuLink = $x("//a[@id='find_link']");
    private final SelenideElement issuesSearchLink = $x("//a[@id='issues_new_search_link_lnk']");
    private final SelenideElement searchInput = $x("//input[@id='searcher-query']");
    private final SelenideElement firstFoundTask = $x("//a[contains(@class,'issue-link') and contains(@data-issue-key,'TEST-')]");
    private final SelenideElement fixVersionSelect = $x("//select[@id='fixVersions']");
    private final SelenideElement issueLinksTextarea = $x("//textarea[@id='issuelinks-issues-textarea']");
    private final SelenideElement sprintInput = $x("//input[@id='customfield_10104-field']");
    private final SelenideElement severitySelect = $x("//select[@id='customfield_10400']");

    public ProjectPage clickViewAllIssues() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
        return this;
    }

    public ProjectPage clickCreateIssue() {
        createIssueButton.shouldBe(visible).click();
        return this;
    }

    public ProjectPage enterSummary(String text) {
        summaryInput.shouldBe(visible).setValue(text);
        return this;
    }

    public ProjectPage submitIssue() {
        submitButton.shouldBe(visible).click();
        $x("//div[contains(@class,'aui-message-success')]").shouldBe(visible);
        return this;
    }

    public ProjectPage refreshIssuesList() {
        refresh();
        return this;
    }

    public ProjectPage navigateBackToIssues() {
        String currentUrl = webdriver().driver().url();
        if (currentUrl.contains("browse")) {
            open("/projects/TEST/issues");
        }
        return this;
    }

    public String getIssuesCountText() {
        SelenideElement countElement = $x("//span[contains(@class,'results-count-total')]");
        countElement.shouldBe(visible);
        return countElement.getText();
    }

    public int extractNumberFromText(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text.replaceAll("[\\s,]", ""));
        int lastNumber = 0;
        while (matcher.find()) lastNumber = Integer.parseInt(matcher.group());
        return lastNumber;
    }

    public ProjectPage searchForTask(String text) {
        searchInput.shouldBe(visible).setValue(text).pressEnter();
        firstFoundTask.shouldBe(visible);
        return this;
    }

    public ProjectPage openTask() {
        firstFoundTask.shouldBe(visible).click();
        return this;
    }

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

    public ProjectPage ensureVisualEditorSelected() {
        SelenideElement visualTab = $x("//li[@data-mode='wysiwyg']//button[text()='Визуальный']");
        SelenideElement textTab = $x("//li[@data-mode='source']//button[text()='Текст']");
        if ("false".equalsIgnoreCase(visualTab.getAttribute("aria-pressed")) &&
                "true".equalsIgnoreCase(textTab.getAttribute("aria-pressed"))) {
            visualTab.shouldBe(visible).click();
        }
        return this;
    }

    public ProjectPage enterTextInVisualEditor(String text) {
        ((JavascriptExecutor) webdriver().object()).executeScript("tinymce.get(0).setContent(arguments[0]);", text);
        return this;
    }

    public ProjectPage enterTextInSecondVisualEditor(String text) {
        ((JavascriptExecutor) webdriver().object()).executeScript("tinymce.get(1).setContent(arguments[0]);", text);
        return this;
    }

    public ProjectPage enterIssueLinkAndPressEnter(String text) {
        issueLinksTextarea.shouldBe(visible).click();
        actions().sendKeys(text).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        return this;
    }

    public ProjectPage enterSprintAndPressEnter(String text) {
        sprintInput.shouldBe(visible).click();
        sprintInput.setValue(text);
        actions().sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        return this;
    }

    public ProjectPage selectSeverityByValue(String value) {
        severitySelect.shouldBe(visible).selectOptionByValue(value);
        return this;
    }

    public ProjectPage selectFixVersionByText(String version) {
        fixVersionSelect.shouldBe(visible).selectOption(version);
        return this;
    }

    public ProjectPage clickSubmitAndWaitForSuccessAndOpenIssue() {
        submitButton.shouldBe(visible).click();
        $x("//div[contains(@class,'aui-message-success')]").shouldBe(visible);
        $x("//div[contains(@class,'aui-message-success')]//a[contains(@href,'browse/')]").shouldBe(visible).click();
        return this;
    }

    public ProjectPage openBusinessProcessAndSelect(String actionText) {

        $x("//a[@id='opsbar-transitions_more']").shouldBe(visible, Duration.ofSeconds(10)).click();

        $$x("//span[@class='trigger-label']").findBy(text(actionText))
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        $x("//input[@id='issue-workflow-transition-submit' and @value='" + actionText + "']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        SelenideElement successMessage = $x("//div[contains(@class,'aui-message-success')]");
        successMessage.shouldBe(visible, Duration.ofSeconds(10));
        successMessage.shouldNotBe(visible, Duration.ofSeconds(15));

        return this;
    }
}
