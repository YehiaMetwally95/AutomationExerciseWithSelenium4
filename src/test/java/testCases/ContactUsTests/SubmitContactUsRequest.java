package testCases.ContactUsTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;
import yehiaEngine.managers.SessionManager;

import java.io.IOException;

import static yehiaEngine.driverManager.BrowserFactory.*;

@Epic("Automation Exercise Features")
@Feature("Contact Us")
@Story("Verify User can Submit Contact Us Form")
public class SubmitContactUsRequest extends BaseTest {

    static String jsonFilePathForContactus = "src/test/resources/TestDataJsonFiles/ContactusTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForContactus);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser0).applyCookiesToCurrentSession();
    }

    //Test Scripts
    @Description("Submit Contact Us Request")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void submitContactusRequest() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openContactUsPage()
                .verifyContactUsPageIsOpened(json.getData("Messages.ContactUsHeader"))
                .submitContactUsRequest(json.getData("Details.Name"),
                        json.getData("Details.Email"),json.getData("Details.Subject"),
                        json.getData("Details.Description"),json.getData("Details.FilePath"))
                .assertContactSuccessMassage(json.getData("Messages.RequestSubmitted"));
    }
}
