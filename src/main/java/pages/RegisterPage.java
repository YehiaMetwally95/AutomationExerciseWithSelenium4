package pages;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static utils.RandomDataGenerator.*;
import static utils.RandomDataGenerator.generateName;

public class RegisterPage extends HomePage{

    /*@AllArgsConstructor
    @Getter
    public enum Title {
    Mr ("Mr"),
    Mrs ("Mrs");
    private String title;
    }

    @AllArgsConstructor
    @Getter
    public enum Month {
        January ("January"),
        February ("February"),
        March ("March"),
        April ("April"),
        May ("May"),
        June ("June"),
        July ("July"),
        August ("August"),
        September ("September"),
        October ("October"),
        November ("November"),
        December ("December");

        private String month;
    }

    @AllArgsConstructor
    @Getter
    public enum Country {
        India ("India"),
        UnitedStates ("United States"),
        Canada ("Canada"),
        Australia("Australia"),
        NewZealand ("New Zealand"),
        Singapore ("Singapore");

        private String country;
    }*/

    //Variables

    //Locators
    By accountInfoHeader = By.cssSelector(".login-form h2");
    By addressInfoHeader = RelativeLocator.with(By.tagName("h2")).below(accountInfoHeader);
    By titleLocator;
    By nameLocator = By.id("name");
    By emailLocator= By.id("email");
    By passwordLocator= By.id("password");
    By dayLocator = By.id("days");
    By monthLocator= RelativeLocator.with(By.tagName("select")).toRightOf(dayLocator);
    By yearLocator= RelativeLocator.with(By.tagName("select")).toRightOf(monthLocator);
    By signUpForNewsPaperCheckbox = By.id("newsletter");
    By receiveOffersCheckbox = RelativeLocator.with(By.tagName("input")).below(signUpForNewsPaperCheckbox);
    By firstnameLocator = By.id("first_name");
    By lastnameLocator = RelativeLocator.with(By.tagName("input")).below(firstnameLocator);
    By companyLocator = By.id("company");
    By addressLocator = By.id("address1");
    By address2Locator = RelativeLocator.with(By.tagName("input")).below(addressLocator);
    By countryLocator = By.id("country");
    By stateLocator = By.id("state");
    By cityLocator = By.id("city");
    By zipcodeLocator = By.id("zipcode");
    By mobileNumberLocator = By.id("mobile_number");
    By createButtonLocator = RelativeLocator.with(By.tagName("button")).below(mobileNumberLocator);

    //Constructor
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    @Step("Register New User")
    public HomePage registerNewUser(String title,String name,String password,String day,String month,
                                        String year,String firstName,String lastName, String company,
                                        String address1, String address2,String country,String state,
                                        String city, String zipcode, String mobileNumber) throws IOException {
        enterAccountInfo(title,name,password,day,month,year).
               enterAddressInfo(firstName,lastName,company,address1,address2,country,state,city,zipcode,mobileNumber).
                checkToSignupForNewsPaper().
                checkToReceiveSpecialOffers().
               clickOnCreateButtonForSuccess().
                continueToHomePage();
        return new HomePage(driver);
    }

    //Validations
    @Step("Verify Register Page is Opened")
    public RegisterPage verifyRegisterPageIsOpened() {
      verifyAccountInfoHeaderDisplayed();
        return this;
    }

    //Private Methods
    private void setTitleLocator(String title)
    {
        titleLocator = By.xpath("//input[@value='"+title+"']");
    }

    @Step("Enter Account Information")
    private RegisterPage enterAccountInfo(String title,String name,String password,
                                          String day,String month,String year) throws IOException {
        setTitleLocator(title);
        bot.
                press(titleLocator).
                type(nameLocator,name).
                type(passwordLocator,password).
                selectFromDropdownByText(dayLocator,day).
                selectFromDropdownByText(monthLocator,month).
                selectFromDropdownByText(yearLocator,year);
        return this;
    }

    @Step("Enter Address Information")
    private RegisterPage enterAddressInfo(String firstName,String lastName, String company, String address1, String address2,
                                          String country,String state,String city, String zipcode, String mobileNumber) throws IOException {
        bot.
                type(firstnameLocator,firstName).
                type(lastnameLocator,lastName).
                type(companyLocator,company).
                type(addressLocator,address1).
                type(address2Locator,address2).
                selectFromDropdownByValue(countryLocator,country).
                type(stateLocator,state).
                type(cityLocator,city).
                type(zipcodeLocator,zipcode).
                type(mobileNumberLocator,mobileNumber);
        return this;
    }

    @Step("Check to Receive Special Offers")
    private RegisterPage checkToReceiveSpecialOffers() throws IOException {
        bot.press(receiveOffersCheckbox);
        return this;
    }

    @Step("Check to Sign UP for Newspaper")
    private RegisterPage checkToSignupForNewsPaper() throws IOException {
        bot.press(signUpForNewsPaperCheckbox);
        return this;
    }

    @Step("Click on Create Button For Success")
    private AccountCreatedPage clickOnCreateButtonForSuccess() throws IOException {
        bot.press(createButtonLocator);
        return new AccountCreatedPage(driver);
    }

    @Step("Verify Account Info Header Displayed")
    private RegisterPage verifyAccountInfoHeaderDisplayed() {
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(accountInfoHeader));
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(addressInfoHeader));
        return this;
    }

    //DropDown Getters
    public static String getRandomTitle() {
        List<String> titlesList = Arrays.asList("Mr","Mrs");
        return generateItemFromList(titlesList);
    }

    public static String getRandomCountry() {
        List<String> titlesList = Arrays.asList("India","United States","Canada","Australia"
                ,"New Zealand","Singapore");
        return generateItemFromList(titlesList);
    }

    public static String getRandomDayOfBirth() {
        return generateNumericalString(1,31);
    }

    public static String getRandomMonthOfBirth() {
        List<String> titlesList = Arrays.asList("January","February","March","April","May","June",
                "July","August","September","October","November","December");
        return generateItemFromList(titlesList);
    }

    public static String getRandomYearOfBirth() {
        return generateNumericalString(1900,2021);
    }
}