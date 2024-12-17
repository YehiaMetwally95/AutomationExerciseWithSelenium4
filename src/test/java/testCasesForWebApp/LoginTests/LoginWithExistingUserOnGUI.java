package testCasesForWebApp.LoginTests;

import baseTest.BaseTestForWebApp;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pagesForWebApp.HomePage;
import yehiaEngine.managers.JsonManager;
import yehiaEngine.managers.SessionManager;

import java.io.IOException;

import static yehiaEngine.driverManager.AppiumFactory.*;

@Epic("Automation Exercise Features")
@Feature("User Login")
@Story("Verify User Login Through GUI")
public class LoginWithExistingUserOnGUI extends BaseTestForWebApp {
    //Variables
    String jsonFilePathForLogin = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLogin);

    @Description("Login With Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithExistingUser_0ThroughGUI() throws IOException {
        AppiumDriver driver = getDriver(isolatedDriver);
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
    public void loginWithExistingUser_1ThroughGUI() throws IOException {
        AppiumDriver driver = getDriver(isolatedDriver);
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
    public void loginWithExistingUser_2ThroughGUI() throws IOException {
        AppiumDriver driver = getDriver(isolatedDriver);
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
    public void loginWithExistingUser_3ThroughGUI() throws IOException {
        AppiumDriver driver = getDriver(isolatedDriver);
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
    public void loginWithExistingUser_4ThroughGUI() throws IOException {
        AppiumDriver driver = getDriver(isolatedDriver);
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
