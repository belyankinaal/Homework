package HW3_Belyankina;

import model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProjectPage;
import util.CustomProperties;
import util.WebHooks;

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
        open(CustomProperties.getProperty("web.url"));
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Test
    @DisplayName("Проект Тест")
    public void openProjectTest() {
        open(CustomProperties.getProperty("web.url"));
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));
    }

    @Test
    @DisplayName("Проверка количества задач")
    public void createIssueTest() {
        open(CustomProperties.getProperty("web.url"));
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        issuePage.clickCreateIssue();
        String summaryText = "A1 ";
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();
        issuePage.waitForIssueCountToIncrease(countBefore);

        String countTextAfter = issuePage.getIssuesCountText();
        int countAfter = issuePage.extractNumberFromText(countTextAfter);

        assertTrue(countAfter == countBefore + 1,
                "Количество задач должно увеличиться на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    @Test
    @DisplayName("Проверка количества задач и поиск созданной задачи")
    public void createTest() {
        open(CustomProperties.getProperty("web.url"));
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        issuePage.clickCreateIssue();
        String summaryText = "A1 ";
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();
        issuePage.waitForIssueCountToIncrease(countBefore);

        String countTextAfter = issuePage.getIssuesCountText();
        int countAfter = issuePage.extractNumberFromText(countTextAfter);

        assertTrue(countAfter == countBefore + 1,
                "Количество задач должно увеличиться на 1. Было: " + countBefore + ", стало: " + countAfter);

        // ---- новый код для поиска задачи ----
        String searchText = "TestSeleniumATHomework";
        issuePage.searchForTask(searchText);

        // Ждём появления ссылки на задачу, с классом issue-link и ключом, содержащим "TEST-"
        issuePage.clickOnFoundTask();

        // Проверяем статус задачи — ожидаем "Сделать"
        issuePage.verifyTaskStatus("СДЕЛАТЬ");


        // Проверяем версию исправления — ожидаем "Version 2.0"
        issuePage.verifyAffectedVersions("Version 2.0");
    }

}
