package baseTest;

import engine.elementActions.WaitsManager;
import engine.elementActions.WebElementsActionBot;
import engine.listeners.TestNGListners;
import org.checkerframework.framework.qual.RelevantJavaTypes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static engine.loggers.LogHelper.*;

@Listeners(TestNGListners.class)
public class TestClass {

    @Test
    public void test() {

        By continueButton = By.xpath("//a[contains(@data-qa,'continue')]");
        By yehia = RelativeLocator.with(By.tagName("a")).below(continueButton);
        System.out.println(yehia);

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
