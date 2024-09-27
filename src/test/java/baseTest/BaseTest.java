package baseTest;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.CustomSoftAssert;
import utils.SessionManager;

import java.io.File;
import java.io.IOException;

import static utils.BrowserFactory.openBrowser;
import static utils.CookiesManager.*;
import static utils.Screenshot.*;
import static utils.WindowManager.*;
import static utils.ThreadDriver.*;
import static utils.DeleteDirectoryFiles.*;
import static utils.PropertiesManager.*;

public class BaseTest {

    //Variables
    public WebDriver driver;
    public String jsonFilePathForSessionData = "src/main/resources/SessionData.json";

    //Annotations
    @BeforeMethod
    //@Parameters({"BrowserType"})
    public void setUpAndOpenBrowser() throws IOException, ParseException {
        //Open Browser
        driver = openBrowser();

        //Perform actions on Window
        navigateToURL(driver,getPropertiesValue("baseUrl"));

        //Set the CustomSoftAssert Class with the driver
        CustomSoftAssert.softAssertDriver = driver;
    }

    @AfterMethod
    public void getScreenshots(ITestResult result) throws IOException, InterruptedException {
        //Take Screenshot after every successful test
        captureSuccess(driver,result);

        //Take Screenshot after every failed test
        captureFailure(driver, result);
    }

    @AfterMethod (dependsOnMethods = "getScreenshots")
    public void tearDownBrowser(){
        //Close Browser after every test
        closeCurrentWindow(driver);
    }
}
