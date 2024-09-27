package testCases;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import objectModelsForAPIs.UserDetailsRequestModel;
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
@Feature("Checkout")
@Story("Verify User can add Products to Cart then Checkout")
@Listeners(utils.TestNGListners.class)
public class CheckoutTests extends BaseTest {

    JsonManager json;
    String jsonFilePathForTestData2 = "src/test/resources/TestDataJsonFiles/CheckoutTestData.json";

    //Test Data Preparation as Getting Products from Database
    @BeforeClass
    public void prepareTestDataOfProducts() throws IOException, ParseException, SQLException {
        json = new JsonManager(jsonFilePathForTestData2);
        String dbQuery = "SELECT Id,Name,Category,Subcategory,Price,Quantity,TotalPrice FROM automationexercise.products Order by Id Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"ID", "Name", "Category", "Subcategory", "Price"
                ,"Quantity","TotalPrice"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Products";

        JDBCManager.setJsonFileFromDBForNestedArrayOfJsonObjects(dbQuery, jsonFilePathForTestData2, jsonKeysForUsers, jsonMainKeyForUsers);
    }

    //Test Data Preparation as Getting User Details from Database
    @BeforeClass
    public void prepareTestDataOfUserDetails() throws IOException, ParseException, SQLException {
        json = new JsonManager(jsonFilePathForTestData2);
        String dbQuery = "SELECT Title,Username,Email,Password,DayofBirth,MonthofBirth,YearofBirth,FirstName,LastName,Company,Address1,Address2,Country,State,City,ZipCode,MobileNumber FROM automationexercise.users Order By Username Asc;";

        //JsonKeys shall be filled by the same order of table columns of database query
        String[] jsonKeysForUsers = {"Title", "Name", "Email", "Password", "DayofBirth","MonthofBirth"
                ,"YearofBirth","FirstName", "LastName", "Company", "Address1", "Address2","Country","State"
                ,"City","ZipCode","MobileNumber"};

        //In Case of writing one JsonMainKey for all records, the Records will represent an array of Json objects
        //In Case of writing JsonMainKey for every record, Each Record will represent an object value for the corresponding JsonMainKey,In this case JsonMainKeys shall be filled by the same order of table rows on database
        String jsonMainKeyForUsers = "Users";

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

    //Test Script
    @Description("Search For Product On GUI then Add Products To Cart Then Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void checkoutProductsOnGUI() throws IOException, ParseException {
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
        // Open Cart Page and Proceed to Checkout Page On GUI
                .viewCart()
                .proceedToCheckOut()
                .verifyCheckOutPageIsOpened(json.getData("PageTitles[0].CheckoutPage"))
        // The Data Validation on Product Details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[3].Name")
                        ,json.getData("Products[3].Price")
                        ,json.getData("Products[3].Quantity")
                        ,json.getData("Products[3].TotalPrice"))
        // The Data Validation on Address Details is done on GUI Layer
                .verifyAddressDetails(json.getData("Users[0].FirstName"),
                        json.getData("Users[0].LastName"),json.getData("Users[0].Company"),
                        json.getData("Users[0].Address1"), json.getData("Users[0].Address2"),
                        json.getData("Users[0].State"), json.getData("Users[0].City"),
                        json.getData("Users[0].ZipCode"), json.getData("Users[0].Country"),
                        json.getData("Users[0].MobileNumber"))
        // Validate The Total Price of All Products On GUI Layer
                .verifyTotalPriceOfAllProducts()
        // Proceed to Payment Page and Perform Payment On GUI Layer
                .placeOrder()
                .verifyPaymentPageIsOpened(json.getData("PageTitles[0].PaymentPage"))
                .performPayment(json.getData("CreditCards[0].Name"),json.getData("CreditCards[0].Number"),
                        json.getData("CreditCards[0].CVC"),json.getData("CreditCards[0].ExpiryMonth")
                        ,json.getData("CreditCards[0].ExpiryYear"))
        // Assert On Order Confirm Message
                .assertOrderConfirmMessage(json.getData("Messages[0].OrderPlaced"));
    }

    @Description("Search For Product On API then Add Products To Cart Then Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void checkoutProductsOnAPI() throws IOException, ParseException {
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

    //Get User Details On API Layer
        var userDetailsResponse = new UserDetailsRequestModel()
                .setRequestParameterByUserEmail(json.getData("Users[0].Email"))
                .sendRequestGetUserDetails()
                .validateResponseCodeFromResponse(200)
    // The Data Validation on Address Details is done on API Layer
                .validateUserNameFromResponse(json.getData("Users[0].Name"))
                .validateUserEmailFromResponse(json.getData("Users[0].Email"))
                .validateUserTitleFromResponse(json.getData("Users[0].Title"))
                .validateUserFirstNameFromResponse(json.getData("Users[0].FirstName"))
                .validateUserLastNameFromResponse(json.getData("Users[0].LastName"))
                .validateUserCompanyFromResponse(json.getData("Users[0].Company"))
                .validateUserAddress1FromResponse(json.getData("Users[0].Address1"))
                .validateUserAddress2FromResponse(json.getData("Users[0].Address2"))
                .validateUserCountryFromResponse(json.getData("Users[0].Country"))
                .validateUserStateFromResponse(json.getData("Users[0].State"))
                .validateUserCityFromResponse(json.getData("Users[0].City"))
                .validateUserZipcodeFromResponse(json.getData("Users[0].ZipCode"))
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
        // Open Cart Page and Proceed to Checkout Page On GUI
                .viewCart()
                .proceedToCheckOut()
                .verifyCheckOutPageIsOpened(json.getData("PageTitles[0].CheckoutPage"))
        // Validate The Total Price of All Products On GUI Layer
                .verifyTotalPriceOfAllProducts()
        // Proceed to Payment Page and Perform Payment On GUI Layer
                .placeOrder()
                .verifyPaymentPageIsOpened(json.getData("PageTitles[0].PaymentPage"))
                .performPayment(json.getData("CreditCards[0].Name"),json.getData("CreditCards[0].Number"),
                        json.getData("CreditCards[0].CVC"),json.getData("CreditCards[0].ExpiryMonth")
                        ,json.getData("CreditCards[0].ExpiryYear"))
        // Assert On Order Confirm Message
                .assertOrderConfirmMessage(json.getData("Messages[0].OrderPlaced"));
    }
}
