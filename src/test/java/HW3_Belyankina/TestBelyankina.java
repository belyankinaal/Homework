package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBelyankina extends WebHooks {

    private LoginPage loginPage = new LoginPage();
    private ProjectPage issuePage = new ProjectPage();

    @Test
    @DisplayName("Авторизация")
    public void autorizationTest() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Test
    @DisplayName("Проект Тест")
    public void openProjectTest() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        project.openIssuesPage();
        webdriver().shouldHave(urlContaining("/projects/TEST/issues"));
    }

    @Test
    @DisplayName("Проверка количества задач")
    public void createIssueTest() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        project.openIssuesPage();
        webdriver().shouldHave(urlContaining("/projects/TEST/issues"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        if (issuePage.isResolutionFilterPresent()) {
            System.out.println("Чек-бокс на 'Не Решен' выставлен, убираем");
            issuePage.removeResolutionFilter();

            String updatedCountText = issuePage.getIssuesCountText();
            int updatedCount = issuePage.extractNumberFromText(updatedCountText);
            System.out.println("Количество задач после снятия фильтра: " + updatedCount);
            countBefore = updatedCount;
        } else {
            System.out.println("Чек-бокс на 'Не Решен' отсутствует");
        }

        issuePage.clickCreateIssue();
        String summaryText = "Тест A1 " + System.currentTimeMillis();
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();
        issuePage.waitForIssueCountToIncrease(countBefore);

        String countTextAfter = issuePage.getIssuesCountText();
        System.out.println("Количество задач после создания новой задачи: " + countTextAfter);
        int countAfter = issuePage.extractNumberFromText(countTextAfter);

        assertTrue(countAfter == countBefore + 1,
                "Количество задач должно увеличиться на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    //не успела запустить и проверить код на работоспособность, упал сайт
    @Test
    @DisplayName("Просмотр задачи")
    public void watchTaskTest() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        project.openIssuesPage();
        webdriver().shouldHave(urlContaining("/projects/TEST/issues"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        if (issuePage.isResolutionFilterPresent()) {
            System.out.println("Чек-бокс на 'Не Решен' выставлен, убираем");
            issuePage.removeResolutionFilter();

            String updatedCountText = issuePage.getIssuesCountText();
            int updatedCount = issuePage.extractNumberFromText(updatedCountText);
            System.out.println("Количество задач после снятия фильтра: " + updatedCount);
            countBefore = updatedCount;
        } else {
            System.out.println("Чек-бокс на 'Не Решен' отсутствует");
        }

        issuePage.clickCreateIssue();
        String summaryText = "Тест A1 " + System.currentTimeMillis();
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();
        issuePage.waitForIssueCountToIncrease(countBefore);

        String countTextAfter = issuePage.getIssuesCountText();
        System.out.println("Количество задач после создания новой задачи: " + countTextAfter);
        int countAfter = issuePage.extractNumberFromText(countTextAfter);

        assertTrue(countAfter == countBefore + 1,
                "Количество задач должно увеличиться на 1. Было: " + countBefore + ", стало: " + countAfter);

        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        project.openProjectsMenu();
        project.selectTestProject();
        project.openIssuesPage();
        webdriver().shouldHave(urlContaining("/projects/TEST/issues"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));

        issuePage.searchForTask("TestSeleniumATHomework");

        issuePage.clickOnFoundTask();

        issuePage.verifyTaskStatus("Сделать");
        issuePage.verifyAffectedVersions("Version 2.0");
    }
}