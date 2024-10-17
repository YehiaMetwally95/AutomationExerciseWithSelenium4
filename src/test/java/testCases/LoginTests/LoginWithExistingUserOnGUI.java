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

import static utils.ThreadDriver.getDriver;

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
    public void loginWithExistingUser_0ThroughGUI() throws IOException, ParseException {
        WebDriver driver = getDriver(isolatedDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(json.getData("Users[0].Email"),json.getData("Users[0].Password"))
                .assertUserIsLoggedIn(json.getData("Users[0].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser0)
                .storeSessionCookies(json.getData("Users[0].Name"));
    }

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUser_1ThroughGUI() throws IOException, ParseException {
        WebDriver driver = getDriver(isolatedDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(json.getData("Users[1].Email"),json.getData("Users[1].Password"))
                .assertUserIsLoggedIn(json.getData("Users[1].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser1)
                .storeSessionCookies(json.getData("Users[1].Name"));
    }

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUser_2ThroughGUI() throws IOException, ParseException {
        WebDriver driver = getDriver(isolatedDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(json.getData("Users[2].Email"),json.getData("Users[2].Password"))
                .assertUserIsLoggedIn(json.getData("Users[2].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser2)
                .storeSessionCookies(json.getData("Users[2].Name"));
    }

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUser_3ThroughGUI() throws IOException, ParseException {
        WebDriver driver = getDriver(isolatedDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(json.getData("Users[3].Email"),json.getData("Users[3].Password"))
                .assertUserIsLoggedIn(json.getData("Users[3].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser3)
                .storeSessionCookies(json.getData("Users[3].Name"));
    }

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUser_4ThroughGUI() throws IOException, ParseException {
        WebDriver driver = getDriver(isolatedDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(json.getData("Users[4].Email"),json.getData("Users[4].Password"))
                .assertUserIsLoggedIn(json.getData("Users[4].Name"));

        //Store Cookies into Json File for the next tests to bypass login
        new SessionManager(driver, jsonFilePathForSessionDataUser4)
                .storeSessionCookies(json.getData("Users[4].Name"));
    }

}
