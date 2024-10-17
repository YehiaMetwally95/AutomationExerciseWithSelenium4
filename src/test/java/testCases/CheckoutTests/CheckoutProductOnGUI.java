package testCases.CheckoutTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
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
public class CheckoutProductOnGUI extends BaseTest {
    String jsonFilePathForCheckout = "src/test/resources/TestDataJsonFiles/CheckoutTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForCheckout);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser4).applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test Script
    @Description("Search For Product On GUI then Add Products To Cart Then Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void checkoutProductsOnGUI() throws IOException, ParseException {
        new HomePage(getDriver(isolatedDriver))
                .verifyHomePageIsOpened()
        // Search for Products and Add them to Cart On GUI Layer
                .openProductsPage()
                .searchForProduct(json.getData("Products[1].Name"))
                .openProductDetailsPage(json.getData("Products[1].Name"))
                .setProductQuantity(json.getData("Products[1].Quantity"))
                .addProductToCart()
                .continueShopping()

                .openProductsPage()
                .searchForProduct(json.getData("Products[2].Name"))
                .openProductDetailsPage(json.getData("Products[2].Name"))
                .setProductQuantity(json.getData("Products[2].Quantity"))
                .addProductToCart()
                .continueShopping()

                .openProductsPage()
                .searchForProduct(json.getData("Products[3].Name"))
                .openProductDetailsPage(json.getData("Products[3].Name"))
                .setProductQuantity(json.getData("Products[3].Quantity"))
                .addProductToCart()
        // Open Cart Page and Proceed to Checkout Page On GUI
                .viewCart()
                .proceedToCheckOut()
                .verifyCheckOutPageIsOpened(json.getData("Messages.ReviewOrderHeader"))
        // The Data Validation on Product Details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[1].Name")
                        ,json.getData("Products[1].Price")
                        ,json.getData("Products[1].Quantity")
                        ,json.getData("Products[1].TotalPrice"))
                .verifyAllProductDetails(json.getData("Products[2].Name")
                        ,json.getData("Products[2].Price")
                        ,json.getData("Products[2].Quantity")
                        ,json.getData("Products[2].TotalPrice"))
                .verifyAllProductDetails(json.getData("Products[3].Name")
                        ,json.getData("Products[3].Price")
                        ,json.getData("Products[3].Quantity")
                        ,json.getData("Products[3].TotalPrice"))
        // The Data Validation on Address Details is done on GUI Layer
                .verifyAddressDetails(json.getData("Users[4].FirstName"),
                        json.getData("Users[4].LastName"),json.getData("Users[4].Company"),
                        json.getData("Users[4].Address1"), json.getData("Users[4].Address2"),
                        json.getData("Users[4].State"), json.getData("Users[4].City"),
                        json.getData("Users[4].ZipCode"), json.getData("Users[4].Country"),
                        json.getData("Users[4].MobileNumber"))
        // Validate The Total Price of All Products On GUI Layer
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
