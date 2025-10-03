package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import util.CustomProperties;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPage {

    private SelenideElement issuesMenuLink = $x("//a[@id='find_link']");
    private SelenideElement issuesSearchLink = $x("//a[@id='issues_new_search_link_lnk']");
    private SelenideElement resolutionFilterButton = $x("div[data-id='resolution']");
    private SelenideElement unresolvedLabel = $x("//label[@class='item-label checkbox' and @title='Не решен']");
    private SelenideElement createIssueButton = $x("//a[@id='create_link']");
    private SelenideElement summaryInput = $x("//input[@id='summary']");
    private SelenideElement newIssueSummary = $x("//div[@id='summary-val']");
    private SelenideElement submitButton = $x("//input[@id='create-issue-submit']");
    private SelenideElement searchInput = $x("//input[@id='searcher-query']");
    private SelenideElement firstFoundTask = $x("//a[contains(@class, 'issue-link') and contains(@data-issue-key, 'TEST-')]");
    private SelenideElement fixVersionSelect = $x("//select[@id='fixVersions']");
    private SelenideElement issueLinksTextarea = $x("//textarea[@id='issuelinks-issues-textarea']");
    private SelenideElement sprintInput = $x("//input[@id='customfield_10104-field']");
    private SelenideElement severitySelect = $x("//select[@id='customfield_10400']");

    public void selectSeverityByValue(String value) {
        severitySelect.shouldBe(visible).selectOptionByValue(value);
    }

    public void clickSubmitAndWaitForSuccessAndOpenIssue() {
        submitButton.shouldBe(visible).click();
        SelenideElement successMessage = $(".aui-message-success").shouldBe(visible);
        SelenideElement issueLink = successMessage.$("a[href*='browse/']");
        issueLink.shouldBe(visible).click();
    }

    public void clickSubmitAndWaitForSuccess() {
        submitButton.shouldBe(visible).click();
        $(".aui-message-success").shouldBe(visible);
    }

    public void enterIssueLinkAndPressEnter(String text) {
        issueLinksTextarea.shouldBe(visible).click();
        actions().sendKeys(text).sendKeys(org.openqa.selenium.Keys.ENTER).perform();
    }

    public void enterSprintAndPressEnter(String text) {
        sprintInput.shouldBe(visible).click();
        sprintInput.setValue(text);
        actions().sendKeys(org.openqa.selenium.Keys.ENTER).perform();
    }

    public void typeSummaryText(String text) {
        summaryInput.shouldBe(visible).setValue(text);
    }

    public void enterIssueLinksText(String text) {
        issueLinksTextarea.shouldBe(visible).setValue(text);
    }

    public void selectFixVersionByText(String versionText) {
        fixVersionSelect.shouldBe(visible).selectOption(versionText);
    }

    public void selectFixVersionByValue(String value) {
        fixVersionSelect.shouldBe(visible).selectOptionByValue(value);
    }

    public void clickViewAllIssues() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
    }

    public boolean isResolutionFilterPresent() {
        return resolutionFilterButton.exists() && resolutionFilterButton.isDisplayed();
    }

    public void removeResolutionFilter() {
        resolutionFilterButton.shouldBe(visible).click();
        unresolvedLabel.shouldBe(visible).click();
        actions().sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
    }

    public String getIssuesCountText() {
        SelenideElement countElement = findIssuesCountElement();
        countElement.shouldBe(visible);
        return countElement.getText();
    }

    private SelenideElement findIssuesCountElement() {
        String[] xpaths = {
                "//span[contains(@class, 'results-count-total')]",
                "//span[contains(@class, 'results-count')]",
                "//*[contains(text(), 'задач')]",
                "//*[contains(text(), 'из')][not(contains(text(), 'избранных'))]",
                "//*[@data-id='issues']//span[contains(@class, 'count')]"
        };
        for (String xpath : xpaths) {
            SelenideElement element = $x(xpath);
            if (element.exists() && element.isDisplayed()) {
                return element;
            }
        }
        return $x("//*[matches(text(), '\\d')][not(self::script)]");
    }

    public int extractNumberFromText(String text) {
        if (text == null || text.isEmpty()) return 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text.replaceAll("[\\s,]", ""));
        int lastNumber = 0;
        while (matcher.find()) {
            try {
                lastNumber = Integer.parseInt(matcher.group());
            } catch (NumberFormatException ignored) {
            }
        }
        return lastNumber;
    }

    public void clickCreateIssue() {
        createIssueButton.shouldBe(visible).click();
    }

    public void enterSummaryAndSubmit(String summaryText) {
        summaryInput.shouldBe(visible).click();
        summaryInput.setValue(summaryText);
        submitButton.shouldBe(visible).click();
        $(".aui-message-success").shouldBe(visible).shouldNotBe(visible, Duration.ofSeconds(10));
    }

    public String getNewIssueSummary() {
        return newIssueSummary.shouldBe(visible).getText();
    }

    public void navigateBackToIssues() {
        String currentUrl = webdriver().driver().url();
        if (currentUrl.contains("browse")) {
            open(CustomProperties.getProperty("web.url") + "/projects/TEST/issues");
        }
    }

    public void refreshIssuesList() {
        refresh();
    }

    public void waitForIssueCountToIncrease(int initialCount) {
        long startTime = System.currentTimeMillis();
        long timeout = 15000;
        while (System.currentTimeMillis() - startTime < timeout) {
            int currentCount = extractNumberFromText(getIssuesCountText());
            if (currentCount > initialCount) return;
            sleep(1000);
        }
        throw new AssertionError("Счетчик задач не увеличился за 15 секунд");
    }

    public void searchForTask(String searchText) {
        searchInput.shouldBe(visible).setValue(searchText).pressEnter();
        firstFoundTask.shouldBe(visible);
    }

    public void clickOnFoundTask() {
        firstFoundTask.shouldBe(visible).click();
    }

    public void verifyTaskStatus(String expectedStatus) {
        $x("//div[@class='wrap']//span[@id='status-val']/span[contains(@class, 'jira-issue-status-lozenge') and normalize-space(text())='" + expectedStatus + "']")
                .shouldBe(visible);
    }

    public void verifyFixVersion(String expectedVersion) {
        $x("//span[@id='fixVersions-field']//a[normalize-space(text())='" + expectedVersion + "']")
                .shouldBe(visible);
    }

    public void ensureVisualEditorSelected() {
        SelenideElement visualTab = $x("//li[@data-mode='wysiwyg']//button[text()='Визуальный']").should(exist);
        SelenideElement textTab = $x("//li[@data-mode='source']//button[text()='Текст']").should(exist);
        if ("false".equalsIgnoreCase(visualTab.getAttribute("aria-pressed")) &&
                "true".equalsIgnoreCase(textTab.getAttribute("aria-pressed"))) {
            visualTab.shouldBe(visible).click();
        }
    }

    public void enterTextInVisualEditor(String text) {
        ((JavascriptExecutor) webdriver().object()).executeScript("tinymce.get(0).setContent(arguments[0]);", text);
    }

    public void enterTextInSecondVisualEditor(String text) {
        ((JavascriptExecutor) webdriver().object()).executeScript("tinymce.get(1).setContent(arguments[0]);", text);
    }

    public void clickWorkflowActionAndWaitSuccess(String actionText) {
        $x("//a[contains(@class,'issueaction-workflow-transition')]//span[normalize-space(text())='" + actionText + "']")
                .shouldBe(visible)
                .click();
        $(".aui-message-success").shouldBe(visible).shouldNotBe(visible, Duration.ofSeconds(10));
    }


    public void openBusinessProcessAndSelect(String menuItemText) {
        $x("//a[@id='opsbar-transitions_more']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        SelenideElement menuItem = $$("span.trigger-label")
                .findBy(text(menuItemText))
                .shouldBe(visible, Duration.ofSeconds(10));
        menuItem.click();

        $x("//input[@id='issue-workflow-transition-submit' and @value='" + menuItemText + "']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        $(".aui-message-success")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldNotBe(visible, Duration.ofSeconds(10));
    }

}
