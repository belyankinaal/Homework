package steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
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

    private final LoginPage loginPage = new LoginPage();
    private final Project project = new Project();
    private final ProjectPage projectPage = new ProjectPage();

    private int issueCountBefore;

    @Когда("^открыта страница сайта$")
    public void openLoginPage() {
    }

    @И("^пользователь вводит логин (.*) и пароль (.*)$")
    public void login(String username, String password) {
        loginPage.enterUsername(username)
                .enterPassword(password)
                .clickLogin();
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Тогда("^он видит домашнюю страницу$")
    public void checkHomePage() {
        webdriver().shouldHave(urlContaining("/secure/Dashboard.jspa"));
    }

    @Когда("^пользователь авторизован в системе$")
    public void userLoggedIn() {
        String username = CustomProperties.getProperty("user.name");
        String password = CustomProperties.getProperty("user.password");
        login(username, password);
    }

    @Когда("^он нажимает на выпадающий список проектов и выбирает проект Test$")
    public void openTestProject() {
        project.openProjectsMenu()
                .selectTestProject();
    }

    @Тогда("^страница проекта Test открыта$")
    public void verifyProjectOpened() {
        webdriver().shouldHave(urlContaining("/projects/TEST"));
    }

    @Дано("^пользователь авторизован и находится в проекте Test$")
    public void userOpenedTestProject() {
        userLoggedIn();
        openTestProject();
    }

    @Когда("^он создаёт новую задачу с заголовком \"([^\"]*)\"$")
    public void createNewIssue(String summary) {
        projectPage.clickViewAllIssues();
        issueCountBefore = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
        projectPage.clickCreateIssue()
                .enterSummary(summary)
                .submitIssue()
                .navigateBackToIssues()
                .refreshIssuesList();
    }

    @Тогда("^количество задач увеличивается минимум на одну$")
    public void verifyIssueCountIncreased() {
        int countAfter = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
        assertTrue(countAfter > issueCountBefore, "Количество задач должно увеличиться минимум на 1");
    }
}