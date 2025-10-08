package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.*;
import org.openqa.selenium.chrome.ChromeOptions;
import util.CustomProperties;

public class Hooks {

    @BeforeAll
    public static void beforeAllScenarios() {
        System.out.println("beforeAllScenarios - Runs once before all scenarios");
    }

    @AfterAll
    public static void afterAllScenarios() {
        System.out.println("afterAllScenarios - Runs once after all scenarios");
    }

    @Before(order = 1)
    public void initBrowser() {
        System.out.println("Initializing browser");
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = options;
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 30000;

        Selenide.open(CustomProperties.getProperty("web.url"));
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @After(order = 1)
    public void closeBrowser() {
        System.out.println("Closing browser");
        Selenide.closeWebDriver();
    }

    @Before("@Autorization")
    public void beforeAutorization(Scenario scenario) {
        System.out.println("Runs before Autorization test");
        scenario.log("Authorization test is about to start");
    }

    @Before("@CreateAndPassingDefect")
    public void beforeCreateAndPassingDefect(Scenario scenario) {
        System.out.println("Runs before Create and Passing Defect test");
        scenario.log("Create and Passing Defect test is about to start");
    }

    @Before("@NewTask")
    public void beforeNewTask(Scenario scenario) {
        System.out.println("Runs before New Task test");
        scenario.log("New Task test is about to start");
    }

    @Before("@OpenTest")
    public void beforeOpenTest(Scenario scenario) {
        System.out.println("Runs before Open Test test");
        scenario.log("Open Test test is about to start");
    }

    @Before("@WatchTask")
    public void beforeWatchTask(Scenario scenario) {
        System.out.println("Runs before Watch Task test");
        scenario.log("Watch Task test is about to start");
    }

    @After("@Autorization")
    public void afterAutorization() {
        System.out.println("After Autorization test");
        Selenide.closeWebDriver();
    }

    @After("@CreateAndPassingDefect")
    public void afterCreateAndPassingDefect() {
        System.out.println("After Create and Passing Defect test");
        Selenide.closeWebDriver();
    }

    @After("@NewTask")
    public void afterNewTask() {
        System.out.println("After New Task test");
        Selenide.closeWebDriver();
    }

    @After("@OpenTest")
    public void afterOpenTest() {
        System.out.println("After Open Test test");
        Selenide.closeWebDriver();
    }

    @After("@WatchTask")
    public void afterWatchTask() {
        System.out.println("After Watch Task test");
        Selenide.closeWebDriver();
    }
}
