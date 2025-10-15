package steps;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import pages.NavigationPage;

@Epic("Навигация")
public class NavigationSteps {

    private final NavigationPage navigationPage = new NavigationPage();
    private final AuthSteps authSteps = new AuthSteps();
    private final NavigationSteps navigationSteps = this;

    @Step("Открываем проект {projectName}")
    @Story("Пользователь открывает проект")
    public void openProjectByName(String projectName) {
        navigationPage.openProjectsMenu();
        navigationPage.selectTestProject();
    }

    public void loginAndOpenTestProject() {
        authSteps.login();
        navigationSteps.openProjectByName("Test");
    }
}
