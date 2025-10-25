package pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TaskSearchPage {

    private final SelenideElement issuesMenuLink = $x("//a[@id='find_link']").as("Меню задач");
    private final SelenideElement issuesSearchLink = $x("//a[@id='issues_new_search_link_lnk']").as("Ссылка поиска задач");
    private final SelenideElement searchInput = $x("//input[@id='searcher-query']").as("Поле поиска задач");
    private final SelenideElement firstFoundTask = $x("//a[contains(@class,'issue-link') and starts-with(@data-issue-key, 'TEST-') and text()=substring(@data-issue-key, 1, string-length(@data-issue-key))]").as("Первая найденная задача");


    public TaskSearchPage clickViewAllTasks() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
        return this;
    }

    public TaskSearchPage searchForTask(String text) {
        searchInput.shouldBe(visible).setValue(text).pressEnter();
        firstFoundTask.scrollTo().shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public void openTask() {
        firstFoundTask.shouldBe(visible).click();
    }
}
