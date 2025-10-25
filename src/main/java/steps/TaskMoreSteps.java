package steps;

import pages.NewTaskPage;
import pages.TaskListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskMoreSteps {

    private final TaskListPage taskListPage;
    private final NewTaskPage newTaskPage;

    public TaskMoreSteps(TaskListPage taskListPage, NewTaskPage newTaskPage) {
        this.taskListPage = taskListPage;
        this.newTaskPage = newTaskPage;
    }

    public void shouldIncreaseIssueCountAfterCreating() {
        taskListPage.clickViewAllIssues();
        int countBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1").submitNewTask();

        taskListPage.navigateBackToIssues().refreshIssuesList();
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }
}
