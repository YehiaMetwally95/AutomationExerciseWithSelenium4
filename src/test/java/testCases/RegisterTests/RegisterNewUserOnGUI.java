package testCases.RegisterTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.testng.annotations.*;
import pages.HomePage;
import pages.RegisterPage;
import prepareTestData.TestNGListners;
import utils.JDBCManager;
import utils.JsonManager;
import static utils.RandomDataGenerator.*;
import static utils.RandomDataGenerator.generateUniqueEmail;
import static utils.ThreadDriver.getDriver;

import java.io.IOException;
import java.sql.SQLException;


@Epic("Automation Exercise Features")
@Feature("User Register")
@Story("Verify New User Register")
@Listeners(TestNGListners.class)
public class RegisterNewUserOnGUI extends BaseTest {
    //Variables
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/RegisterTestData.json";
    JsonManager json;

    //Test Data Preparation as setting registration inputs with random data
    @BeforeMethod
    public void prepareTestData() throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        json
                .setData("NewUser1.Title",RegisterPage.getRandomTitle())
                .setData("NewUser1.Name",generateUniqueName())
                .setData("NewUser1.Email",generateUniqueEmail())
                .setData("NewUser1.Password",generateStrongPassword())
                .setData("NewUser1.DayOfBirth",RegisterPage.getRandomDayOfBirth())
                .setData("NewUser1.MonthOfBirth",RegisterPage.getRandomMonthOfBirth())
                .setData("NewUser1.YearOfBirth",RegisterPage.getRandomYearOfBirth())
                .setData("NewUser1.FirstName",generateName())
                .setData("NewUser1.LastName",generateName())
                .setData("NewUser1.Company",generateCompany())
                .setData("NewUser1.Address1",generateAddress())
                .setData("NewUser1.Address2",generateAddress())
                .setData("NewUser1.Country",RegisterPage.getRandomCountry())
                .setData("NewUser1.State",generateCity())
                .setData("NewUser1.City",generateCity())
                .setData("NewUser1.ZipCode",generateZipCode())
                .setData("NewUser1.MobileNumber",generateUniqueInteger());
    }

    //Test Scripts
    @Description("Register New User On GUI")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registerNewUserThroughGUI() throws IOException, ParseException {
        new HomePage(getDriver(isolatedDriver))
                .verifyHomePageIsOpened()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .signupWithNewUser(json.getData("NewUser1.Name"),json.getData("NewUser1.Email"))
                .registerNewUser(json.getData("NewUser1.Title"),json.getData("NewUser1.Name"),json.getData("NewUser1.Password")
                        ,json.getData("NewUser1.DayOfBirth"),json.getData("NewUser1.MonthOfBirth"),json.getData("NewUser1.YearOfBirth")
                        ,json.getData("NewUser1.FirstName"),json.getData("NewUser1.LastName"),json.getData("NewUser1.Company")
                        ,json.getData("NewUser1.Address1"),json.getData("NewUser1.Address2"),json.getData("NewUser1.Country")
                        ,json.getData("NewUser1.State"),json.getData("NewUser1.City"),json.getData("NewUser1.ZipCode")
                        ,json.getData("NewUser1.MobileNumber"))
              /*  .logout()
                .openLoginSignupPage()
                .verifyLoginSignupPageIsOpened()
                .loginWithValidUser(json.getData("NewUser1.Email"),json.getData("NewUser1.Password"))*/
                .assertUserIsLoggedIn(json.getData("NewUser1.Name"));
    }

    @AfterMethod
    public void updateDatabaseWithNewData() throws IOException, SQLException, ParseException {
        if (System.getProperty("syncWithDB").equalsIgnoreCase("true")) {
            String title = json.getData("NewUser1.Title");
            String name = json.getData("NewUser1.Name");
            String email = json.getData("NewUser1.Email");
            String password = json.getData("NewUser1.Password");
            String dayOfBirth = json.getData("NewUser1.DayOfBirth");
            String monthOfBirth = json.getData("NewUser1.MonthOfBirth");
            String yearOfBirth = json.getData("NewUser1.YearOfBirth");
            String firstName = json.getData("NewUser1.FirstName");
            String LastName = json.getData("NewUser1.LastName");
            String company = json.getData("NewUser1.Company");
            String address1 = json.getData("NewUser1.Address1");
            String address2 = json.getData("NewUser1.Address2");
            String country = json.getData("NewUser1.Country");
            String state = json.getData("NewUser1.State");
            String city = json.getData("NewUser1.City");
            String zipCode = json.getData("NewUser1.ZipCode");
            String mobileNumber = json.getData("NewUser1.MobileNumber");

            String query = "INSERT INTO automationexercise.users VALUES ('" + title + "','" + name + "','" + email + "','" + password + "','" + dayOfBirth + "','" + monthOfBirth + "','" + yearOfBirth + "','" + firstName + "','" + LastName + "','" + company + "','" + address1 + "','" + address2 + "','" + country + "','" + state + "','" + city + "','" + zipCode + "','" + mobileNumber + "')";
            JDBCManager.insertNewRecordToDatabase(query);
        }
    }
}
