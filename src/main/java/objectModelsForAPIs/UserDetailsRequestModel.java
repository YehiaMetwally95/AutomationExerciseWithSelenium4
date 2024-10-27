package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.UserDetailsRequestPojo;
import pojoClassesForAPIs.UserDetailsResponsePojo;

import java.util.HashMap;
import java.util.Map;

import static engine.managers.ApisManager.*;
import static engine.managers.PropertiesManager.getPropertiesValue;

public class UserDetailsRequestModel {

    //Variables
    String userDetailsEndpoint = getPropertiesValue("baseUrlApi")+"getUserDetailByEmail";
    Map<String,String > queryParam;
    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    UserDetailsRequestPojo requestObject;
    UserDetailsResponsePojo responseObject;

    //Method to set Request Parameter by User Email
    @Step("Set Request Parameters by User Email")
    public UserDetailsRequestModel prepareUserDetailsRequest(String userEmail) {
        queryParam = new HashMap<>();
        queryParam.put("email",userEmail);
        return this;
    }

    //Method to Execute User Details Request
    @Step("Send Request of Get User Details")
    public UserDetailsResponseModel sendUserDetailsRequest() throws JsonProcessingException {
        response =
                GetRequest(userDetailsEndpoint,queryParam);
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, UserDetailsResponsePojo.class);

        return new UserDetailsResponseModel(requestObject,responseObject);
    }

}
