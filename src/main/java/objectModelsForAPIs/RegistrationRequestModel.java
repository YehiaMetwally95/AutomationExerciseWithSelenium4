package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.RegistrationRequestPojo;
import pojoClassesForAPIs.RegistrationResponsePojo;
import engine.managers.JsonManager;

import java.io.IOException;

import static engine.managers.ApisManager.*;
import static engine.managers.PropertiesManager.getPropertiesValue;

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
    @Step("Prepare Registration Request Body From Json File")
    public RegistrationRequestModel prepareRegisterRequest(String userData) throws IOException {
        mapper = new JsonMapper();
        requestObject = mapper.readValue(userData, RegistrationRequestPojo.class);
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
