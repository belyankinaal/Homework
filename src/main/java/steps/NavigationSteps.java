package steps;

import pages.NavigationPage;

public class NavigationSteps {

    private final NavigationPage navigationPage = new NavigationPage();

    public void openProjectByName(String projectName) {
        navigationPage.openProjectsMenu();
        navigationPage.selectTestProject();
    }
}
