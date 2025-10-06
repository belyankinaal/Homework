package HW3_Belyankina.test;

import model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProjectPage;
import utils.CustomProperties;
import utils.WebHooks;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBelyankina extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();

    @Test
    @DisplayName("Авторизация")
    public void authorizationTest() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Test
    @DisplayName("Открытие проекта")
    public void openProjectTest() {
        loginAndOpenProject();
        webdriver().shouldHave(urlContaining("/projects/TEST/issues"));
    }

    @Test
    @DisplayName("Создание новой задачи")
    public void createIssueTest() {
        loginAndOpenProject();

        projectPage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));

        int initialIssueCount = getCurrentIssueCount();

        if (projectPage.isResolutionFilterPresent()) {
            projectPage.removeResolutionFilter();
            initialIssueCount = getCurrentIssueCount();
        }

        projectPage.clickCreateIssue();
        String summaryText = "Тест A1 " + System.currentTimeMillis();
        projectPage.enterSummaryAndSubmit(summaryText);
        projectPage.navigateBackToIssues();
        projectPage.refreshIssuesList();
        projectPage.waitForIssueCountToIncrease(initialIssueCount);

        int finalIssueCount = getCurrentIssueCount();
        assertTrue(finalIssueCount == initialIssueCount + 1,
                "Количество задач должно увеличиться на 1. Было: " + initialIssueCount + ", стало: " + finalIssueCount);
    }

    @Test
    @DisplayName("Просмотр и проверка задачи")
    public void viewTaskTest() {
        loginAndOpenProject();

        projectPage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));

        projectPage.searchForTask("TestSeleniumATHomework");
        projectPage.clickOnFoundTask();

        projectPage.verifyTaskStatus("Сделать");
        projectPage.verifyAffectedVersions("Version 2.0");
    }

    private void loginAndOpenProject() {
        open(CustomProperties.getWebUrl());
        loginPage.login(CustomProperties.getUserName(), CustomProperties.getUserPassword());
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();
        project.openIssuesPage();
    }

    private int getCurrentIssueCount() {
        String countText = projectPage.getIssuesCountText();
        int count = projectPage.extractNumberFromText(countText);
        System.out.println("Текущее количество задач: " + count);
        return count;
    }
}