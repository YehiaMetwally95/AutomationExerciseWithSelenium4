package testCases.AddToCartTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import prepareTestData.TestNGListners;
import utils.JDBCManager;
import utils.JsonManager;
import utils.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

import static utils.ThreadDriver.getIsolatedDriver;

@Epic("Automation Exercise Features")
@Feature("AddToCart")
@Story("Verify User can add Products to Cart")
@Listeners(TestNGListners.class)
public class RemoveFromCartWithSearchOnAPI extends BaseTest {
    String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForAddToCart);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new SessionManager(driver, jsonFilePathForSessionDataUser2).applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new HomePage(driver)
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test Scripts
    @Description("Search for Product On API then Remove Product From Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void removeProductFromCartWithSearchOnAPI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
    //Search for 3 Products On API
        var searchProductResponse1 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[0].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse2 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[4].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse3 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[3].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

    // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(driver)
                .openProductDetailsPageByUrl(searchProductResponse1.getProducts().get(0).getId())
                .setProductQuantity(json.getData("Products[0].Quantity"))
                .addProductToCart()
                .continueShopping()
                .openProductDetailsPageByUrl(searchProductResponse2.getProducts().get(0).getId())
                .setProductQuantity(json.getData("Products[4].Quantity"))
                .addProductToCart()
                .continueShopping().openProductDetailsPageByUrl(searchProductResponse3.getProducts().get(0).getId())
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
