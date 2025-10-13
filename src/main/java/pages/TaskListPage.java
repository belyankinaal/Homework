package pages;

import com.codeborne.selenide.SelenideElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TaskListPage {

    private final SelenideElement createIssueButton = $x("//a[@id='create_link']");
    private final SelenideElement issuesMenuLink = $x("//a[@id='find_link']");
    private final SelenideElement issuesSearchLink = $x("//a[@id='issues_new_search_link_lnk']");
    private final SelenideElement countElement = $x("//span[contains(@class,'results-count-total')]");

    public TaskListPage clickViewAllIssues() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
        return this;
    }

    public TaskListPage clickCreateIssue() {
        createIssueButton.shouldBe(visible).click();
        return this;
    }

    public TaskListPage refreshIssuesList() {
        refresh();
        return this;
    }

    public TaskListPage navigateBackToIssues() {
        String currentUrl = webdriver().driver().url();
        if (currentUrl.contains("browse")) {
            open("/projects/TEST/issues");
        }
        return this;
    }

    public String getIssuesCountText() {
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
}