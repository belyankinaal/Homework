package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TaskSearchPage {

    private final SelenideElement issuesMenuLink = $x("//a[@id='find_link']");
    private final SelenideElement issuesSearchLink = $x("//a[@id='issues_new_search_link_lnk']");
    private final SelenideElement searchInput = $x("//input[@id='searcher-query']");
    private final SelenideElement firstFoundTask = $x("//a[contains(@class,'issue-link') and contains(@data-issue-key,'TEST-')]");

    public TaskSearchPage clickViewAllTasks() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
        return this;
    }

    public TaskSearchPage searchForTask(String text) {
        searchInput.shouldBe(visible).setValue(text).pressEnter();
        firstFoundTask.shouldBe(visible);
        return this;
    }

    public TaskSearchPage openTask() {
        firstFoundTask.shouldBe(visible).click();
        return this;
    }
}
