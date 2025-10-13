package steps;

import pages.NewTaskPage;
import pages.TaskListPage;

public class IssueSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    public void createIssue(String summary) {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary(summary);
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
    }
}
