package baseTest;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import engine.listeners.TestNGListners;
import prepareTestData.LoadProductsFromDB;
import prepareTestData.LoadUsersFromDB;

import java.io.IOException;
import java.sql.SQLException;

import static engine.driverManager.BrowserFactory.*;
import static engine.browserActions.WindowManager.*;
import static engine.managers.PropertiesManager.*;

@Listeners(TestNGListners.class)
public class BaseTest {

    //Variables
    protected ThreadLocal<RemoteWebDriver> isolatedDriver;;

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

    //Open Browser
    @Parameters({"BrowserType"})
    @BeforeMethod
    public void setUpAndOpenBrowser(@Optional String browserType) throws IOException {
        //Open Browser
        isolatedDriver = openBrowser();

        //Perform actions on Window
        navigateToURL(getDriver(isolatedDriver),getPropertiesValue("baseUrlWeb"));
    }

    @AfterMethod
    public void tearDownBrowser(){
        //Close Browser after every test
        closeAllWindows(getDriver(isolatedDriver));

        //Remove the Isolated Driver from Memory
        removeIsolatedDriver(isolatedDriver);
    }
}
