package utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.*;
import com.jayway.jsonpath.*;

import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonManager {

    //Variables
    private String filePath;

    //Constructor
    public JsonManager(String filePath) {
        this.filePath = filePath;
    }

    //Method to Get JsonData by Key as String
    public String getData(String jsonPath) throws IOException {
        return JsonPath.parse(new File(filePath)).read(jsonPath);
    }

    //Method to Get JsonData by Key expressed as Json Object
    public Object getDataAsJson(String jsonPath) throws IOException {
        return JsonPath.parse(new File(filePath)).read(jsonPath);
    }

    //Method to Get JsonData by Key expressed as Json Array
    public JsonArray getDataAsJsonArray(String jsonPath) throws IOException {
        List<Object> list = JsonPath.parse(new File(filePath)).read(jsonPath);
        return JsonParser.parseString(list.toString()).getAsJsonArray();
    }

    //Method to Set Key expressed in JsonPath with new value
    public JsonManager setData(String jsonPath, String value) throws IOException {
        Object obj = readJsonFile(filePath);
        JsonPath.parse(obj).set(jsonPath,value);
        createJsonFile(obj,filePath);
        return this;
    }

    //Method to read JsonFile and convert it to JsonObject
    public static JsonObject readJsonFile(String filePath) throws IOException {
        //pass the path of test data json file
        File filename = new File(filePath);
        //convert the json file to string
        String jsonString = FileUtils.readFileToString(filename, "UTF8");
        //parse the json string into Json Object (Deserialization)
        return JsonParser.parseString(jsonString).getAsJsonObject();
    }

    //Method to Create JsonFile from Object
    public static void createJsonFile(Object obj , String filePath) throws IOException {
        FileWriter file = new FileWriter(filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJson = gson.toJson(obj);
        file.write(formattedJson);
        file.close();
    }

    //Method to Combine multiple JsonObjects then write them to JsonFile as a Combined Object
    public static void createJsonFileFromMultipleJsonObjects(JsonObject[] arr, String jsonFilePath) throws IOException {
        Map<String,Object> total = new HashMap<>();
        for (int i = 0;i<arr.length;i++)
        {
            total.putAll(arr[i].asMap());
        }
        //Write the Pretty Format of Parent JSON Array into the JSON File
        createJsonFile(total,jsonFilePath);
    }

    //Method to Convert Map to Json Object
    public static JsonObject convertMapToJsonObject(Map map) throws JsonProcessingException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(map);
        return gson.fromJson(jsonString,JsonObject.class);
    }

    //Method to Convert Object to Map
    public static  Map<String, Object> convertJsonStringToMap(Object object) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        Map<String, Object> map =
                gson.fromJson(jsonString, new TypeToken<Map<String, Object>>() {}.getType());
        return map;
    }

}


