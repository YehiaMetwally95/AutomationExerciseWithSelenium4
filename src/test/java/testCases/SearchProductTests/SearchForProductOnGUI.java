package testCases.SearchProductTests;

import baseTest.BaseTest;
import io.qameta.allure.*;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import prepareTestData.TestNGListners;
import utils.JsonManager;

import java.io.IOException;

import static utils.ThreadDriver.getDriver;

@Epic("Automation Exercise Features")
@Feature("Product Search")
@Story("Verify User can Search for any Product")
@Listeners(TestNGListners.class)
public class SearchForProductOnGUI extends BaseTest {
    String jsonFilePathForSearchProduct = "src/test/resources/TestDataJsonFiles/SearchProductTestData.json";
    JsonManager json = new JsonManager(jsonFilePathForSearchProduct);

    @Description("Search For Product And Open Product Page On GUI")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchForProductAndOpenProductPageOnGUI() throws IOException, ParseException {
        new HomePage(getDriver(isolatedDriver))
                .openProductsPage()
                .verifyProductPageIsOpened(json.getData("Messages.AllProductsHeader"))
                // Search For Product is done On GUI Layer
                .searchForProduct(json.getData("Products[0].Name"))
                .verifySearchedProduct(json.getData("Products[0].Name"))
                // Open Product Details Page On GUI
                .openProductDetailsPage(json.getData("Products[0].Name"))
                .verifyProductDetailsPageIsOpened(json.getData("Messages.ReviewSection"))
                // The Data Validation on product details is done on GUI Layer
                .verifyAllProductDetails(json.getData("Products[0].Name")
                        ,json.getData("Products[0].Price"),json.getData("Products[0].Availability")
                        ,json.getData("Products[0].Condition"),json.getData("Products[0].Brand")
                        ,json.getData("Products[0].Category"),json.getData("Products[0].Subcategory"));
    }

}
