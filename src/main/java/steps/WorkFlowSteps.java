package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import pages.WorkFlowPage;

@Epic("Рабочие процессы")
@Feature("Завершение работы")
public class WorkFlowSteps {

    private final WorkFlowPage workFlowPage = new WorkFlowPage();

    @Step("Проверка статусов")
    public void completeWorkflow(String... statuses) {
        for (String status : statuses) {
            workFlowPage.openBusinessProcessAndSelect(status);
        }
    }
}
