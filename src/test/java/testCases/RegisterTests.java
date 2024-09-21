package testCases;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.JDBCManager;
import utils.JsonManager;
import static utils.RandomDataGenerator.*;
import static utils.RandomDataGenerator.generateUniqueEmail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@Epic("Automation Exercise Features")
@Feature("User Register")
@Story("Verify New User Register Through GUI")
@Listeners(utils.TestNGListners.class)
public class RegisterTests extends BaseTest {
    //Variables
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/RegisterTestData.json";
    JsonManager json;

    //Test Data Preparation
    @BeforeClass
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

    @AfterClass
    public void updateDatabaseWithNewData() throws IOException, SQLException, ParseException {
        String name = json.getData("NewUser1.Name");
        String email = json.getData("NewUser1.Email");
        String password = json.getData("NewUser1.Password");
        String query = "INSERT INTO automationexercise.users VALUES ('" +name+ "','" +email+ "','" +password+ "')";

        JDBCManager.insertNewRecordToDatabase(query);
    }

    //Test Scripts
    @Description("Register New User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registerNewUserThroughGUI() throws IOException, ParseException {
        new HomePage(driver)
                .openHomePage()
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
                .logout()
                .verifyLoginSignupPageIsOpened()
                .loginWithValidUser(json.getData("NewUser1.Email"),json.getData("NewUser1.Password"))
                .assertUserIsLoggedIn(json.getData("NewUser1.Name"));
    }

    @Description("Register Existing User")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registerExistingUserThroughGUI() throws IOException, ParseException {
        new HomePage(driver)
                .openHomePage()
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
                .logout()
                .signupWithExistingUser(json.getData("NewUser1.Name"),json.getData("NewUser1.Email"))
                .assertIncorrectSignupMassage(json.getData("Messages[0].DuplicatedEmail"));
    }
}
