package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.*;
import util.CustomProperties;
import util.WebHooks;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBelyankina extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final NavigationPage navigationPage = new NavigationPage();
    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final WorkFlowPage workFlowPage = new WorkFlowPage();

    private void loginAndOpenTestProject() {
        loginPage.login(CustomProperties.getProperty("user.name"),
                CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
        navigationPage.openProjectsMenu().selectTestProject();
    }

    @Test
    @DisplayName("1. Авторизация")
    void authorizationTest() {
        loginPage.login(CustomProperties.getProperty("user.name"),
                CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Test
    @DisplayName("2. Открытие проекта Test")
    void openProjectTest() {
        loginAndOpenTestProject();
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    void issueCountTest() {
        loginAndOpenTestProject();
        taskListPage.clickViewAllIssues();
        int countBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1").submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        loginAndOpenTestProject();
        taskListPage.clickViewAllIssues()
                .clickCreateIssue();
        newTaskPage.enterSummary("A1").submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
        taskSearchPage.clickViewAllTasks()
                .searchForTask("TestSeleniumATHomework")
                .openTask();
        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        loginAndOpenTestProject();
        taskListPage.clickViewAllIssues();
        int countBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1").submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
        taskSearchPage.clickViewAllTasks()
                .searchForTask("TestSeleniumATHomework")
                .openTask();
        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1");
        new VisualEditorPage(0).ensureVisualEditorSelected().setContent("Дефект");
        projectPage.selectFixVersionByText("Version 2.0");
        new VisualEditorPage(1).setContent("DEV");
        projectPage.enterIssueLinkAndPressEnter("Test-207008");
        projectPage.enterSprintAndPressEnter("Доска Спринт 1");
        projectPage.selectSeverityByValue("10100");
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
        workFlowPage.openBusinessProcessAndSelect("В процессе")
                .openBusinessProcessAndSelect("Исполнено")
                .openBusinessProcessAndSelect("Подтверждено");
    }
}
