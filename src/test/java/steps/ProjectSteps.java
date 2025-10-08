package steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import model.Project;
import pages.LoginPage;
import pages.ProjectPage;
import util.CustomProperties;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectSteps {

    private LoginPage loginPage;
    private Project project = new Project();
    private ProjectPage projectPage;

    private int issueCountBefore;

    @Дано("пользователь находится на странице логина")
    public void openLoginPage() {
        loginPage = new LoginPage();
        projectPage = new ProjectPage();
    }

    @Когда("он вводит логин {string} и пароль {string}")
    public void login(String username, String password) {
        loginPage.login(username, password);
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Тогда("он видит домашнюю страницу")
    public void checkHomePage() {
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Дано("пользователь вошёл в систему")
    public void userLoggedIn() {
        loginPage = new LoginPage();
        loginPage.login(CustomProperties.getProperty("user.name"), CustomProperties.getProperty("user.password"));
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Когда("он открывает меню проектов и выбирает проект Test")
    public void openTestProject() {
        project.openProjectsMenu()
                .selectTestProject();
    }

    @Тогда("проект Test открыт")
    public void verifyProjectOpened() {
        webdriver().shouldHave(urlContaining("/projects/TEST"));
    }

    @Дано("пользователь открыл проект Test")
    public void userOpenedTestProject() {
        userLoggedIn();
        openTestProject();
    }

    @Когда("он создаёт новую задачу с заголовком {string}")
    public void createNewIssue(String summary) {
        projectPage = new ProjectPage();
        projectPage.clickViewAllIssues();
        String countText = projectPage.getIssuesCountText();
        issueCountBefore = projectPage.extractNumberFromText(countText);

        projectPage.clickCreateIssue()
                .enterSummary(summary)
                .submitIssue()
                .navigateBackToIssues()
                .refreshIssuesList();
    }

    @Тогда("количество задач увеличивается на 1")
    public void verifyIssueCountIncreased() {
        String countTextAfter = projectPage.getIssuesCountText();
        int countAfter = projectPage.extractNumberFromText(countTextAfter);
        assertTrue(countAfter > issueCountBefore, "Количество задач должно увеличиться минимум на 1");
    }
}
