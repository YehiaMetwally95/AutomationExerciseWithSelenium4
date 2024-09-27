package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static utils.CookiesManager.*;
import static utils.JsonManager.createJsonFile;
import static utils.WindowManager.*;

public class SessionManager {
    private WebDriver driver;
    private String jsonFilePath;
    JsonManager json;

    public SessionManager(WebDriver driver, String jsonFilePath){
        this.driver= driver;
        this.jsonFilePath = jsonFilePath;
        json = new JsonManager(jsonFilePath);
    }

    // Get Cookies
    private JSONArray getCookiesData(){
        JSONArray cookies =new JSONArray();
        driver.manage().getCookies().stream()
                .forEach(
                        x -> {
                            JSONObject json = new JSONObject();
                            json.put("name", x.getName());
                            json.put("value", x.getValue());
                            json.put("path", x.getPath());
                            json.put("domain", x.getDomain());
                            json.put("expiry", x.getExpiry());
                            json.put("isSecure", x.isSecure());
                            json.put("isHttpOnly", x.isHttpOnly());
                            cookies.add(json);
                        });
        return cookies;
    }

    // Store Cookies Data in Json File If login success for each time
    public void storeSessionCookies(String userName) throws IOException {

        JSONObject sessionObj = new JSONObject();
        sessionObj.put("username", userName);
        //sessionObj.put("createdAt", LocalDateTime.now());
        sessionObj.put("cookies_data", getCookiesData());
        createJsonFile(sessionObj,jsonFilePath);
    }

    //Apply the Stored Cookies Data on Json File to the Current Session
    public void applyCookiesToCurrentSession() throws IOException, ParseException {
        //Delete All Cookies
        deleteAllCookies(driver);

        //Apply the Saved Cookies in the JsonFile to Current Session
        JSONArray cookiesArray = (JSONArray) json.getDataAsObject("cookies_data");
        for (int i = 0; i < cookiesArray.size(); i++){
            JSONObject cookies = (JSONObject) cookiesArray.get(i);
            Cookie ck =
                    new Cookie.Builder(cookies.get("name").toString(), cookies.get("value").toString())
                            .path(cookies.get("path").toString())
                            .domain(cookies.get("domain").toString())
                            .expiresOn(
                                    !cookies.containsKey("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                            .isSecure((Boolean) cookies.get("isSecure"))
                            .isHttpOnly((Boolean) cookies.get("isHttpOnly"))
                            .build();
            addCookie(driver,ck);
        }

        //Refresh the Browser Page to check the Updates
        refreshWindow(driver);
    }
}
