package testCases.LoginTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

import static yehiaEngine.driverManager.BrowserFactory.*;

@Epic("Automation Exercise Features")
@Feature("User Login")
@Story("Verify User Login Through GUI")
public class LoginWithNonExistingUserOnGUI extends BaseTest {
    //Variables
    String jsonFilePathForLogin = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForLogin);

    //Test Scripts
    @Description("Login With Non Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithNonExistingUserThroughGUI() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithInvalidUser(json.getData("Users[0].Email"),json.getData("Users[1].Password"))
                .assertIncorrectLoginMassage(json.getData("Messages.InvalidCredentials"));
    }

}
