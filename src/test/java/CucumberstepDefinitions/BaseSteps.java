package CucumberstepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import utils.BrowserOptions;
import utils.Screenshot;
import utils.WindowManager;

import java.io.File;
import java.io.IOException;

public class BaseSteps {

    //Global or Shared Variables
    protected WebDriver driver;
    protected String url = "https://the-internet.herokuapp.com/";
    protected String Username;

    //Shared Methods

    @Before()
    public void OpenBrowser()
    {
        //Open Browser before every Scenario
        driver= new ChromeDriver(BrowserOptions.getChromeOptions());
        //Perform actions on Window Manager
        WindowManager.maximizeWindow(driver);
    }

    @After()
    public void closeBrowser(Scenario scenario) throws IOException {
        //Take Screenshot after every successful test
        Screenshot.captureSuccess(driver,scenario);

        //Take Screenshot after every failed test
        Screenshot.captureFailure(driver,scenario);

        //Close Browser after every Scenario
        WindowManager.closeCurrentWindow(driver);
    }
}
