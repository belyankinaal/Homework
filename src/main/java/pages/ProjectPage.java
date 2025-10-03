package pages;

import com.codeborne.selenide.SelenideElement;
import util.CustomProperties;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
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

    public void clickViewAllIssues() {
        issuesMenuLink.shouldBe(visible).click();
        issuesSearchLink.shouldBe(visible).click();
        sleep(2000);
    }

    public boolean isResolutionFilterPresent() {
        return resolutionFilterButton.exists() && resolutionFilterButton.isDisplayed();
    }

    public void removeResolutionFilter() {
        System.out.println("Нажимаем на 'Не Решен'");
        resolutionFilterButton.shouldBe(visible).click();
        unresolvedLabel.shouldBe(visible).click();
        System.out.println("Ждем применения фильтра");
        sleep(2000);
        System.out.println("Закрываем меню ESC");
        actions().sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
    }

    public String getIssuesCountText() {
        SelenideElement countElement = findIssuesCountElement();
        countElement.shouldBe(visible);
        String text = countElement.getText();
        System.out.println("Найден текст счетчика: " + text);
        return text;
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
                System.out.println("Найден счетчик по XPath: " + xpath);
                return element;
            }
        }

        return $x("//*[matches(text(), '\\d')][not(self::script)]");
    }

    public int extractNumberFromText(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        System.out.println("Парсим текст: " + text);

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text.replaceAll("[\\s,]", ""));

        int lastNumber = 0;
        while (matcher.find()) {
            try {
                lastNumber = Integer.parseInt(matcher.group());
                System.out.println("Найдено число: " + lastNumber);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка парсинга числа: " + matcher.group());
            }
        }

        if (lastNumber > 0) {
            return lastNumber;
        }

        System.out.println("Число не найдено в тексте: " + text);
        return 0;
    }

    public void clickCreateIssue() {
        createIssueButton.shouldBe(visible).click();
    }

    public void enterSummaryAndSubmit(String summaryText) {
        summaryInput.shouldBe(visible).click();
        summaryInput.setValue(summaryText);

        submitButton.shouldBe(visible).click();

        SelenideElement successMessage = $(".aui-message-success");
        successMessage.shouldBe(visible);
        successMessage.shouldNotBe(visible, Duration.ofSeconds(10));
    }


    public String getNewIssueSummary() {
        return newIssueSummary.shouldBe(visible).getText();
    }

    public void navigateBackToIssues() {
        String currentUrl = webdriver().driver().url();
        if (currentUrl.contains("browse")) {
            open(CustomProperties.getProperty("web.url") + "/projects/TEST/issues");
            sleep(3000);
        }
    }

    public void refreshIssuesList() {
        refresh();
        sleep(3000);
    }

    public void waitForIssueCountToIncrease(int initialCount) {
        System.out.println("Ожидаем увеличения счетчика с " + initialCount);

        long startTime = System.currentTimeMillis();
        long timeout = 15000;

        while (System.currentTimeMillis() - startTime < timeout) {
            String currentText = getIssuesCountText();
            int currentCount = extractNumberFromText(currentText);

            if (currentCount > initialCount) {
                System.out.println("Счетчик увеличился: было " + initialCount + ", стало: " + currentCount);
                return;
            }

            System.out.println("Счетчик еще не увеличился. Текущее значение: " + currentCount);
            sleep(2000);
        }

        String finalText = getIssuesCountText();
        int finalCount = extractNumberFromText(finalText);
        throw new AssertionError("Счетчик задач не увеличился за 15 секунд. Был: " + initialCount + ", остался: " + finalCount);
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
        SelenideElement visualTab = $x("//li[@data-mode='wysiwyg']//button[text()='Визуальный']")
                .should(exist); // ждём, пока появится в DOM
        SelenideElement textTab = $x("//li[@data-mode='source']//button[text()='Текст']")
                .should(exist); // ждём, пока появится в DOM

        String visualPressed = visualTab.getAttribute("aria-pressed");
        String textPressed = textTab.getAttribute("aria-pressed");

        // Здесь убираем защиту от null — вызываем equalsIgnoreCase на атрибутах, которые могут быть null
        if (visualPressed.equalsIgnoreCase("false") && textPressed.equalsIgnoreCase("true")) {
            System.out.println("Выбран режим 'Текст'. Переключаемся на 'Визуальный'.");
            visualTab.shouldBe(visible).click();
        } else {
            System.out.println("Режим 'Визуальный' уже выбран.");
        }

    }

}
