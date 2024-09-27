package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;

import java.io.IOException;

import static utils.ApiManager.MakeRequest;
import static utils.ApiManager.getResponseBody;
import static utils.PropertiesManager.getPropertiesValue;

public class SearchProductRequestModel {

    //Variables
    String searchProductEndpoint = getPropertiesValue("baseUrlApi")+"searchProduct";

    String jsonBodyAsString;
    Response response;
    JsonMapper mapper;

    //ObjectsFromPojoClasses
    SearchProductRequestPojo requestObject;
    SearchProductResponsePojo responseObject;

    //Method to set Request body by Product name
    @Step("Set a Product Name to Request Body")
    public SearchProductRequestModel addProductNameToRequestBody(String productName) throws IOException, ParseException {
        requestObject = SearchProductRequestPojo.builder()
                .search_product(productName)
                .build();
        return this;
    }

    //Method to Execute Search Request
    @Step("Send Request of Search For Product")
    public SearchProductResponseModel sendRequestSearchForProduct() throws JsonProcessingException {
        response =
                MakeRequest("Post", searchProductEndpoint,requestObject, "application/x-www-form-urlencoded");
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, SearchProductResponsePojo.class);

        return new SearchProductResponseModel(requestObject,responseObject);
    }

}
