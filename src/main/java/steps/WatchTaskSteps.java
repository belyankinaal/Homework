package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import pages.LoginPage;
import pages.Project;
import pages.ProjectPage;

public class WatchTaskSteps {
    private final LoginPage loginPage = new LoginPage();
    private final Project project = new Project();
    private final ProjectPage projectPage = new ProjectPage();
    private final AutorizationSteps autorizationSteps = new AutorizationSteps();

    private int issueCountBefore;

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
}
