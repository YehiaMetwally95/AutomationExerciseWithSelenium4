package baseTest;

import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import engine.listeners.TestNGListners;
import engine.loggers.CustomSoftAssert;
import prepareTestData.LoadProductsFromDB;
import prepareTestData.LoadUsersFromDB;

import java.io.IOException;
import java.sql.SQLException;

import static engine.driverManager.BrowserFactory.*;
import static engine.loggers.Screenshot.*;
import static engine.browserActions.WindowManager.*;
import static engine.managers.PropertiesManager.*;

@Listeners(TestNGListners.class)
public class BaseTest {

    //Variables
    protected ThreadLocal<WebDriver> isolatedDriver = new ThreadLocal<>();;

    public String jsonFilePathForSessionDataUser0 = "src/test/resources/SessionData/SessionDataForUser0.json";
    public String jsonFilePathForSessionDataUser1 = "src/test/resources/SessionData/SessionDataForUser1.json";
    public String jsonFilePathForSessionDataUser2 = "src/test/resources/SessionData/SessionDataForUser2.json";
    public String jsonFilePathForSessionDataUser3 = "src/test/resources/SessionData/SessionDataForUser3.json";
    public String jsonFilePathForSessionDataUser4 = "src/test/resources/SessionData/SessionDataForUser4.json";

    // Sync with Database to Load Latest Products and Users and Update Test Data Json Files
    @BeforeSuite
    public void prepareTestData() throws SQLException, IOException {
        if (System.getProperty("syncWithDB").equalsIgnoreCase("true")) {
            LoadProductsFromDB.loadProductsFromDB();
            LoadUsersFromDB.loadUsersFromDB();
        }
    }

    @AfterSuite
    public void logAssertionErrors() throws SQLException, IOException {
        //Log All Soft Assertion Errors after Every Run with screenshot
        CustomSoftAssert.reportSoftAssertionErrors();
    }

    //Open Browser
    @Parameters({"BrowserType"})
    @BeforeMethod
    public void setUpAndOpenBrowser(@Optional String browserType) throws IOException {
        //Open Browser
        WebDriver driver = openBrowser();

        //Generate an Isolated Driver by ThreadLocal
        isolateWebDriver(driver,isolatedDriver);

        //Perform actions on Window
        navigateToURL(getDriver(isolatedDriver),getPropertiesValue("baseUrlWeb"));

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
