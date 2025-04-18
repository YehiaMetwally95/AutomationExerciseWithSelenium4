package baseTest;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import prepareTestData.LoadProductsFromDB;
import prepareTestData.LoadUsersFromDB;

import java.io.IOException;
import java.sql.SQLException;

import static yehiaEngine.driverManager.BrowserFactory.*;
import static yehiaEngine.browserActions.WindowManager.*;
import static yehiaEngine.managers.PropertiesManager.*;

public class BaseTest {

    //Variables
    protected ThreadLocal<RemoteWebDriver> isolatedDriver;;

    public String jsonFilePathForSessionDataUser0 = "src/test/resources/SessionData/SessionDataForUser0.json";
    public String jsonFilePathForSessionDataUser1 = "src/test/resources/SessionData/SessionDataForUser1.json";
    public String jsonFilePathForSessionDataUser2 = "src/test/resources/SessionData/SessionDataForUser2.json";
    public String jsonFilePathForSessionDataUser3 = "src/test/resources/SessionData/SessionDataForUser3.json";
    public String jsonFilePathForSessionDataUser4 = "src/test/resources/SessionData/SessionDataForUser4.json";

    // Sync with Database to Load Latest Products and Users and Update Test Data Json Files
    @BeforeTest
    public void prepareTestData() throws SQLException, IOException {
        if (System.getProperty("syncWithDB").equalsIgnoreCase("true")) {
            LoadProductsFromDB.loadProductsFromDB();
            LoadUsersFromDB.loadUsersFromDB();
        }
    }

    @BeforeMethod
    //Open Browser
    public void setUpAndOpenBrowser() throws IOException {
        //Open Browser
        isolatedDriver = openBrowser();

        //Perform actions on Window
        navigateToURL(getDriver(isolatedDriver),getPropertiesValue("baseUrlWeb"));
    }

    @AfterMethod
    public void closeBrowser(){
        //Close Browser after every test
        closeCurrentWindow(getDriver(isolatedDriver));

        //Remove the Isolated Driver from Memory
        removeIsolatedDriver(isolatedDriver);
    }
}
