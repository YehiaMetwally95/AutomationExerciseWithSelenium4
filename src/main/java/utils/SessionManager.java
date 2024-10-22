package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
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
    private JsonArray getCookiesData(){
        JsonArray cookies =new JsonArray();
        driver.manage().getCookies().stream()
                .forEach(
                        x -> {
                            JsonObject json = new JsonObject();
                            json.addProperty("name", x.getName());
                            json.addProperty("value", x.getValue());
                            json.addProperty("path", x.getPath());
                            json.addProperty("domain", x.getDomain());
                            json.addProperty("expiry", String.valueOf(x.getExpiry()));
                            json.addProperty("isSecure", x.isSecure());
                            json.addProperty("isHttpOnly", x.isHttpOnly());
                            cookies.add(json);
                        });
        return cookies;
    }

    // Store Cookies Data in Json File If login success for each time
    public void storeSessionCookies(String userName) throws IOException {

        JsonObject sessionObj = new JsonObject();
        sessionObj.addProperty("username", userName);
        //sessionObj.put("createdAt", LocalDateTime.now());
        sessionObj.add("cookies_data", getCookiesData());
        createJsonFile(sessionObj,jsonFilePath);
    }

    //Apply the Stored Cookies Data on Json File to the Current Session
    public void applyCookiesToCurrentSession() throws IOException {
        //Delete All Cookies
        deleteAllCookies(driver);

        //Apply the Saved Cookies in the JsonFile to Current Session
        JsonArray cookiesArray = json.getDataAsJsonArray("cookies_data");
        for (int i = 0; i < cookiesArray.size(); i++){
            JsonObject cookies = (JsonObject) cookiesArray.get(i);
            Cookie ck =
                    new Cookie.Builder(cookies.get("name").getAsString(), cookies.get("value").getAsString())
                            .path(cookies.get("path").getAsString())
                            .domain(cookies.get("domain").getAsString())
                            .expiresOn(
                                    !cookies.has("expiry") ? null : new Date(new Date().getTime() + 3600 * 1000))
                            .isSecure(cookies.get("isSecure").getAsBoolean())
                            .isHttpOnly(cookies.get("isHttpOnly").getAsBoolean())
                            .build();
            addCookie(driver,ck);
        }

        //Refresh the Browser Page to check the Updates
        refreshWindow(driver);
    }
}
