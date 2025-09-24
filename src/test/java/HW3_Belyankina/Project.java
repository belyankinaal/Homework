package HW3_Belyankina;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

import org.openqa.selenium.By;

public class Project {

    private SelenideElement projectsButton = $("#browse_link");
    private SelenideElement testProjectLink = $("#admin_main_proj_link_lnk");
    private SelenideElement issuesLink = $(By.linkText("Задачи")); // Новый элемент для кнопки "Задачи"

    // Открыть меню проектов
    public void openProjectsMenu() {
        projectsButton.shouldBe(visible).click();
    }

    // Выбрать тестовый проект
    public void selectTestProject() {
        testProjectLink.shouldBe(visible).click();
    }

    // Перейти на страницу задач
    public void openIssuesPage() {
        issuesLink.shouldBe(visible).click();
    }
}
