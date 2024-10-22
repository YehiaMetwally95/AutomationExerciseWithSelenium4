package prepareTestData;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import utils.JDBCManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoadProductsFromDB {
    static String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    static String jsonFilePathForCheckout = "src/test/resources/TestDataJsonFiles/CheckoutTestData.json";
    static String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";

    @Description("Load The Latest Products from DB and Update them into Test Data Json Files")
    @Test
    public static void loadProductsFromDB() throws SQLException, IOException {
        String dbQuery = "SELECT Id,Name,Category,Subcategory,Price,Availability,Situation,Brand,Quantity,TotalPrice FROM automationexercise.products Order by Id Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"ID", "Name","Category", "Subcategory", "Price","Availability"
                ,"Condition","Brand","Quantity","TotalPrice"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Products";

        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForAddToCart, jsonKeysForUsers, jsonMainKeyForUsers);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForCheckout, jsonKeysForUsers, jsonMainKeyForUsers);
        JDBCManager.setJsonFileFromDBAsNestedArrayOfJsonObjects(dbQuery, jsonFilePathForSearchProduct, jsonKeysForUsers, jsonMainKeyForUsers);
    }
}
