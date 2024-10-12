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

import static utils.ThreadDriver.getIsolatedDriver;

@Epic("Automation Exercise Features")
@Feature("AddToCart")
@Story("Verify User can add Products to Cart")
@Listeners(TestNGListners.class)
public class AddtoCartWithoutSearch extends BaseTest {
    String jsonFilePathForAddToCart = "src/test/resources/TestDataJsonFiles/AddToCartTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForAddToCart);

    //Test Scripts
    @Description("Search for Product On GUI then Add Product To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void addProductToCartWithSearchOnGUI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        new HomePage(driver)
                .verifyHomePageIsOpened()
                .openProductsPage()
                .verifyProductPageIsOpened()
                // Add Products to Cart Directly from Products Page
                .addProductToCart(json.getData("Products[0].Name"))
                .continueShopping()
                .addProductToCart(json.getData("Products[1].Name"))
                .continueShopping()
                .addProductToCart(json.getData("Products[2].Name"))
                .continueShopping()
                .addProductToCart(json.getData("Products[3].Name"))
                .continueShopping()
                .addProductToCart(json.getData("Products[4].Name"))
                // Open Cart Page and Validate Products are Added to Cart On GUI
                .viewCart()
                .verifyProductIsAddedToCart(json.getData("Products[0].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[1].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[2].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[3].Name"))
                .verifyProductIsAddedToCart(json.getData("Products[4].Name"))
                // The Data Validation on product details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[0].Name")
                        ,json.getData("Products[0].Price")
                        ,json.getData("Products[0].Quantity")
                        ,json.getData("Products[0].TotalPrice"))
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
                .verifyAllProductDetails(json.getData("Products[4].Name")
                        ,json.getData("Products[4].Price")
                        ,json.getData("Products[4].Quantity")
                        ,json.getData("Products[4].TotalPrice"));
    }
}
