package testCases.AddToCartTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
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
public class AddToCartWithSearchOnGUI extends BaseTest {
    String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForAddToCart);

    //Apply Saved Cookies to Current Session to ByPass Login
    @BeforeMethod
    public void byPassLogin() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new SessionManager(driver, jsonFilePathForSessionDataUser1).applyCookiesToCurrentSession();
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
    @Description("Search for Product On GUI then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnGUI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                // Search for Products and Add them to Cart On GUI Layer
                .openProductsPage()
                .searchForProduct(json.getData("Products[1].Name"))
                .openProductDetailsPage(json.getData("Products[1].Name"))
                .setProductQuantity(json.getData("Products[1].Quantity"))
                .addProductToCart()
                .continueShopping()

                .openProductsPage()
                .searchForProduct(json.getData("Products[4].Name"))
                .openProductDetailsPage(json.getData("Products[4].Name"))
                .setProductQuantity(json.getData("Products[4].Quantity"))
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
}
