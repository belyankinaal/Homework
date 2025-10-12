package steps;

import io.cucumber.java.ru.*;
import pages.ProjectPage;


public class CreateAndPassingDefect {

    private final ProjectPage projectPage = new ProjectPage();

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