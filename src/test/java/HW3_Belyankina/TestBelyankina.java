package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.*;
import util.WebHooks;

public class TestBelyankina extends WebHooks {

    private static final Logger logger = LoggerFactory.getLogger(TestBelyankina.class);

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final TaskMoreSteps taskMoreSteps = new TaskMoreSteps();
    private final TaskStatusSteps taskStatusSteps = new TaskStatusSteps();
    private final BugSteps bugSteps = new BugSteps();

    private void loginAndOpenTestProject() {
        logger.info("Выполнение логина и открытие проекта Test");
        authSteps.login();
        navigationSteps.openProjectByName("Test");
    }

    @Test
    @DisplayName("1. Авторизация")
    void authorizationTest() {
        logger.info("Запуск теста авторизации");
        authSteps.login();
        logger.info("Тест авторизации завершен");
    }

    @Test
    @DisplayName("2. Открытие проекта Test")
    void openProjectTest() {
        logger.info("Запуск теста открытия проекта Test");
        loginAndOpenTestProject();
        logger.info("Тест открытия проекта Test завершен");
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    void issueCountTest() {
        logger.info("Запуск теста проверки увеличения количества задач");
        loginAndOpenTestProject();
        taskMoreSteps.shouldIncreaseIssueCountAfterCreating();
        logger.info("Тест проверки увеличения количества задач завершен");
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        logger.info("Запуск теста проверки созданной задачи и статуса");
        loginAndOpenTestProject();
        taskStatusSteps.createAndVerifyTask();
        logger.info("Тест проверки созданной задачи и статуса завершен");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        logger.info("Запуск теста создания и прохождения дефекта");
        loginAndOpenTestProject();
        bugSteps.createAndCompleteBug();
        logger.info("Тест создания и прохождения дефекта завершен");
    }
}