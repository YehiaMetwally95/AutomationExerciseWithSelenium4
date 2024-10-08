package testCases.SearchProductTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
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
public class SearchForProductOnAPI extends BaseTest {
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

    //Test Scripts
    @Description("Search For Product And Open Product Page On API")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnAPI() throws IOException, ParseException {
        WebDriver driver = getIsolatedDriver(threadDriver);
        // Search For Product is done On API Layer
        var searchProductResponse = new SearchProductRequestModel()
                .addProductNameToRequestBody(json.getData("Products[1].Name"))
                .sendRequestOfSearchForProduct()
                .validateResponseCodeFromResponse(200)
        // The Data Validation on product details is done on API Layer
                .validateProductDetailsFromResponse(json.getData("Products[1].ID"),json.getData("Products[1].Name"),
                        json.getData("Products[1].Price"),json.getData("Products[1].Brand"),
                        json.getData("Products[1].Category"),json.getData("Products[1].Subcategory"))
                .getResponsePojoObject();

        // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(driver)
                .openProductDetailsPageByUrl(searchProductResponse.getProducts().get(0).getId())
                .verifyProductDetailsPageIsOpened(json.getData("PageTitles[0].ProductDetailsPage"))
        // The Data Validation Again on product details But is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[1].Name")
                ,json.getData("Products[1].Price"),json.getData("Products[1].Availability")
                ,json.getData("Products[1].Condition"),json.getData("Products[1].Brand")
                ,json.getData("Products[1].Category"),json.getData("Products[1].Subcategory"));
    }
}
