package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;

import java.io.IOException;

public class ProductsPage extends HomePage{
    //Variables

    //Locators
    By allProductsHeaderLocator= By.xpath("//h2[contains(@class,'title')]");
    By searchTextBox = By.cssSelector(".container #search_product");
    By searchButton = By.cssSelector(".container #submit_search");
    By continueShoppingButton = By.xpath("//button[@data-dismiss='modal']");
    By viewCartButton = By.xpath("//div[@id='cartModal']/descendant::a");

    private By productNameInnerLocator (String productName)
    {
        return By.xpath("(//div[@class='features_items']/descendant::*[.='"+productName+"'])[2]");
    }

    private By productNameOuterLocator (String productName)
    {
        return By.xpath("(//div[@class='features_items']/descendant::*[.='"+productName+"'])[1]");
    }

    private By viewProductButtonLocator (String productName)
    {
        return By.linkText("View Product");
    }

    private By addToCartInnerLocator (String productName)
    {
        return RelativeLocator.with(By.tagName("a")).below(productNameInnerLocator(productName));
    }

    private By productImageOuterLocator (String productName)
    {
        return RelativeLocator.with(By.tagName("img")).above(productNameOuterLocator(productName));
    }

    //Constructor
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    @Step("Search For A Product")
    public ProductsPage searchForProduct(String productName) throws IOException {
        bot.
                type(searchTextBox,productName).
                press(searchButton);
        return this;
    }

    @Step("Open Product Details Page")
    public ProductDetailsPage openProductDetailsPage(String productName) throws IOException {
        bot.press(viewProductButtonLocator(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Add Product To Cart")
    public ProductsPage addProductToCart(String productName) throws IOException {
        bot.press(productImageOuterLocator(productName))
                .press(addToCartInnerLocator(productName));
        return this;
    }

    @Step("Click On Continue Shopping")
    public ProductsPage continueShopping() throws IOException {
        bot.press(continueShoppingButton);
        return this;
    }

    @Step("Click On View Cart")
    public CartPage viewCart() throws IOException {
        bot.press(viewCartButton);
        return new CartPage(driver);
    }

    //Validations
    @Step("Verify Product Page is Opened")
    public ProductsPage verifyProductPageIsOpened(String header) throws IOException {
        verifyProductsHeader(header);
        return this;
    }

    @Step("Verify Searched Products")
    public ProductsPage verifySearchedProduct(String productName) {
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(productImageOuterLocator(productName)));
        return this;
    }

    //Private Methods
    @Step("Verify Products Header")
    private ProductsPage verifyProductsHeader(String header) throws IOException {
        CustomSoftAssert.softAssert.assertEquals(bot.readText(allProductsHeaderLocator),header);
        return this;
    }
}
