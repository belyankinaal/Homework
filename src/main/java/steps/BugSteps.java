package steps;

import pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BugSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final WorkFlowSteps workFlowSteps = new WorkFlowSteps();

    public void createAndCompleteBug() {

        verifyIssueCountIncrease();

        verifyCreatedTask();

        createAndConfigureBug();

        completeBugWorkflow();
    }

    private void verifyIssueCountIncrease() {
        taskListPage.clickViewAllIssues();
        int countBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1").submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    private void verifyCreatedTask() {
        taskSearchPage.clickViewAllTasks()
                .searchForTask("TestSeleniumATHomework")
                .openTask();
        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
    }

    private void createAndConfigureBug() {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1");
        new VisualEditorPage(0).ensureVisualEditorSelected().setContent("Дефект");
        projectPage.selectFixVersionByText("Version 2.0");
        new VisualEditorPage(1).setContent("DEV");
        projectPage.enterIssueLinkAndPressEnter("Test-207008");
        projectPage.enterSprintAndPressEnter("Доска Спринт 1");
        projectPage.selectSeverityByValue("10100");
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
    }

    private void completeBugWorkflow() {
        workFlowSteps.completeWorkflow("В процессе", "Исполнено", "Подтверждено");
    }
}