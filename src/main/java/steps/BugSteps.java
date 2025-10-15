package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Работа с дефектом")
@Feature("Создание и прохождение дефекта")
public class BugSteps {

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final WorkFlowSteps workFlowSteps = new WorkFlowSteps();

    @Step("Создание и прохождение дефекта")
    @Story("Создание дефект и проведение по статусам")
    public void createAndCompleteBug() {

        verifyIssueCountIncrease();

        verifyCreatedTask();

        createAndConfigureBug();

        completeBugWorkflow();
    }

    @Step("Проверка, что после создания задачи увеличилось количество задач")
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

    @Step("Находим задачу,проверяем статус и версию")
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
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
    }

    @Step("Проверка прохождения дефекта по статусам")
    private void completeBugWorkflow() {
        workFlowSteps.completeWorkflow("В процессе", "Исполнено", "Подтверждено");
    }
}