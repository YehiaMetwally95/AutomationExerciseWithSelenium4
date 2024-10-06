package testCases.LoginTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import prepareTestData.TestNGListners;
import utils.JsonManager;
import utils.SessionManager;

import java.io.IOException;

import static utils.ThreadDriver.getIsolatedDriver;

@Epic("Automation Exercise Features")
@Feature("User Login")
@Story("Verify User Login Through GUI")
@Listeners(TestNGListners.class)
public class LoginWithExistingUserOnGUI extends BaseTest {
    //Variables
    String jsonFilePathForLogin = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLogin);

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUserThroughGUI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .loginWithValidUser(json.getData("Users[4].Email"),json.getData("Users[4].Password"))
                .assertUserIsLoggedIn(json.getData("Users[4].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser4)
                .storeSessionCookies(json.getData("Users[4].Name"));
    }

}
