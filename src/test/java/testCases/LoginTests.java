package testCases;


import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.JDBCManager;
import utils.JsonManager;

import java.io.IOException;
import java.sql.SQLException;

@Epic("Automation Exercise Features")
@Feature("User Login")
@Story("Verify User Login Through GUI")
@Listeners(utils.TestNGListners.class)
public class LoginTests extends BaseTest {
    //Variables
    JsonManager json;
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/LoginTestData.json";

    //Test Data Preparation
    @BeforeClass
    public void prepareTestData() throws IOException, ParseException, SQLException {
        json = new JsonManager(jsonFilePath);
        String dbQuery = "SELECT Username,Email,Password FROM automationexercise.users Order by Username Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"Username","Email","Password"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Users";

        JDBCManager.setJsonFileFromDBForNestedArrayOfJsonObjects(dbQuery,jsonFilePath,jsonKeysForUsers,jsonMainKeyForUsers);
    }

    //Test Scripts
    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUserThroughGUI() throws IOException, ParseException {
        new HomePage(driver)
                .openHomePage()
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .loginWithValidUser(json.getData("Users[0].Email"),json.getData("Users[0].Password"))
                .assertUserIsLoggedIn(json.getData("Users[0].Username"));
    }

    @Description("Login With Non Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithNonExistingUserThroughGUI() throws IOException, ParseException {
        new HomePage(driver)
                .openHomePage()
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .loginWithInvalidUser(json.getData("Users[0].Email"),json.getData("Users[1].Password"))
                .assertIncorrectLoginMassage(json.getData("Messages[0].InvalidCredentials"));
    }

}
