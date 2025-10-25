package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.*;

@Epic("Работа с дефектом")
@Feature("Создание и прохождение дефекта")
public class BugSteps {

    private final TaskListPage taskListPage;
    private final NewTaskPage newTaskPage;
    private final TaskMoreSteps taskMoreSteps;
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final WorkFlowSteps workFlowSteps = new WorkFlowSteps();

    public BugSteps(TaskListPage taskListPage, NewTaskPage newTaskPage) {
        this.taskListPage = taskListPage;
        this.newTaskPage = newTaskPage;
        this.taskMoreSteps = new TaskMoreSteps(taskListPage, newTaskPage);
    }

    @Step("Создание и прохождение дефекта")
    @Story("Создание дефекта и проведение по статусам")
    public void createAndCompleteBug() {
        taskMoreSteps.shouldIncreaseIssueCountAfterCreating();
        verifyCreatedTask();
        createAndConfigureBug();
        completeBugWorkflow();
    }

    @Step("Находим задачу, проверяем статус и версию")
    private void verifyCreatedTask() {
        taskSearchPage.clickViewAllTasks()
                .searchForTask("TestSeleniumATHomework")
                .openTask();
        projectPage.verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
    }

    @Step("Заведение дефекта")
    private void createAndConfigureBug() {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary("A1");
        new VisualEditorPage(0).ensureVisualEditorSelected().setContent("Дефект");
        projectPage.selectFixVersionByText("Version 2.0");
        new VisualEditorPage(1).setContent("DEV");
        projectPage.enterIssueLinkAndPressEnter("Test-207008");
        projectPage.enterSprintAndPressEnter("Доска Спринт 1");
        projectPage.selectSeverityByValue("10100");
        newTaskPage.submitAndOpenNewTask();
    }

    @Step("Проверка прохождения дефекта по статусам")
    private void completeBugWorkflow() {
        workFlowSteps.completeWorkflow("В процессе", "Исполнено", "Подтверждено");
    }
}
