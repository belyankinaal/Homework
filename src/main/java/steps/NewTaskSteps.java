package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.LoginPage;
import pages.Project;
import pages.ProjectPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTaskSteps {
    private final LoginPage loginPage = new LoginPage();
    private final Project project = new Project();
    private final ProjectPage projectPage = new ProjectPage();
    private final AutorizationSteps autorizationSteps = new AutorizationSteps();

    private int issueCountBefore;

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

}
