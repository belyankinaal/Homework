package steps;

import io.cucumber.java.ru.*;
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

    @И("^он создал новую задачу и убедился что количество задач стало минимум на одну больше$")
    public void createNewIssueAndVerifyCount() {
        String summary = "AT1";
        projectPage.clickViewAllIssues();
        issueCountBefore = projectPage.extractNumberFromText(projectPage.getIssuesCountText());

        projectPage.clickCreateIssue()
                .enterSummary(summary)
                .submitIssue()
                .navigateBackToIssues()
                .refreshIssuesList();

        int countAfter = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
        assertTrue(countAfter > issueCountBefore, "Количество задач должно увеличиться минимум на 1");
    }

    @Когда("^он вводит в поиск задачу TestSeleniumATHomework$")
    public void searchForTask() {
        projectPage.searchForTask("TestSeleniumATHomework");
    }

    @Когда("^открывает задачу$")
    public void openTask() {
        projectPage.openTask();
    }

    @И("^видит статус задачи \"([^\"]*)\"$")
    public void verifyTaskStatus(String status) {
        projectPage.verifyTaskStatus(status);
    }

    @И("^Fix Version \"([^\"]*)\"$")
    public void verifyFixVersion(String version) {
        projectPage.verifyFixVersion(version);
    }

    @И("^он переходит на список всех задач$")
    public void viewAllIssues() {
        projectPage.clickViewAllIssues();
    }

    @И("^запоминает количество задач до создания$")
    public void rememberIssueCountBefore() {
        issueCountBefore = projectPage.extractNumberFromText(projectPage.getIssuesCountText());
    }

    @Когда("^пользователь создаёт дефект с заголовком \"([^\"]*)\"$")
    public void createBug(String summary) {
        projectPage.clickCreateIssue()
                .enterSummary(summary)
                .ensureVisualEditorSelected();
    }

    @И("^указывает описание \"([^\"]*)\"$")
    public void enterVisualDescription(String description) {
        projectPage.enterTextInVisualEditor(description);
    }

    @И("^выбирает Fix Version \"([^\"]*)\"$")
    public void selectFixVersion(String version) {
        projectPage.selectFixVersionByText(version);
    }

    @И("^указывает DEV в среде тестирования$")
    public void enterSecondDescription() {
        projectPage.enterTextInSecondVisualEditor("DEV");
    }

    @И("^указывает связь с задачей \"([^\"]*)\"$")
    public void enterIssueLink(String taskKey) {
        projectPage.enterIssueLinkAndPressEnter(taskKey);
    }

    @Также("^добавляет задачу в спринт \"([^\"]*)\"$")
    public void enterSprint(String sprintName) {
        projectPage.enterSprintAndPressEnter(sprintName);
    }

    @И("^выбирает серьезность дефекта с кодом \"([^\"]*)\"$")
    public void selectSeverity(String value) {
        projectPage.selectSeverityByValue(value);
    }

    @Затем("^сохраняет дефект и открывает его$")
    public void submitAndOpenIssue() {
        projectPage.clickSubmitAndWaitForSuccessAndOpenIssue();
    }

    @Пусть("^он меняет статус задачи на \"([^\"]*)\"$")
    @И("^меняет статус задачи на \"([^\"]*)\"$")
    public void changeIssueStatus(String status) {
        projectPage.openBusinessProcessAndSelect(status);
    }

}