package testCasesForWebApp.SearchProductTests;

import baseTest.BaseTest;
import baseTest.BaseTestForWebApp;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.HomePage;
import yehiaEngine.managers.JsonManager;

import java.io.IOException;

import static yehiaEngine.driverManager.AppiumFactory.getDriver;

@Epic("Automation Exercise Features")
@Feature("Product Search")
@Story("Verify User can Search for any Product")
public class SearchForProductOnGUI extends BaseTestForWebApp {
    String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForSearchProduct);

    @Description("Search For Product And Open Product Page On GUI")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnGUI() throws IOException {
        new HomePage(getDriver(isolatedDriver))
                .openProductsPage()
                .verifyProductPageIsOpened(json.getData("Messages.AllProductsHeader"))
                // Search For Product is done On GUI Layer
                .searchForProduct(json.getData("Products[2].Name"))
                .verifySearchedProduct(json.getData("Products[2].Name"))
                // Open Product Details Page On GUI
                .openProductDetailsPage(json.getData("Products[2].Name"))
                .verifyProductDetailsPageIsOpened(json.getData("Messages.ReviewSection"))
                // The Data Validation on product details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[2].Name")
                        ,json.getData("Products[2].Price"),json.getData("Products[2].Availability")
                        ,json.getData("Products[2].Condition"),json.getData("Products[2].Brand")
                        ,json.getData("Products[2].Category"),json.getData("Products[2].Subcategory"));
    }

}
