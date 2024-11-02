package baseTest;

import yehiaEngine.elementActions.WebElementsActionBot;
import yehiaEngine.listeners.ParallelExecutionListener;
import yehiaEngine.listeners.MethodListeners;
import yehiaEngine.listeners.SuiteListeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import java.io.IOException;


@Listeners({ParallelExecutionListener.class, MethodListeners.class, SuiteListeners.class})
public class TestClass {

    @Test
    public void test() {

        By continueButton = By.xpath("//a[contains(@data-qa,'continue')]");
        By yehia = RelativeLocator.with(By.tagName("a")).below(continueButton);
        System.out.println(yehia);
        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();
        context.getCurrentXmlTest().getSuite().setParallel(XmlSuite.ParallelMode.METHODS);
       /* logInfo("Starting my test");
        logWarning("This is warning");

        WebDriver driver = new ChromeDriver();
        logInfo("Navigating to Google Page");

        try {
            driver.navigate().to("https://www.google.com");
        }catch (Exception e)
        {
            logError("Failed to Navigate to Google Page",e);
        }
        driver.getTitle();
        logInfo("Page Title Retrieved");

        driver.quit();
        logInfo("Browser is Closed");*/
    }

    @Test
    public void test2() throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://automationexercise.com/login");
        By signUpNameText = By.xpath("//input[@data-qa='signup-name']");
        By signUpEmailText = By.xpath("//input[@data-qa='signup-email']");
        By signUpButton = By.xpath("//button[@data-qa='signup-button']");
        By disabledEmail = By.id("email");
        WebElementsActionBot bot = new WebElementsActionBot(driver);
        bot
                .type(signUpNameText,"Yehia")
                .type(signUpEmailText,"yyYehiaYehiay@email.com")
                .press(signUpButton)
                .type(disabledEmail,"aaa@aaa.com");
    }
}
