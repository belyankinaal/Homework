package HW3_Belyankina;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.NewTaskPage;
import pages.ProjectPage;
import pages.VisualEditorPage;
import steps.AuthSteps;
import steps.IssueSteps;
import steps.NavigationSteps;
import steps.WorkFlowSteps;
import util.CustomProperties;
import util.WebHooks;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBelyankina extends WebHooks {
    @BeforeAll
    static void beforeAll() {
        CustomProperties.loadProperties();
    }

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final WorkFlowSteps workFlowSteps = new WorkFlowSteps();
    private final IssueSteps issueSteps = new IssueSteps();

    private final ProjectPage projectPage = new ProjectPage();

    private void loginAndOpenTestProject() {
        authSteps.login();
        navigationSteps.openProjectByName("Test");
    }

    @Test
    @DisplayName("1. Авторизация")
    void authorizationTest() {
        authSteps.login();
    }

    @Test
    @DisplayName("2. Открытие проекта Test")
    void openProjectTest() {
        loginAndOpenTestProject();
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    void issueCountTest() {
        String issueSummary = CustomProperties.getProperty("issue.summary");

        loginAndOpenTestProject();
        int countBefore = issueSteps.getIssueCount();
        issueSteps.createIssue(issueSummary);
        int countAfter = issueSteps.getIssueCount();

        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        String issueSummary = CustomProperties.getProperty("issue.summary");

        loginAndOpenTestProject();
        issueSteps.createIssue(issueSummary);
        issueSteps.searchAndOpenTask("TestSeleniumATHomework");

        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        String issueSummary = CustomProperties.getProperty("issue.summary");

        loginAndOpenTestProject();
        int countBefore = issueSteps.getIssueCount();
        issueSteps.createIssue(issueSummary);
        int countAfter = issueSteps.getIssueCount();

        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);

        issueSteps.searchAndOpenTask("TestSeleniumATHomework");
        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");

        issueSteps.createIssue(issueSummary);

        new VisualEditorPage(0).ensureVisualEditorSelected().setContent("Дефект");
        projectPage.selectFixVersionByText("Version 2.0");
        new VisualEditorPage(1).setContent("DEV");
        projectPage.enterIssueLinkAndPressEnter("Test-207008");
        projectPage.enterSprintAndPressEnter("Доска Спринт 1");
        projectPage.selectSeverityByValue("10100");
        new NewTaskPage().clickSubmitAndWaitForSuccessAndOpenIssue();

        workFlowSteps.completeWorkflow("В процессе", "Исполнено", "Подтверждено");
    }
}
