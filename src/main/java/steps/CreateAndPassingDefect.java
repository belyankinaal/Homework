package steps;

import io.cucumber.java.ru.*;
import pages.*;

public class CreateAndPassingDefect {

    private final TaskListPage taskListPage = new TaskListPage();
    private final ProjectPage projectPage = new ProjectPage();
    private final NewTaskPage newTaskPage = new NewTaskPage();
    private final WorkflowPage workflowPage = new WorkflowPage();

    @Когда("^пользователь создаёт дефект с заголовком \"([^\"]*)\"$")
    public void createBug(String summary) {
        taskListPage.clickCreateIssue();
        newTaskPage.enterSummary(summary);
        new VisualEditorPage(0).ensureVisualEditorSelected();
    }

    @И("^указывает описание \"([^\"]*)\"$")
    public void enterVisualDescription(String description) {
        new VisualEditorPage(0).setContent(description);
    }

    @И("^выбирает Fix Version \"([^\"]*)\"$")
    public void selectFixVersion(String version) {
        projectPage.selectFixVersionByText(version);
    }

    @И("^указывает DEV в среде тестирования$")
    public void enterSecondDescription() {
        new VisualEditorPage(1).setContent("DEV");
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
        newTaskPage.clickSubmitAndWaitForSuccessAndOpenIssue();
    }

    @Пусть("^он меняет статус задачи на \"([^\"]*)\"$")
    @И("^меняет статус задачи на \"([^\"]*)\"$")
    public void changeIssueStatus(String status) {
        workflowPage.openBusinessProcessAndSelect(status);
    }
}
