package testCases.AddToCartTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import prepareTestData.TestNGListners;
import utils.JsonManager;
import utils.SessionManager;

import java.io.IOException;

import static utils.ThreadDriver.getDriver;

@Epic("Automation Exercise Features")
@Feature("AddToCart")
@Story("Verify User can add Products to Cart")
@Listeners(TestNGListners.class)
public class AddToCartWithSearchOnAPI extends BaseTest {
    String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForAddToCart);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser0).applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    @Description("Search for Product On API then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnAPI() throws IOException, ParseException {
    // Search for Products On API Layer
        var searchProductResponse1 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[0].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse2 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[2].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
                .getResponsePojoObject();

        var searchProductResponse3 = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[3].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
    // The Data Validation on product details is done on API Layer
                .validateProductDetailsFromResponse(json.getData("Products[3].ID"),json.getData("Products[3].Name"),
                        json.getData("Products[3].Price"),json.getData("Products[3].Brand"),
                        json.getData("Products[3].Category"),json.getData("Products[3].Subcategory"),
                        json.getData("Products[3].Quantity"),json.getData("Products[3].TotalPrice"))
                .getResponsePojoObject();

   // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(getDriver(isolatedDriver))
                .openProductDetailsPageByUrl(searchProductResponse1.getProducts().get(0).getId())
                .setProductQuantity(json.getData("Products[0].Quantity"))
                .addProductToCart()
                .continueShopping()
                .openProductDetailsPageByUrl(searchProductResponse2.getProducts().get(0).getId())
                .setProductQuantity(json.getData("Products[2].Quantity"))
                .addProductToCart()
                .continueShopping().openProductDetailsPageByUrl(searchProductResponse3.getProducts().get(0).getId())
                .setProductQuantity(json.getData("Products[3].Quantity"))
                .addProductToCart()
    // Open Cart Page and Validate Products are Added to Cart On GUI
                .viewCart()
                .verifyCartPageIsOpened(json.getData("Messages.ShoppingCartHeader"))
                .assertProductIsAddedToCart(json.getData("Products[0].Name"))
                .assertProductIsAddedToCart(json.getData("Products[2].Name"))
                .assertProductIsAddedToCart(json.getData("Products[3].Name"));
    }
}
