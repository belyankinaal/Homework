package steps;

import pages.NewTaskPage;
import pages.TaskListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskMoreSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    public void shouldIncreaseIssueCountAfterCreating() {
        taskListPage.clickViewAllIssues();
        int countBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1").submitIssue();

        taskListPage.navigateBackToIssues().refreshIssuesList();
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    public void createTaskWithSummary(String summary) {
        taskListPage.clickViewAllIssues();
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary(summary).submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
    }
}