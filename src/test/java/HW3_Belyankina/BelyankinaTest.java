package HW3_Belyankina;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.*;

import java.util.logging.Logger;

public class BelyankinaTest extends BaseTest {

    private static final Logger logger = Logger.getLogger(BelyankinaTest.class.getName());

    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = new NavigationSteps();
    private final TaskMoreSteps taskMoreSteps = new TaskMoreSteps();
    private final TaskStatusSteps taskStatusSteps = new TaskStatusSteps();
    private final BugSteps bugSteps = new BugSteps();



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
        navigationSteps.loginAndOpenTestProject();
        logger.info("Тест открытия проекта Test завершен");
    }

    @Test
    @DisplayName("3. Проверка увеличения количества задач")
    void issueCountTest() {
        logger.info("Запуск теста проверки увеличения количества задач");
        navigationSteps.loginAndOpenTestProject();
        taskMoreSteps.shouldIncreaseIssueCountAfterCreating();
        logger.info("Тест проверки увеличения количества задач завершен");
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        logger.info("Запуск теста проверки созданной задачи и статуса");
        navigationSteps.loginAndOpenTestProject();
        taskStatusSteps.createAndVerifyTask();
        logger.info("Тест проверки созданной задачи и статуса завершен");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        logger.info("Запуск теста создания и прохождения дефекта");
        navigationSteps.loginAndOpenTestProject();
        bugSteps.createAndCompleteBug();
        logger.info("Тест создания и прохождения дефекта завершен");
    }
}
