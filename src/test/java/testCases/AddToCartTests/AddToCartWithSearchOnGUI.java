package testCases.AddToCartTests;

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
@Feature("AddToCart")
@Story("Verify User can add Products to Cart")
@Listeners(TestNGListners.class)
public class AddToCartWithSearchOnGUI extends BaseTest {
    String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForAddToCart);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        new SessionManager(getDriver(isolatedDriver), jsonFilePathForSessionDataUser1).applyCookiesToCurrentSession();
    }

    //Reset Cart and Remove any old products
    @BeforeMethod (dependsOnMethods = "byPassLogin")
    public void resetCart() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openCartPage()
                .removeAllOldProductsFromCart();
    }

    //Test Scripts
    @Description("Search for Product On GUI then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnGUI() throws IOException, ParseException {
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
        // Open Cart Page and Validate Products are Added to Cart On GUI
                .viewCart()
                .verifyProductIsAddedToCart(json.getData("Products[1].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[2].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[3].Name"))
        // The Data Validation on product details is done on GUI Layer
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
                        ,json.getData("Products[3].TotalPrice"));
    }
}
