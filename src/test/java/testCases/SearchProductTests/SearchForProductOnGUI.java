package testCases.SearchProductTests;

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
@Feature("Product Search")
@Story("Verify User can Search for any Product")
@Listeners(TestNGListners.class)
public class SearchForProductOnGUI extends BaseTest {
    String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForSearchProduct);

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

    @Description("Search For Product And Open Product Page On GUI")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnGUI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
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

}
