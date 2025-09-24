package HW3_Belyankina;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ProjectPage {

    private SelenideElement viewAllIssuesLink = $(By.xpath("//a[contains(text(), 'Посмотреть все задачи и фильтры')]"));
    private SelenideElement resolutionFilterButton = $(By.cssSelector("div[data-id='resolution']"));
    private SelenideElement unresolvedLabel = $(By.xpath("//label[@class='item-label checkbox' and @title='Не решен']"));

    private SelenideElement issuesCountElement = $(By.xpath("//span[contains(@class, 'results-count-total')] | //span[contains(text(), 'задач')] | //*[contains(text(), 'из')][not(contains(text(), 'избранных'))]"));

    private SelenideElement createIssueButton = $(By.id("create_link"));
    private SelenideElement summaryTextarea = $(By.id("summary"));
    private SelenideElement newIssueSummary = $(By.id("summary-val"));
    private SelenideElement submitButton = $(By.id("create-issue-submit"));

    public void clickViewAllIssues() {
        viewAllIssuesLink.shouldBe(visible).click();
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
            SelenideElement element = $(By.xpath(xpath));
            if (element.exists() && element.isDisplayed()) {
                System.out.println("Найден счетчик по XPath: " + xpath);
                return element;
            }
        }

        return $("body").find(By.xpath(".//*[text()[contains(., '1') or contains(., '2') or contains(., '3') or contains(., '4') or contains(., '5') or contains(., '6') or contains(., '7') or contains(., '8') or contains(., '9') or contains(., '0')]][not(self::script)]"));
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
        summaryTextarea.shouldBe(visible).setValue(summaryText);
        submitButton.shouldBe(visible).click();

        $(".aui-message-success").shouldBe(visible);
        sleep(2000);
    }

    public String getNewIssueSummary() {
        return newIssueSummary.shouldBe(visible).getText();
    }

    public void navigateBackToIssues() {
        String currentUrl = webdriver().driver().url();
        if (currentUrl.contains("browse")) {
            open(CustomProperties.getWebUrl() + "/projects/TEST/issues");

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
                System.out.println("Счетчик увеличился: было " + initialCount + ", стало " + currentCount);
                return;
            }

            System.out.println("Счетчик еще не увеличился. Текущее значение: " + currentCount);
            sleep(2000);
        }

        String finalText = getIssuesCountText();
        int finalCount = extractNumberFromText(finalText);
        throw new AssertionError("Счетчик задач не увеличился за 15 секунд. Был: " + initialCount + ", остался: " + finalCount);
    }
}