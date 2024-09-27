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
@Feature("AddToCart")
@Story("Verify User can add Products to Cart")
@Listeners(utils.TestNGListners.class)
public class AddToCartTests extends BaseTest {
    JsonManager json;
    String jsonFilePathForTestData2 = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";

    //Test Data Preparation as Getting Products from Database
    @BeforeClass
    public void prepareTestData() throws IOException, ParseException, SQLException {
        json = new JsonManager(jsonFilePathForTestData2);
        String dbQuery = "SELECT Id,Name,Category,Subcategory,Price,Quantity,TotalPrice FROM automationexercise.products Order by Id Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"ID", "Name","Category", "Subcategory", "Price"
                ,"Quantity","TotalPrice"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Products";

        JDBCManager.setJsonFileFromDBForNestedArrayOfJsonObjects(dbQuery, jsonFilePathForTestData2, jsonKeysForUsers, jsonMainKeyForUsers);
    }

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod (dependsOnMethods = "setUpAndOpenBrowser")
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
    @Description("Search for Product On GUI then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnGUI() throws IOException, ParseException {
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openProductsPage()
        // Search for Products and Add them to Cart On GUI Layer
                .addProductToCart(json.getData("Products[1].Name"))
                .continueShopping()
                .addProductToCart(json.getData("Products[4].Name"))
                .continueShopping()
                .searchForProduct(json.getData("Products[3].Name"))
                .openProductDetailsPage(json.getData("Products[3].Name"))
                .setProductQuantity(json.getData("Products[3].Quantity"))
                .addProductToCart()
        // Open Cart Page and Validate Products are Added to Cart On GUI
                .viewCart()
                .verifyProductIsAddedToCart(json.getData("Products[1].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[4].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[3].Name"))
        // The Data Validation on product details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[1].Name")
                        ,json.getData("Products[1].Price")
                        ,json.getData("Products[1].Quantity")
                        ,json.getData("Products[1].TotalPrice"))
                .verifyAllProductDetails(json.getData("Products[4].Name")
                        ,json.getData("Products[4].Price")
                        ,json.getData("Products[4].Quantity")
                        ,json.getData("Products[4].TotalPrice"))
                .verifyAllProductDetails(json.getData("Products[3].Name")
                        ,json.getData("Products[3].Price")
                        ,json.getData("Products[3].Quantity")
                        ,json.getData("Products[3].TotalPrice"));
    }

    @Description("Search for Product On API then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnAPI() throws IOException, ParseException {
    // Search for Products On API Layer
        var searchProductResponse1 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[0].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse2 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[2].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse3 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[3].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
    // The Data Validation on product details is done on API Layer
                .validateProductIdFromResponse(json.getData("Products[3].ID"))
                .validateProductNameFromResponse(json.getData("Products[3].Name"))
                .validateProductPriceFromResponse(json.getData("Products[3].Price"))
                .validateProductCategoryFromResponse(json.getData("Products[3].Category"))
                .validateProductSubcategoryFromResponse(json.getData("Products[3].Subcategory"))
                .validateProductTotalPrice(json.getData("Products[3].Quantity"),json.getData("Products[3].TotalPrice"))
                .getResponsePojoObject();

   // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(driver)
                .openProductDetailsPageByUrl(searchProductResponse1.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[0].Quantity"))
                .addProductToCart()
                .continueShopping()
                .openProductDetailsPageByUrl(searchProductResponse2.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[2].Quantity"))
                .addProductToCart()
                .continueShopping().openProductDetailsPageByUrl(searchProductResponse3.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[3].Quantity"))
                .addProductToCart()
    // Open Cart Page and Validate Products are Added to Cart On GUI
                .viewCart()
                .verifyProductIsAddedToCart(json.getData("Products[0].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[2].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[3].Name"));
    }

    @Description("Search for Product On API then Remove Product From Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void removeProductFromCartWithSearchOnAPI() throws IOException, ParseException {
    //Search for 3 Products On API
        var searchProductResponse1 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[0].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse2 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[4].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse3 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[3].Name"))
                .sendRequestSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

    // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(driver)
                .openProductDetailsPageByUrl(searchProductResponse1.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[0].Quantity"))
                .addProductToCart()
                .continueShopping()
                .openProductDetailsPageByUrl(searchProductResponse2.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[4].Quantity"))
                .addProductToCart()
                .continueShopping().openProductDetailsPageByUrl(searchProductResponse3.getProducts().getFirst().getId())
                .setProductQuantity(json.getData("Products[3].Quantity"))
                .addProductToCart()
    // Open Cart Page and Remove 2 Products On GUI Layer
                .viewCart()
                .removeProductFromCart(json.getData("Products[0].Name"))
                .removeProductFromCart(json.getData("Products[4].Name"))
    // Validate the Added and Removed Products on Cart On GUI Layer
                .verifyProductIsRemovedFromCart(json.getData("Products[0].Name"))
                .verifyProductIsRemovedFromCart(json.getData("Products[4].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[3].Name"));
    }
}
