package testCases.RegisterTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.RegistrationRequestModel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.HomePage;

import yehiaEngine.managers.JDBCManager;
import yehiaEngine.managers.JsonManager;
import pojoClassesForAPIs.RegistrationRequestPojo;

import java.io.IOException;
import java.sql.SQLException;

import static yehiaEngine.driverManager.BrowserFactory.*;


@Epic("Automation Exercise Features")
@Feature("User Register")
@Story("Verify New User Register")
public class RegisterNewUserOnAPI extends BaseTest {
    //Variables
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/RegisterTestData.json";
    JsonManager json;
    RegistrationRequestPojo registerRequestObject;

    //Test Script
    @Description("Register New User On API")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registerNewUserThroughAPI() throws IOException {
        json = new JsonManager(jsonFilePath);
        //Test Data Preparation as setting registration inputs Dynamically with random data
         registerRequestObject = new RegistrationRequestModel()
                .prepareRegisterRequestWithRandomData()
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
            String title = registerRequestObject.getTitle();
            String name = registerRequestObject.getName();
            String email = registerRequestObject.getEmail();
            String password = registerRequestObject.getPassword();
            String dayOfBirth = registerRequestObject.getBirth_date();
            String monthOfBirth = registerRequestObject.getBirth_month();
            String yearOfBirth = registerRequestObject.getBirth_year();
            String firstName = registerRequestObject.getFirstname();
            String LastName = registerRequestObject.getLastname();
            String company = registerRequestObject.getCompany();
            String address1 = registerRequestObject.getAddress1();
            String address2 = registerRequestObject.getAddress2();
            String country = registerRequestObject.getCountry();
            String state = registerRequestObject.getState();
            String city = registerRequestObject.getCity();
            String zipCode = registerRequestObject.getZipcode();
            String mobileNumber = registerRequestObject.getMobile_number();

            String query = "INSERT INTO automationexercise.users VALUES ('" + title + "','" + name + "','" + email + "','" + password + "','" + dayOfBirth + "','" + monthOfBirth + "','" + yearOfBirth + "','" + firstName + "','" + LastName + "','" + company + "','" + address1 + "','" + address2 + "','" + country + "','" + state + "','" + city + "','" + zipCode + "','" + mobileNumber + "')";
            JDBCManager.insertNewRecordToDatabase(query);
        }
    }
}
