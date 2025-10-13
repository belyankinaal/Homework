package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.NewTaskPage;
import pages.ProjectPage;
import pages.TaskListPage;
import pages.TaskSearchPage;
import steps.*;
import util.WebHooks;

public class TestBelyankina extends WebHooks {

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final WorkFlowSteps workFlowSteps = new WorkFlowSteps();

    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final TaskMoreSteps taskMoreSteps = new TaskMoreSteps();
    private final TaskStatusSteps taskStatusSteps = new TaskStatusSteps();
    private final BugSteps bugSteps = new BugSteps();

    private void loginAndOpenTestProject() {
        authSteps.login();
        navigationSteps.openProjectByName("Test");
    }

    @Test
    @DisplayName("1. Авторизация")
    void authorizationTest() {
        authSteps.login();
    }

    @Test
    @DisplayName("2. Открытие проекта Test")
    void openProjectTest() {
        loginAndOpenTestProject();
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    void issueCountTest() {
        loginAndOpenTestProject();
        taskMoreSteps.shouldIncreaseIssueCountAfterCreating();
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        loginAndOpenTestProject();
        taskStatusSteps.createAndVerifyTask();
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        loginAndOpenTestProject();
        bugSteps.createAndCompleteBug();
    }
}
