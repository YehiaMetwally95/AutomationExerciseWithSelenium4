package testCases.ContactUsTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import prepareTestData.TestNGListners;
import utils.JsonManager;
import utils.SessionManager;

import java.io.IOException;

import static utils.ThreadDriver.getDriver;

@Epic("Automation Exercise Features")
@Feature("Contact Us")
@Story("Verify User can Submit Contact Us Form")
@Listeners(TestNGListners.class)
public class SubmitContactUsRequest extends BaseTest {

    static String jsonFilePathForContactus = "src/test/resources/TestDataJsonFiles/ContactusTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForContactus);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser0).applyCookiesToCurrentSession();
    }

    //Test Scripts
    @Description("Submit Contact Us Request")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void submitContactusRequest() throws IOException, ParseException {
        new HomePage(getDriver(isolatedDriver))
                .openContactUsPage()
                .verifyContactUsPageIsOpened()
                .submitContactUsRequest(json.getData("Details.Name"),
                        json.getData("Details.Email"),json.getData("Details.Subject"),
                        json.getData("Details.Description"),json.getData("Details.FilePath"))
                .assertContactSuccessMassage(json.getData("Messages[0].RequestSubmitted"));
    }
}
