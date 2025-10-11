package HW3_Belyankina;

import model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProjectPage;
import util.CustomProperties;
import util.WebHooks;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBelyankina extends WebHooks {

    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();

    private void loginAndOpenTestProject() {
        loginPage.login(CustomProperties.getProperty("user.name"),
                CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));

        new Project().openProjectsMenu()
                .selectTestProject();
    }

    @Test
    @DisplayName("1. Авторизация")
    void authorizationTest() {
        loginPage.login(CustomProperties.getProperty("user.name"),
                CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
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
        projectPage.clickViewAllIssues();

        int countBefore = projectPage.extractNumberFromText(projectPage.getIssuesCountText());

        projectPage.clickCreateIssue()
                .enterSummary("A1")
                .submitIssue();

        projectPage.navigateBackToIssues()
                .refreshIssuesList();

        int countAfter = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);
    }

    @Test
    @DisplayName("4. Проверка созданной задачи и статуса")
    void checkTaskTest() {
        loginAndOpenTestProject();
        projectPage.clickViewAllIssues()
                .clickCreateIssue()
                .enterSummary("A1")
                .submitIssue()
                .navigateBackToIssues()
                .refreshIssuesList();

        projectPage.searchForTask("TestSeleniumATHomework")
                .openTask()
                .verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");
    }

    @Test
    @DisplayName("5. Создание и прохождение дефекта")
    void createBugTest() {
        loginAndOpenTestProject();
        projectPage.clickViewAllIssues();

        int countBefore = projectPage.extractNumberFromText(projectPage.getIssuesCountText());

        projectPage.clickCreateIssue()
                .enterSummary("A1")
                .submitIssue()
                .navigateBackToIssues()
                .refreshIssuesList();

        int countAfter = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
        assertTrue(countAfter > countBefore,
                "Количество задач должно увеличиться минимум на 1. Было: " + countBefore + ", стало: " + countAfter);

        projectPage.searchForTask("TestSeleniumATHomework")
                .openTask()
                .verifyTaskStatus("Сделать")
                .verifyFixVersion("Version 2.0");

        projectPage.clickCreateIssue()
                .enterSummary("A1")
                .ensureVisualEditorSelected()
                .enterTextInVisualEditor("Дефект")
                .selectFixVersionByText("Version 2.0")
                .enterTextInSecondVisualEditor("DEV")
                .enterIssueLinkAndPressEnter("Test-207008")
                .enterSprintAndPressEnter("Доска Спринт 1")
                .selectSeverityByValue("10100")
                .clickSubmitAndWaitForSuccessAndOpenIssue()
                .openBusinessProcessAndSelect("В процессе")
                .openBusinessProcessAndSelect("Исполнено")
                .openBusinessProcessAndSelect("Подтверждено");
    }
}
