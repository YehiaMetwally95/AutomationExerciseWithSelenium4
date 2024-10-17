package testCases.CheckoutTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import objectModelsForAPIs.UserDetailsRequestModel;
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
@Feature("Checkout")
@Story("Verify User can add Products to Cart then Checkout")
@Listeners(TestNGListners.class)
public class CheckoutProductOnAPI extends BaseTest {
    String jsonFilePathForCheckout = "src/test/resources/TestDataJsonFiles/CheckoutTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForCheckout);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser3)
                .applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test Script
    @Description("Search For Product On API then Add Products To Cart Then Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void checkoutProductsOnAPI() throws IOException, ParseException {
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

    //Get User Details On API Layer
        var userDetailsResponse = new UserDetailsRequestModel()
                .setRequestParameterByUserEmail(json.getData("Users[3].Email"))
                .sendRequestOfGetUserDetails()
                .validateResponseCodeFromResponse(200)
    // The Data Validation on Address Details is done on API Layer
                .validateAddressDetailsFromResponse(json.getData("Users[3].Name"),json.getData("Users[3].Title"),
                        json.getData("Users[3].FirstName"),json.getData("Users[3].LastName"),
                        json.getData("Users[3].Company"),json.getData("Users[3].Address1"),
                        json.getData("Users[3].Address2"),json.getData("Users[3].Country"),
                        json.getData("Users[3].State"),json.getData("Users[3].City"),
                        json.getData("Users[3].ZipCode"))
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
        // Open Cart Page and Proceed to Checkout Page On GUI
                .viewCart()
                .proceedToCheckOut()
                .verifyCheckOutPageIsOpened(json.getData("Messages.ReviewOrderHeader"))
        // Validate The Total Price of All Products On GUI Layer
                .assertProductIsAddedToCart(json.getData("Products[0].Name"))
                .assertProductIsAddedToCart(json.getData("Products[2].Name"))
                .assertProductIsAddedToCart(json.getData("Products[3].Name"))
                .verifyTotalPriceOfAllProducts()
        // Proceed to Payment Page and Perform Payment On GUI Layer
                .placeOrder()
                .verifyPaymentPageIsOpened(json.getData("Messages.PaymentHeader"))
                .performPayment(json.getData("CreditCards[0].Name"),json.getData("CreditCards[0].Number"),
                        json.getData("CreditCards[0].CVC"),json.getData("CreditCards[0].ExpiryMonth")
                        ,json.getData("CreditCards[0].ExpiryYear"))
        // Assert On Order Confirm Message
                .assertOrderConfirmMessage(json.getData("Messages.OrderPlaced"));
    }
}
