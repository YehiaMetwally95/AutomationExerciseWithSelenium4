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

import java.io.IOException;

import static utils.ThreadDriver.getIsolatedDriver;

@Epic("Automation Exercise Features")
@Feature("User Login")
@Story("Verify User Login Through GUI")
@Listeners(TestNGListners.class)
public class LoginWithNonExistingUserOnGUI extends BaseTest {
    //Variables
    String jsonFilePathForLogin = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLogin);

    //Test Scripts
    @Description("Login With Non Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithNonExistingUserThroughGUI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .loginWithInvalidUser(json.getData("Users[0].Email"),json.getData("Users[1].Password"))
                .assertIncorrectLoginMassage(json.getData("Messages[0].InvalidCredentials"));
    }

}