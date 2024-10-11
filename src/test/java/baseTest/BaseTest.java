package baseTest;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import prepareTestData.LoadProductsFromDB;
import utils.CustomSoftAssert;

import java.io.IOException;
import java.sql.SQLException;

import static prepareTestData.LoadProductsFromDB.loadProductsFromDB;
import static prepareTestData.LoadUsersFromDB.loadUsersFromDB;
import static utils.BrowserFactory.*;
import static utils.Screenshot.*;
import static utils.WindowManager.*;
import static utils.ThreadDriver.*;
import static utils.PropertiesManager.*;

public class BaseTest {

    //Variables
    public WebDriver driver;
    public ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();;

    public String jsonFilePathForSessionDataUser0 = "src/test/resources/SessionData/SessionDataForUser0.json";
    public String jsonFilePathForSessionDataUser1 = "src/test/resources/SessionData/SessionDataForUser1.json";
    public String jsonFilePathForSessionDataUser2 = "src/test/resources/SessionData/SessionDataForUser2.json";
    public String jsonFilePathForSessionDataUser3 = "src/test/resources/SessionData/SessionDataForUser3.json";
    public String jsonFilePathForSessionDataUser4 = "src/test/resources/SessionData/SessionDataForUser4.json";

    //Open Browser by read Browser Type from Properties files
    @BeforeMethod
    public void setUpAndOpenBrowserFromPropertiesFile() throws IOException, ParseException {

        //Open Browser
        driver = openBrowser();

        //Generate Isolated Driver from ThreadDriver
        setIsolatedDriver(threadDriver,driver);

        //Perform actions on Window
        navigateToURL(driver,getPropertiesValue("baseUrl"));

        //Set the CustomSoftAssert Class with the driver
        CustomSoftAssert.softAssertDriver = driver;
    }

    //Open Browser by read Browser Type from TestNG XML File
    /*@BeforeMethod
    @Parameters({"BrowserType"})
    public void setUpAndOpenBrowserFromTestngFile(String browserType) throws IOException, ParseException {
        //Open Browser
        driver = openBrowser(browserType);
        //Generate Isolated Driver from ThreadDriver
        setIsolatedDriver(threadDriver,driver);

        //Perform actions on Window
        navigateToURL(driver,getPropertiesValue("baseUrl"));

        //Set the CustomSoftAssert Class with the driver
        CustomSoftAssert.softAssertDriver = driver;
    }*/

    @AfterMethod
    public void getScreenshots(ITestResult result) throws IOException, InterruptedException {
        //Take Screenshot after every successful test
        captureSuccess(getIsolatedDriver(threadDriver),result);

        //Take Screenshot after every failed test
        captureFailure(getIsolatedDriver(threadDriver), result);
    }

    @AfterMethod (dependsOnMethods = "getScreenshots")
    public void tearDownBrowser(){
        //Close Browser after every test
        closeAllWindows(getIsolatedDriver(threadDriver));

        //Remove the Isolated Driver from Memory
        removeIsolatedDriver(threadDriver);

    }


}
