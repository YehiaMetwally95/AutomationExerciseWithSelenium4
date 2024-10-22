package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static utils.PropertiesManager.*;
import static utils.JsonManager.*;

public class JDBCManager {
    // Retrieve DB records and represent them as Array of JsonObjects with one Nested Key to the whole JsonArray
    public static JsonObject setJsonObjectFromDBAsNestedArrayOfJsonObjects(String query ,
                                                                           String[] jsonKeys, String jsonMainKey) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create JSON Objects and JSON Array that represent the Json File Format
        JsonArray array = new JsonArray();
        JsonObject record;
        JsonObject mainKey = new JsonObject();
        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JsonObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.addProperty(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
            //Fill the  Json Array of each object hat represent a certain row on Table
            array.add(record);
        }
        //assign the Json Array as value of the Main key
        mainKey.add(jsonMainKey,array);

        return mainKey;
    }

    // Retrieve DB records and represent them as JsonObjects with Nested Key for every JsonObject
    public static JsonObject setJsonObjectFromDBAsNestedJsonObjects(String query ,
                                                                    String[] jsonKeys, String[] jsonMainKeys) throws SQLException, IOException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));        //Execute the Select Query on the Database and retrieve the query results in Result Set
        ResultSet rs = connection.createStatement().executeQuery(query);

        //Create Parent & Child JSON Objects that represent the Json File Format
        JsonObject mainRecord = new JsonObject();
        JsonObject record;

        //Outer Looping on each record/row on Database table
        while((rs.next())) {
            //Inner Looping on each column in a specific row of Database table
            record = new JsonObject();
            for (int i = 0 ; i <rs.getMetaData().getColumnCount(); i++)
            {
                //Fill the child Json Object with the column value of a specific row
                record.addProperty(jsonKeys[i],
                        rs.getString(rs.getMetaData().getColumnName(i+1)));
            }
            //Fill the Parent Json Object with main keys and the corresponding child object of each key
            mainRecord.add(jsonMainKeys[ rs.getRow()-1 ], record);
        }
        return mainRecord;
    }

    //**********************************************************************************************************************//

    // Retrieve DB records and Write them on JsonFile that represent them as Array of JsonObjects with one Nested Key to the whole JsonArray
    public static void setJsonFileFromDBAsNestedArrayOfJsonObjects(String query, String jsonFilePath, String[] jsonKeys,
                                                                   String jsonMainKey) throws SQLException, IOException {
        //Read the object1 for data retrieved from Database
        JsonObject object1 = setJsonObjectFromDBAsNestedArrayOfJsonObjects(query,jsonKeys,jsonMainKey);

        //Read the Current Json File content and convert it to Object 2
        JsonObject object2 = readJsonFile(jsonFilePath);

        //Combine the two Json objects into one Object then Write it to the JSON File
        object2.asMap().putAll(object1.asMap());
        createJsonFile(object2,jsonFilePath);
    }

    // Retrieve DB records and Write them on JsonFile that represent them as JsonObjects with Nested Key for every JsonObject
    public static void setJsonFileFromDBForNestedJsonObjects(String query, String jsonFilePath,
                                                                         String[] jsonKeys,String[] jsonMainKeys) throws SQLException, IOException {
        //Read the object1 for data retrieved from Database
        JsonObject object1 = setJsonObjectFromDBAsNestedJsonObjects(query,jsonKeys,jsonMainKeys);

        //Read the Current Json File content and convert it to Object 2
        JsonObject object2 = readJsonFile(jsonFilePath);

        //Combine the two Json objects into one Object then Write it to the JSON File
        object2.asMap().putAll(object1.asMap());
        createJsonFile(object2,jsonFilePath);
    }

    //**********************************************************************************************************************//

    //Insert New Record to Database
    public static void insertNewRecordToDatabase(String query) throws IOException, SQLException {
        //Register Driver Classs for the Database
        DriverManager.registerDriver(new Driver());
        //Create Connection with the Database
        Connection connection = DriverManager.getConnection(getPropertiesValue("dbUrl")
                ,getPropertiesValue("dbUser"),getPropertiesValue("dbPassword"));
        connection.createStatement().executeUpdate(query);
    }
}


