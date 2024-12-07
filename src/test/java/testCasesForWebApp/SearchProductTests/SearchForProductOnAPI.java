package testCasesForWebApp.SearchProductTests;

import baseTest.BaseTest;
import baseTest.BaseTestForWebApp;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import objectModelsForAPIs.SearchProductRequestModel;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pagesForWebApp.HomePage;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

import static yehiaEngine.driverManager.AppiumFactory.getDriver;

@Epic("Automation Exercise Features")
@Feature("Product Search")
@Story("Verify User can Search for any Product")
public class SearchForProductOnAPI extends BaseTestForWebApp {
    String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForSearchProduct);

    //Test Scripts
    @Description("Search For Product And Open Product Page On API")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnAPI() throws IOException {
        // Search For Product is done On API Layer
        var searchProductResponse = new SearchProductRequestModel()
                .prepareSearchProductRequest(json.getData("Products[1].Name"))
                .sendSearchProductRequest()
                .validateResponseCodeFromResponse(200)
        // The Data Validation on product details is done on API Layer
                .validateProductDetailsFromResponse(json.getData("Products[1].ID"),json.getData("Products[1].Name"),
                        json.getData("Products[1].Price"),json.getData("Products[1].Brand"),
                        json.getData("Products[1].Category"),json.getData("Products[1].Subcategory"))
                .getResponsePojoObject();

        // Open Product Details Page By Url and Add them to Cart On GUI Layer
        new HomePage(getDriver(isolatedDriver))
                .openProductDetailsPageByUrl(searchProductResponse.getProducts().get(0).getId())
                .verifyProductDetailsPageIsOpened(json.getData("Messages.ReviewSection"))
        // The Data Validation Again on product details But is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[1].Name")
                ,json.getData("Products[1].Price"),json.getData("Products[1].Availability")
                ,json.getData("Products[1].Condition"),json.getData("Products[1].Brand")
                ,json.getData("Products[1].Category"),json.getData("Products[1].Subcategory"));
    }
}
