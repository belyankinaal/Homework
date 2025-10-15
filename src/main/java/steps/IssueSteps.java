package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.NewTaskPage;
import pages.TaskListPage;

@Epic("Работа с задачей")
public class IssueSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    @Step("Создание задачи")
    @Story("Пользователь создает задачу")
    public void createIssue(String summary) {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary(summary);
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
        taskListPage.navigateBackToIssues().refreshIssuesList();
    }
}
