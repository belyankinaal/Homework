package steps;

import pages.WorkFlowPage;

public class WorkFlowSteps {

    private final WorkFlowPage workFlowPage = new WorkFlowPage();

    public void completeWorkflow(String... statuses) {
        for (String status : statuses) {
            workFlowPage.openBusinessProcessAndSelect(status);
        }
    }
}
