package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pages.RegisterPage;
import pojoClassesForAPIs.RegistrationRequestPojo;
import pojoClassesForAPIs.RegistrationResponsePojo;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;
import static pages.RegisterPage.*;

public class RegistrationRequestModel {

    //Variables
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"createAccount";
    JsonManager json;

    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    RegistrationRequestPojo requestObject;
    RegistrationResponsePojo responseObject;

    //Method to set Request Body by reading from Json File
    @Step("Prepare Registration Request Body With Random Data")
    public RegistrationRequestModel prepareRegisterRequestWithRandomData() throws IOException {
        requestObject = RegistrationRequestPojo.builder()
                .title(RegisterPage.getRandomTitle())
                .name(generateUniqueName())
                .email(generateUniqueEmail())
                .password(generateStrongPassword())
                .birth_date(getRandomDayOfBirth())
                .birth_month(getRandomMonthOfBirth())
                .birth_year(getRandomYearOfBirth())
                .firstname(generateName())
                .lastname(generateName())
                .company(generateCompany())
                .address1(generateAddress())
                .address2(generateAddress())
                .country(getRandomCountry())
                .state(generateCity())
                .city(generateCity())
                .zipcode(generateZipCode())
                .mobile_number(generateUniqueInteger())
                .build();
        return this;
    }

    //Method to Execute Registration Request
    @Step("Send Request of Register New User")
    public RegistrationResponseModel sendRegisterRequest() throws JsonProcessingException {
        response =
                MakeRequest("Post", registerEndpoint, requestObject, "application/x-www-form-urlencoded");
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, RegistrationResponsePojo.class);

        return new RegistrationResponseModel(requestObject, responseObject);
    }

}
