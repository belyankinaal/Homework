package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.NewTaskPage;
import pages.TaskListPage;
import steps.*;

public class BelyankinaTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BelyankinaTest.class);

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final TaskMoreSteps taskMoreSteps = new TaskMoreSteps(taskListPage, newTaskPage);
    private final TaskStatusSteps taskStatusSteps = new TaskStatusSteps();
    private final BugSteps bugSteps = new BugSteps(taskListPage, newTaskPage);

    @Test
    @DisplayName("1. Авторизация")
    @Tag("Test_1")
    void authorizationTest() {
        logger.info("Запуск теста авторизации");
        authSteps.login();
        logger.info("Тест авторизации завершен");
    }

    @Test
    @DisplayName("2. Открытие проекта Test")
    @Tag("Test_2")
    void openProjectTest() {
        logger.info("Запуск теста открытия проекта Test");
        navigationSteps.loginAndOpenTestProject();
        logger.info("Тест открытия проекта Test завершен");
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    @Tag("Test_3")
    void issueCountTest() {
        logger.info("Запуск теста проверки увеличения количества задач");
        navigationSteps.loginAndOpenTestProject();
        taskMoreSteps.shouldIncreaseIssueCountAfterCreating();
        logger.info("Тест проверки увеличения количества задач завершен");
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    @Tag("Test_4")
    void checkTaskTest() {
        logger.info("Запуск теста проверки созданной задачи и статуса");
        navigationSteps.loginAndOpenTestProject();
        taskStatusSteps.createAndVerifyTask();
        logger.info("Тест проверки созданной задачи и статуса завершен");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    @Tag("Test_5")
    void createBugTest() {
        logger.info("Запуск теста создания и прохождения дефекта");
        navigationSteps.loginAndOpenTestProject();
        bugSteps.createAndCompleteBug();
        logger.info("Тест создания и прохождения дефекта завершен");
    }
}
