package steps;

import pages.NewTaskPage;
import pages.ProjectPage;
import pages.TaskListPage;
import pages.TaskSearchPage;

public class TaskStatusSteps {

    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    public void createAndVerifyTask() {

        taskListPage.clickViewAllIssues()
                .clickCreateIssue();
        newTaskPage.enterSummary(getTaskSummaryForCreation()).submitIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();

        verifyCreatedTaskStatus();
    }

    public void verifyCreatedTaskStatus() {
        String taskSummary = "TestSeleniumATHomework";
        String expectedStatus = "Сделать";
        String expectedFixVersion = "Version 2.0";

        taskSearchPage.clickViewAllTasks()
                .searchForTask(taskSummary)
                .openTask();

        projectPage.verifyTaskStatus(expectedStatus)
                .verifyFixVersion(expectedFixVersion);
    }

    public String getTaskSummaryForCreation() {
        return "A1";
    }
}