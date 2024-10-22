package utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static utils.JsonManager.convertJsonStringToMap;
import static utils.Screenshot.logApiRequestsToAllureReport;

public class ApiManager {

    public static Response MakeRequest(String requestType,String endpoint,Object requestBody,String contentType) throws JsonProcessingException {
        RestAssured.registerParser("text/html", Parser.JSON);
        RequestSpecification request = RestAssured.given().filter(logApiRequestsToAllureReport());
        Response response = null;

        if(contentType != null)
        {
            if (contentType.equalsIgnoreCase("application/json"))
            {
                request = request.contentType(contentType).body(requestBody);
            }

            else if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))
            {
                //convert requestObject to Map
                Map<String, Object> map = convertJsonStringToMap(requestBody);
                request = request.contentType(contentType + "; charset=utf-8").formParams(map);
            }
        }

        switch (requestType)
        {
            case "Post":
                response = request.when().post(endpoint);
                break;
            case "Put":
                response = request.when().put(endpoint);
                break;
            case "Patch":
                response = request.when().patch(endpoint);
                break;
            case "Delete":
                response = request.when().delete(endpoint);
                break;
        }
        return response;
    }

    public static Response MakeAuthRequest(String requestType,String endpoint,Object requestBody,String contentType,
                                           String authType,String authUser,String authPass, String token) throws JsonProcessingException {
        RestAssured.registerParser("text/html", Parser.JSON);
        RequestSpecification request = RestAssured.given().filter(logApiRequestsToAllureReport());
        Response response = null;

        if(contentType != null)
        {
            if (contentType.equalsIgnoreCase("application/json"))
            {
                request = request.contentType(contentType).body(requestBody);
            }

            else if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))
            {
                //convert requestObject to Map
                Map<String, Object> map = convertJsonStringToMap(requestBody);
                request = request.contentType(contentType + "; charset=utf-8").formParams(map);
            }
        }

        if(authType.equalsIgnoreCase("BasicAuth"))
        {
            request = request.auth().basic(authUser,authPass);
        }

        else if(authType.equalsIgnoreCase("BearerToken"))
        {
            request = request.header("Authorization","Bearer "+token);
        }

        else if(authType.equalsIgnoreCase("CookieAuth"))
        {
            request = request.header("Cookie","token="+token);
        }

        else if(authType.equalsIgnoreCase("X-Auth-Token"))
        {
            request = request.header("x-auth-token",token);
        }

        switch (requestType)
        {
            case "Post":
                response = request.when().post(endpoint);
                break;
            case "Put":
                response = request.when().put(endpoint);
                break;

            case "Patch":
                response = request.when().patch(endpoint);
                break;

            case "Delete":
                response = request.when().delete(endpoint);
                break;
        }
        return response;
    }

    public static Response GetRequest(String endpoint,Map queryParameters)
    {
        RequestSpecification request = RestAssured.given().filter(logApiRequestsToAllureReport());
        Response response = null;

        if (queryParameters != null)
        {request = request.queryParams(queryParameters);}

        response = request.when().get(endpoint);
        return response;
    }

    public static Response GetAuthRequest(String endpoint,Map queryParameters,
                                          String authType,String authUser,String authPass, String token)
    {
        RequestSpecification request = RestAssured.given().filter(logApiRequestsToAllureReport());
        Response response = null;

        if (queryParameters != null)
        {request = request.queryParams(queryParameters);}

        if(authType.equalsIgnoreCase("BasicAuth"))
        {
            request = request.auth().basic(authUser,authPass);
        }

        else if(authType.equalsIgnoreCase("BearerToken"))
        {
            request = request.header("Authorization","Bearer "+token);
        }

        else if(authType.equalsIgnoreCase("CookieAuth"))
        {
            request = request.header("Cookie","token="+token);
        }
        else if(authType.equalsIgnoreCase("X-Auth-Token"))
        {
            request = request.header("x-auth-token",token);
        }

        response = request.when().get(endpoint);
        return response;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public static int getJsonIntValueFromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getInt(jsonPath);
    }

    public static String getJsonStringValuefromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getString(jsonPath);
    }

    public static JsonObject getJsonObjectfromResponse(Response response, String jsonPath)
    {
        return response.jsonPath().getJsonObject(jsonPath);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    public static void logResponseBody(Response response)
    {
        response.then().log().body();
        System.out.println("*********************************************");
    }

    public static String getResponseBody(Response response)
    {
       return response.getBody().asPrettyString();
    }

    public static List<Header> getResponseHeaders(Response response)
    {
        return response.getHeaders().asList();
    }

    public static String getResponseHeaderByName(Response response, String headerName)
    {
        return response.getHeader(headerName);
    }

    public static Map getResponseCookies(Response response)
    {
        return response.getCookies();
    }

    public static long getResponseTime(Response response, TimeUnit timeUnit)
    {
        return response.getTimeIn(timeUnit);
    }

    public static void verifyResponseCode(Response response, int code)
    {
        response.then().statusCode(code);
    }

    public static void verifyResponseContentType(Response response,String contentType)
    {
        response.then().contentType(contentType);
    }

}
