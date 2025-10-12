package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.LoginPage;
import pages.NewTaskPage;
import pages.TaskListPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTaskSteps {
    private final LoginPage loginPage = new LoginPage();
    private final TaskListPage taskListPage = new TaskListPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();

    private int issueCountBefore;

    @Когда("^он создаёт новую задачу с заголовком \"([^\"]*)\"$")
    public void createNewIssue(String summary) {
        taskListPage.clickViewAllIssues();
        issueCountBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        taskListPage.clickCreateIssue();

        newTaskPage.enterSummary(summary)
                .submitIssue();

        taskListPage.navigateBackToIssues()
                .refreshIssuesList();
    }

    @Тогда("^количество задач увеличивается минимум на одну$")
    public void verifyIssueCountIncreased() {
        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        assertTrue(countAfter > issueCountBefore, "Количество задач должно увеличиться минимум на 1");
    }

    @И("^он создал новую задачу и убедился что количество задач стало минимум на одну больше$")
    public void createNewIssueAndVerifyCount() {
        String summary = "AT1";
        taskListPage.clickViewAllIssues();
        issueCountBefore = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());

        taskListPage.clickCreateIssue();

        newTaskPage.enterSummary(summary)
                .submitIssue();

        taskListPage.navigateBackToIssues()
                .refreshIssuesList();

        int countAfter = taskListPage.extractNumberFromText(taskListPage.getIssuesCountText());
        assertTrue(countAfter > issueCountBefore, "Количество задач должно увеличиться минимум на 1");
    }

}
