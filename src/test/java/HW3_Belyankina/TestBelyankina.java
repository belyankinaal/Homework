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
        // 1. Открываем главную страницу и логинимся
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

        open(CustomProperties.getProperty("web.url") + "/secure/RapidBoard.jspa?projectKey=TEST&rapidView=1");
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

        open(CustomProperties.getProperty("web.url") + "/secure/RapidBoard.jspa?projectKey=TEST&rapidView=1");
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        issuePage.clickCreateIssue();
        String summaryText = "A1";
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();

        String countTextAfter = issuePage.getIssuesCountText();
        int countAfter = issuePage.extractNumberFromText(countTextAfter);
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
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

        open(CustomProperties.getProperty("web.url") + "/secure/RapidBoard.jspa?projectKey=TEST&rapidView=1");
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));


        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        issuePage.clickCreateIssue();
        String summaryText = "A1";
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();

        String countTextAfter = issuePage.getIssuesCountText();
        int countAfter = issuePage.extractNumberFromText(countTextAfter);
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);

        String searchText = "TestSeleniumATHomework";
        issuePage.searchForTask(searchText);
        issuePage.clickOnFoundTask();

        issuePage.verifyTaskStatus("Сделать");
        issuePage.verifyFixVersion("Version 2.0");
    }

    @Test
    @DisplayName("Заведение дефекта")
    public void createBagTest() {
        open(CustomProperties.getProperty("web.url"));
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        Project project = new Project();
        project.openProjectsMenu();
        project.selectTestProject();

        open(CustomProperties.getProperty("web.url") + "/secure/RapidBoard.jspa?projectKey=TEST&rapidView=1");
        webdriver().shouldHave(urlContaining("/secure/RapidBoard.jspa"));

        issuePage.clickViewAllIssues();
        webdriver().shouldHave(urlContaining("/issues"));
        String countTextBefore = issuePage.getIssuesCountText();
        int countBefore = issuePage.extractNumberFromText(countTextBefore);
        System.out.println("Количество задач до создания: " + countBefore);

        issuePage.clickCreateIssue();
        String summaryText = "A1";
        issuePage.enterSummaryAndSubmit(summaryText);
        issuePage.navigateBackToIssues();
        issuePage.refreshIssuesList();

        String countTextAfter = issuePage.getIssuesCountText();
        int countAfter = issuePage.extractNumberFromText(countTextAfter);
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);

        String searchText = "TestSeleniumATHomework";
        issuePage.searchForTask(searchText);
        issuePage.clickOnFoundTask();

        issuePage.verifyTaskStatus("Сделать");
        issuePage.verifyFixVersion("Version 2.0");

        issuePage.clickCreateIssue();
        issuePage.typeSummaryText("A1");

        issuePage.ensureVisualEditorSelected();
        issuePage.enterTextInVisualEditor("Дефект");

        issuePage.selectFixVersionByText("Version 2.0");
        issuePage.enterTextInSecondVisualEditor("DEV");
        issuePage.enterIssueLinkAndPressEnter("Test-207008");
        issuePage.enterSprintAndPressEnter("Доска Спринт 1");
        issuePage.selectSeverityByValue("10100");

        issuePage.clickSubmitAndWaitForSuccessAndOpenIssue();

        issuePage.openBusinessProcessAndSelect("В процессе");
        issuePage.openBusinessProcessAndSelect("Исполнено");
        issuePage.openBusinessProcessAndSelect("Подтверждено");

    }


}
