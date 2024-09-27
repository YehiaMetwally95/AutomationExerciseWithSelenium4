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
    By addToCartButton;
    By productNameLocator;
    By viewProductButtonLocator;

    //Constructor
    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    private void setLocatorsByProductName(String productName)
    {
        productNameLocator = By.xpath("//div[contains(@class,'overlay-content')]/p[.='"+productName+"']");
        viewProductButtonLocator = RelativeLocator.with(By.partialLinkText("View Product")).below(productNameLocator);
        addToCartButton = RelativeLocator.with(By.partialLinkText("Add to cart")).below(productNameLocator);
    }

    @Step("Search For A Product")
    public ProductsPage searchForProduct(String productName) throws IOException {
        setLocatorsByProductName(productName);
        bot.
                type(searchTextBox,productName).
                press(searchButton);
        return this;
    }

    @Step("Open Product Details Page")
    public ProductDetailsPage openProductDetailsPage(String productName) throws IOException {
        setLocatorsByProductName(productName);
        bot.press(viewProductButtonLocator);
        return new ProductDetailsPage(driver);
    }

    @Step("Add Product To Cart")
    public ProductsPage addProductToCart(String productName) throws IOException {
        setLocatorsByProductName(productName);
        bot.press(addToCartButton);
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
    public ProductsPage verifyProductPageIsOpened() {
        verifyProductsHeaderDisplayed();
        return this;
    }

    @Step("Verify Searched Products")
    public ProductsPage verifySearchedProduct(String productName) {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(productNameLocator));
        return this;
    }

    //Private Methods
    @Step("Verify Products Header Displayed")
    private ProductsPage verifyProductsHeaderDisplayed() {
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(allProductsHeaderLocator));
        return this;
    }
}
