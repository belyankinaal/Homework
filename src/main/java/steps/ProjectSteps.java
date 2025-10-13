package steps;

import pages.ProjectPage;
import pages.VisualEditorPage;

public class ProjectSteps {

    private final ProjectPage projectPage = new ProjectPage();

    public void verifyTaskStatusAndFixVersion(String status, String fixVersion) {
        projectPage.verifyTaskStatus(status)
                .verifyFixVersion(fixVersion);
    }

    public void fillBugDetails(String fixVersion, String environment, String linkedIssue, String sprint, String severity) {
        new VisualEditorPage(0).ensureVisualEditorSelected().setContent("Дефект");
        projectPage.selectFixVersionByText(fixVersion);
        new VisualEditorPage(1).setContent(environment);
        projectPage.enterIssueLinkAndPressEnter(linkedIssue);
        projectPage.enterSprintAndPressEnter(sprint);
        projectPage.selectSeverityByValue(severity);
    }
}
