package prepareTestData;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import yehiaEngine.managers.JDBCManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoadUsersFromDB {
    static String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    static String jsonFilePathForCheckout = "src/test/resources/TestDataJsonFiles/CheckoutTestData.json";
    static String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";
    static String jsonFilePathForLogin = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
    static String jsonFilePathForContactus = "src/test/resources/TestDataJsonFiles/ContactusTestData.json";

    @Description("Load The Latest User from DB and Update them into Test Data Json Files")
    @Test
    @Step("Load Users from DB")
    public static void loadUsersFromDB() throws SQLException, IOException {
        String dbQuery = "SELECT Title,Username,Email,Password,DayofBirth,MonthofBirth,YearofBirth,FirstName,LastName,Company,Address1,Address2,Country,State,City,ZipCode,MobileNumber FROM automationexercise.users Order By Username Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeys = {"Title", "Name", "Email", "Password", "DayofBirth","MonthofBirth"
                ,"YearofBirth","FirstName", "LastName", "Company", "Address1", "Address2","Country","State"
                ,"City","ZipCode","MobileNumber"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKey = "Users";

        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForAddToCart, jsonKeys, jsonMainKey);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForCheckout, jsonKeys, jsonMainKey);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForSearchProduct, jsonKeys, jsonMainKey);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForLogin, jsonKeys, jsonMainKey);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForContactus, jsonKeys, jsonMainKey);
    }
}
