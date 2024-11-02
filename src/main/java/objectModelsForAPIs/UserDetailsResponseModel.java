package objectModelsForAPIs;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClassesForAPIs.UserDetailsRequestPojo;
import pojoClassesForAPIs.UserDetailsResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.assertions.CustomSoftAssert;

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
        CustomAssert.assertEquals(responseObject.getResponseCode(),responseCode);
        return this;
    }

    @Step("Validate Address Details from Response")
    public UserDetailsResponseModel validateAddressDetailsFromResponse(String name,String title,String firstName,
                                                                        String lastName,String company,String address1,
                                                                        String address2,String country,String state,
                                                                        String city,String zipcode) {
        validateUserNameFromResponse(name)
                .validateUserTitleFromResponse(title)
                .validateUserFirstNameFromResponse(firstName)
                .validateUserLastNameFromResponse(lastName)
                .validateUserCompanyFromResponse(company)
                .validateUserAddress1FromResponse(address1)
                .validateUserAddress2FromResponse(address2)
                .validateUserCountryFromResponse(country)
                .validateUserStateFromResponse(state)
                .validateUserCityFromResponse(city)
                .validateUserZipcodeFromResponse(zipcode);
        return this;
    }

    @Step("Validate User Id from Response")
    private UserDetailsResponseModel validateUserIdFromResponse(String id) {
        CustomSoftAssert.assertEquals(String.valueOf(responseObject.getUser().getId()),id);
        return this;
    }

    @Step("Validate User Name from Response")
    private UserDetailsResponseModel validateUserNameFromResponse(String name) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getName(),name);
        return this;
    }

    @Step("Validate User Email from Response")
    private UserDetailsResponseModel validateUserEmailFromResponse(String email) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getEmail(),email);
        return this;
    }

    @Step("Validate User Title from Response")
    private UserDetailsResponseModel validateUserTitleFromResponse(String title) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getTitle(),title);
        return this;
    }

    @Step("Validate User BirthDay from Response")
    private UserDetailsResponseModel validateUserBirthDayFromResponse(String birthDay) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getBirth_day(),birthDay);
        return this;
    }

    @Step("Validate User BirthMonth from Response")
    private UserDetailsResponseModel validateUserBirthMonthFromResponse(String birthMonth) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getBirth_month(),birthMonth);
        return this;
    }

    @Step("Validate User BirthYear from Response")
    private UserDetailsResponseModel validateUserBirthYearFromResponse(String birthYear) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getBirth_year(),birthYear);
        return this;
    }

    @Step("Validate User First Name from Response")
    private UserDetailsResponseModel validateUserFirstNameFromResponse(String firstName) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getFirst_name(),firstName);
        return this;
    }

    @Step("Validate User Last Name from Response")
    private UserDetailsResponseModel validateUserLastNameFromResponse(String lastName) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getLast_name(),lastName);
        return this;
    }

    @Step("Validate User Company from Response")
    private UserDetailsResponseModel validateUserCompanyFromResponse(String company) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getCompany(),company);
        return this;
    }

    @Step("Validate User Address1 from Response")
    private UserDetailsResponseModel validateUserAddress1FromResponse(String address1) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getAddress1(),address1);
        return this;
    }

    @Step("Validate User Address2 from Response")
    private UserDetailsResponseModel validateUserAddress2FromResponse(String address2) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getAddress2(),address2);
        return this;
    }

    @Step("Validate User Country from Response")
    private UserDetailsResponseModel validateUserCountryFromResponse(String country) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getCountry(),country);
        return this;
    }

    @Step("Validate User State from Response")
    private UserDetailsResponseModel validateUserStateFromResponse(String state) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getState(),state);
        return this;
    }

    @Step("Validate User City from Response")
    private UserDetailsResponseModel validateUserCityFromResponse(String city) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getCity(),city);
        return this;
    }

    @Step("Validate User Zipcode from Response")
    private UserDetailsResponseModel validateUserZipcodeFromResponse(String zipcode) {
        CustomSoftAssert.assertEquals(responseObject.getUser().getZipcode(),zipcode);
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
