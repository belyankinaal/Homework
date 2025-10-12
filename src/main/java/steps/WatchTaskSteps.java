package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import pages.LoginPage;
import pages.ProjectPage;
import pages.TaskSearchPage;

public class WatchTaskSteps {
    private final LoginPage loginPage = new LoginPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final TaskSearchPage taskSearchPage = new TaskSearchPage();
    private final AutorizationSteps autorizationSteps = new AutorizationSteps();

    private int issueCountBefore;

    @Когда("^он вводит в поиск задачу TestSeleniumATHomework$")
    public void searchForTask() {
        taskSearchPage.searchForTask("TestSeleniumATHomework");
    }

    @Когда("^открывает задачу$")
    public void openTask() {
        taskSearchPage.openTask();
    }

    @И("^видит статус задачи \"([^\"]*)\"$")
    public void verifyTaskStatus(String status) {
        projectPage.verifyTaskStatus(status);
    }

    @И("^Fix Version \"([^\"]*)\"$")
    public void verifyFixVersion(String version) {
        projectPage.verifyFixVersion(version);
    }
}
