package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.NewTaskPage;
import pages.ProjectPage;
import pages.TaskListPage;
import pages.TaskSearchPage;

@Epic("Работа с задачей")
@Feature("Проверка статусов задачи")
public class TaskStatusSteps {

    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    @Step("Создание и проверка задачи")
    @Story("Пользователь создает задачу и проверяет")
    public void createAndVerifyTask() {

        taskListPage.clickViewAllIssues()
                .clickCreateIssue();
        newTaskPage.enterSummary(getTaskSummaryForCreation()).submitNewTask();
        taskListPage.navigateBackToIssues().refreshIssuesList();

        verifyCreatedTaskStatus();
    }

    @Step("Проверка статуса и версии задачи")
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