package testCases;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.JDBCManager;
import utils.JsonManager;
import utils.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

@Epic("Automation Exercise Features")
@Feature("Product Search")
@Story("Verify User can Search for any Product")
@Listeners(utils.TestNGListners.class)
public class SearchProductTests extends BaseTest {
    JsonManager json;
    String jsonFilePathForTestData2 = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";

    //Test Data Preparation as Getting Products from Database
    @BeforeClass
    public void prepareTestData() throws IOException, ParseException, SQLException {
        json = new JsonManager(jsonFilePathForTestData2);
        String dbQuery = "SELECT Id,Name,Brand,Category,Subcategory,Price,Availability,Situation FROM automationexercise.products Order by Id Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"ID","Name","Brand","Category","Subcategory","Price"
                ,"Availability","Condition"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Products";

        JDBCManager.setJsonFileFromDBForNestedArrayOfJsonObjects(dbQuery, jsonFilePathForTestData2,jsonKeysForUsers,jsonMainKeyForUsers);
    }

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod(dependsOnMethods = "setUpAndOpenBrowser")
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(driver,jsonFilePathForSessionData).applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        new HomePage(driver)
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test Scripts
    @Description("Search For Product And Open Product Page On GUI")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnGUI() throws IOException, ParseException {
        new HomePage(driver)
                .openProductsPage()
                .verifyProductPageIsOpened()
        // Search For Product is done On GUI Layer
                .searchForProduct(json.getData("Products[0].Name"))
                .verifySearchedProduct(json.getData("Products[0].Name"))
        // Open Product Details Page On GUI
                .openProductDetailsPage(json.getData("Products[0].Name"))
                .verifyProductDetailsPageIsOpened(json.getData("PageTitles[0].ProductDetailsPage"))
        // The Data Validation on product details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[0].Name")
                        ,json.getData("Products[0].Price"),json.getData("Products[0].Availability")
                        ,json.getData("Products[0].Condition"),json.getData("Products[0].Brand")
                        ,json.getData("Products[0].Category"),json.getData("Products[0].Subcategory"));
    }

    @Description("Search For Product And Open Product Page On API")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnAPI() throws IOException, ParseException {
        // Search For Product is done On API Layer
        var searchProductResponse = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[1].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
        // The Data Validation on product details is done on API Layer
                .validateProductIdFromResponse(json.getData("Products[1].ID"))
                .validateProductNameFromResponse(json.getData("Products[1].Name"))
                .validateProductPriceFromResponse(json.getData("Products[1].Price"))
                .validateProductBrandFromResponse(json.getData("Products[1].Brand"))
                .validateProductCategoryFromResponse(json.getData("Products[1].Category"))
                .validateProductSubcategoryFromResponse(json.getData("Products[1].Subcategory"))
                .getResponsePojoObject();

        // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(driver)
                .openProductDetailsPageByUrl(searchProductResponse.getProducts().getFirst().getId())
                .verifyProductDetailsPageIsOpened(json.getData("PageTitles[0].ProductDetailsPage"))
        // The Data Validation Again on product details But is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[1].Name")
                ,json.getData("Products[1].Price"),json.getData("Products[1].Availability")
                ,json.getData("Products[1].Condition"),json.getData("Products[1].Brand")
                ,json.getData("Products[1].Category"),json.getData("Products[1].Subcategory"));
    }

}
