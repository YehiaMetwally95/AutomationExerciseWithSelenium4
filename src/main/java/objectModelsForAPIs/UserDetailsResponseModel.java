package objectModelsForAPIs;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;
import pojoClassesForAPIs.UserDetailsRequestPojo;
import pojoClassesForAPIs.UserDetailsResponsePojo;
import utils.CustomSoftAssert;

public class UserDetailsResponseModel {
    //ObjectsFromPojoClasses
    UserDetailsRequestPojo requestObject;
    UserDetailsResponsePojo responseObject;

    //Constructor to pass the response from Request Model to Response Model
    public UserDetailsResponseModel(UserDetailsRequestPojo requestObject, UserDetailsResponsePojo responseObject) {
        this.requestObject = requestObject;
        this.responseObject = responseObject;
    }

    //Validation Methods
    @Step("Validate Response Code from Response")
    public UserDetailsResponseModel validateResponseCodeFromResponse(int responseCode) {
        Assert.assertEquals(responseObject.getResponseCode(),responseCode);
        return this;
    }

    @Step("Validate User Id from Response")
    public UserDetailsResponseModel validateUserIdFromResponse(String id) {
        CustomSoftAssert.softAssert.assertEquals(String.valueOf(responseObject.getUser().getId()),id);
        return this;
    }

    @Step("Validate User Name from Response")
    public UserDetailsResponseModel validateUserNameFromResponse(String name) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getName(),name);
        return this;
    }

    @Step("Validate User Email from Response")
    public UserDetailsResponseModel validateUserEmailFromResponse(String email) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getEmail(),email);
        return this;
    }

    @Step("Validate User Title from Response")
    public UserDetailsResponseModel validateUserTitleFromResponse(String title) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getTitle(),title);
        return this;
    }

    @Step("Validate User BirthDay from Response")
    public UserDetailsResponseModel validateUserBirthDayFromResponse(String birthDay) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getBirth_day(),birthDay);
        return this;
    }

    @Step("Validate User BirthMonth from Response")
    public UserDetailsResponseModel validateUserBirthMonthFromResponse(String birthMonth) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getBirth_month(),birthMonth);
        return this;
    }

    @Step("Validate User BirthYear from Response")
    public UserDetailsResponseModel validateUserBirthYearFromResponse(String birthYear) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getBirth_year(),birthYear);
        return this;
    }

    @Step("Validate User First Name from Response")
    public UserDetailsResponseModel validateUserFirstNameFromResponse(String firstName) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getFirst_name(),firstName);
        return this;
    }

    @Step("Validate User Last Name from Response")
    public UserDetailsResponseModel validateUserLastNameFromResponse(String lastName) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getLast_name(),lastName);
        return this;
    }

    @Step("Validate User Company from Response")
    public UserDetailsResponseModel validateUserCompanyFromResponse(String company) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getCompany(),company);
        return this;
    }

    @Step("Validate User Address1 from Response")
    public UserDetailsResponseModel validateUserAddress1FromResponse(String address1) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getAddress1(),address1);
        return this;
    }

    @Step("Validate User Address2 from Response")
    public UserDetailsResponseModel validateUserAddress2FromResponse(String address2) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getAddress2(),address2);
        return this;
    }

    @Step("Validate User Country from Response")
    public UserDetailsResponseModel validateUserCountryFromResponse(String country) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getCountry(),country);
        return this;
    }

    @Step("Validate User State from Response")
    public UserDetailsResponseModel validateUserStateFromResponse(String state) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getState(),state);
        return this;
    }

    @Step("Validate User City from Response")
    public UserDetailsResponseModel validateUserCityFromResponse(String city) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getCity(),city);
        return this;
    }

    @Step("Validate User Zipcode from Response")
    public UserDetailsResponseModel validateUserZipcodeFromResponse(String zipcode) {
        CustomSoftAssert.softAssert.assertEquals(responseObject.getUser().getZipcode(),zipcode);
        return this;
    }

    //Getter Methods
    public UserDetailsRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public UserDetailsResponsePojo getResponsePojoObject() {
        return responseObject;
    }
}
