package objectModelsForAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClassesForAPIs.SearchProductRequestPojo;
import pojoClassesForAPIs.SearchProductResponsePojo;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;

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
    public SearchProductRequestModel prepareSearchProductRequest(String productName) {
        requestObject = SearchProductRequestPojo.builder()
                .search_product(productName)
                .build();
        return this;
    }

    //Method to Execute Search Request
    @Step("Send Request of Search For Product")
    public SearchProductResponseModel sendSearchProductRequest() throws JsonProcessingException {
        response =
                MakeRequest(ApisManager.MethodType.POST, searchProductEndpoint,requestObject, ApisManager.ContentType.URLENCODED);
        jsonBodyAsString = getResponseBody(response);

        mapper = new JsonMapper();
        responseObject = mapper.readValue(jsonBodyAsString, SearchProductResponsePojo.class);

        return new SearchProductResponseModel(requestObject,responseObject);
    }

}
