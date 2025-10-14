package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class NavigationPage {

    private final SelenideElement projectsButton = $x("//*[@id='browse_link']").as("Кнопка меню проектов");
    private final SelenideElement testProjectLink = $x("//*[@id='admin_main_proj_link_lnk']").as("Ссылка на проект");

    public NavigationPage openProjectsMenu() {
        projectsButton.shouldBe(visible).click();
        return this;
    }

    public NavigationPage selectTestProject() {
        testProjectLink.shouldBe(visible).click();
        return this;
    }
}
