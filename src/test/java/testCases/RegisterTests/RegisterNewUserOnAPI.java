package testCases.RegisterTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.RegistrationRequestModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import engine.listeners.TestNGListners;
import engine.managers.JDBCManager;
import engine.managers.JsonManager;

import java.io.IOException;
import java.sql.SQLException;

import static engine.utilities.RandomDataGenerator.*;
import static engine.driverManager.BrowserFactory.*;


@Epic("Automation Exercise Features")
@Feature("User Register")
@Story("Verify New User Register")
@Listeners(TestNGListners.class)
public class RegisterNewUserOnAPI extends BaseTest {
    //Variables
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/RegisterTestData.json";
    JsonManager json;

    //Test Data Preparation as setting registration inputs with random data
    @BeforeMethod
    public void prepareTestData() throws IOException {
        json = new JsonManager(jsonFilePath);
        json
                .setData("NewUser2.Title",RegisterPage.getRandomTitle())
                .setData("NewUser2.Name",generateUniqueName())
                .setData("NewUser2.Email",generateUniqueEmail())
                .setData("NewUser2.Password",generateStrongPassword())
                .setData("NewUser2.DayOfBirth",RegisterPage.getRandomDayOfBirth())
                .setData("NewUser2.MonthOfBirth",RegisterPage.getRandomMonthOfBirth())
                .setData("NewUser2.YearOfBirth",RegisterPage.getRandomYearOfBirth())
                .setData("NewUser2.FirstName",generateName())
                .setData("NewUser2.LastName",generateName())
                .setData("NewUser2.Company",generateCompany())
                .setData("NewUser2.Address1",generateAddress())
                .setData("NewUser2.Address2",generateAddress())
                .setData("NewUser2.Country",RegisterPage.getRandomCountry())
                .setData("NewUser2.State",generateCity())
                .setData("NewUser2.City",generateCity())
                .setData("NewUser2.ZipCode",generateZipCode())
                .setData("NewUser2.MobileNumber",generateUniqueInteger());
    }

    //Test Script
    @Description("Register New User On API")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registerNewUserThroughAPI() throws IOException {
        var registerRequestObject = new RegistrationRequestModel()
                .prepareRegisterRequest(json.getData("NewUser2"))
                .sendRegisterRequest()
                .validateCodeFromResponse(201)
                .validateMassageFromResponse("User created!")
                .getRequestPojoObject();

        new HomePage(getDriver(isolatedDriver))
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened(json.getData("Messages.LoginHeader"),json.getData("Messages.SignupHeader"))
                .loginWithValidUser(registerRequestObject.getEmail(), registerRequestObject.getPassword())
                .assertUserIsLoggedIn(registerRequestObject.getName());
    }

    @AfterMethod
    public void updateDatabaseWithNewData() throws IOException, SQLException {
        if (System.getProperty("syncWithDB").equalsIgnoreCase("true")) {
            String title = json.getData("NewUser2.Title");
            String name = json.getData("NewUser2.Name");
            String email = json.getData("NewUser2.Email");
            String password = json.getData("NewUser2.Password");
            String dayOfBirth = json.getData("NewUser2.DayOfBirth");
            String monthOfBirth = json.getData("NewUser2.MonthOfBirth");
            String yearOfBirth = json.getData("NewUser2.YearOfBirth");
            String firstName = json.getData("NewUser2.FirstName");
            String LastName = json.getData("NewUser2.LastName");
            String company = json.getData("NewUser2.Company");
            String address1 = json.getData("NewUser2.Address1");
            String address2 = json.getData("NewUser2.Address2");
            String country = json.getData("NewUser2.Country");
            String state = json.getData("NewUser2.State");
            String city = json.getData("NewUser2.City");
            String zipCode = json.getData("NewUser2.ZipCode");
            String mobileNumber = json.getData("NewUser2.MobileNumber");

            String query = "INSERT INTO automationexercise.users VALUES ('" + title + "','" + name + "','" + email + "','" + password + "','" + dayOfBirth + "','" + monthOfBirth + "','" + yearOfBirth + "','" + firstName + "','" + LastName + "','" + company + "','" + address1 + "','" + address2 + "','" + country + "','" + state + "','" + city + "','" + zipCode + "','" + mobileNumber + "')";
            JDBCManager.insertNewRecordToDatabase(query);
        }
    }
}
