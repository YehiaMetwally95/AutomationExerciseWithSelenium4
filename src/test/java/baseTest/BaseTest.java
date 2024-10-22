package baseTest;

import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import prepareTestData.TestNGListners;
import utils.CustomSoftAssert;

import java.io.IOException;

import static utils.BrowserFactory.*;
import static utils.Screenshot.*;
import static utils.WindowManager.*;
import static utils.ThreadDriver.*;
import static utils.PropertiesManager.*;

@Listeners(TestNGListners.class)
public class BaseTest {

    //Variables
    protected ThreadLocal<WebDriver> isolatedDriver = new ThreadLocal<>();;

    public String jsonFilePathForSessionDataUser0 = "src/test/resources/SessionData/SessionDataForUser0.json";
    public String jsonFilePathForSessionDataUser1 = "src/test/resources/SessionData/SessionDataForUser1.json";
    public String jsonFilePathForSessionDataUser2 = "src/test/resources/SessionData/SessionDataForUser2.json";
    public String jsonFilePathForSessionDataUser3 = "src/test/resources/SessionData/SessionDataForUser3.json";
    public String jsonFilePathForSessionDataUser4 = "src/test/resources/SessionData/SessionDataForUser4.json";

    //Open Browser
    @Parameters({"BrowserType"})
    @BeforeMethod
    public void setUpAndOpenBrowser(@Optional String browserType) throws IOException {
        //Open Browser
        WebDriver driver = openBrowser();

        //Generate an Isolated Driver by ThreadLocal
        isolateWebDriver(driver,isolatedDriver);

        //Perform actions on Window
        navigateToURL(getDriver(isolatedDriver),getPropertiesValue("baseUrl"));

        //Set the CustomSoftAssert Class with the driver
        CustomSoftAssert.softAssertDriver = getDriver(isolatedDriver);
    }

    @AfterMethod
    public void getScreenshots(ITestResult result) throws IOException, InterruptedException {
        //Take Screenshot after every successful test
        captureSuccess(getDriver(isolatedDriver),result);

        //Take Screenshot after every failed test
        captureFailure(getDriver(isolatedDriver), result);
    }

    @AfterMethod (dependsOnMethods = "getScreenshots")
    public void tearDownBrowser(){
        //Close Browser after every test
        closeAllWindows(getDriver(isolatedDriver));

        //Remove the Isolated Driver from Memory
        removeIsolatedDriver(isolatedDriver);
    }


}
