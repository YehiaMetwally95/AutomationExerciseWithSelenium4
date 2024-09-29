package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import pojoClassesForAPIs.RegistrationRequestPojo;
import pojoClassesForAPIs.RegistrationResponsePojo;
import utils.JsonManager;

import java.io.IOException;

import static utils.ApiManager.*;
import static utils.PropertiesManager.getPropertiesValue;

public class RegistrationRequestModel {

    //Variables
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"createAccount";
    String jsonFilePath = "src/test/resources/TestDataJsonFiles/RegisterTestData.json";
    JsonManager json;

    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    RegistrationRequestPojo requestObject;
    RegistrationResponsePojo responseObject;

    //Method to set Request Body by reading from Json File
    @Step("Prepare Registration Request Body From Json File")
    public RegistrationRequestModel prepareRegistrationRequestBody(String userJsonObject) throws IOException, ParseException {
        json = new JsonManager(jsonFilePath);
        jsonBodyAsString = userJsonObject;

        mapper = new JsonMapper();
        requestObject = mapper.readValue(jsonBodyAsString, RegistrationRequestPojo.class);
        return this;
    }

    //Method to Execute Registration Request
    @Step("Send Request of Register New User")
    public RegistrationResponseModel sendRequestRegisterNewUser() throws JsonProcessingException {
        response =
                MakeRequest("Post", registerEndpoint,requestObject, "application/x-www-form-urlencoded");
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, RegistrationResponsePojo.class);

        return new RegistrationResponseModel(requestObject,responseObject);
    }

}
