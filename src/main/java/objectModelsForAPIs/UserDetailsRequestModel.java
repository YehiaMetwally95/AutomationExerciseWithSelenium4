package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;
import pojoClassesForAPIs.UserDetailsRequestPojo;
import pojoClassesForAPIs.UserDetailsResponsePojo;
import utils.JsonManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.ApiManager.*;
import static utils.PropertiesManager.getPropertiesValue;

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
    public UserDetailsRequestModel setRequestParameterByUserEmail(String userEmail) throws IOException, ParseException {
        queryParam = new HashMap<>();
        queryParam.put("email",userEmail);
        return this;
    }

    //Method to Execute User Details Request
    @Step("Send Request of Get User Details")
    public UserDetailsResponseModel sendRequestGetUserDetails() throws JsonProcessingException {
        response =
                GetRequest(userDetailsEndpoint,queryParam);
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, UserDetailsResponsePojo.class);

        return new UserDetailsResponseModel(requestObject,responseObject);
    }

}
