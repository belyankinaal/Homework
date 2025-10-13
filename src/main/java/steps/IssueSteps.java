package steps;

import pages.NewTaskPage;
import pages.TaskListPage;
import pages.TaskSearchPage;

public class IssueSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();

    public int getIssueCount() {
        taskListPage.clickViewAllIssues();
        return taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
    }

    public void createIssue(String summary) {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary(summary);
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
    }


    public void searchAndOpenTask(String issueKey) {
        taskSearchPage.clickViewAllTasks()
                .searchForTask(issueKey)
                .openTask();
    }
}
