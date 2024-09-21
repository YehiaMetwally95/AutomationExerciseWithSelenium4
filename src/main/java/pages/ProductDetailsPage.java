package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;

import java.io.IOException;

public class ProductDetailsPage extends HomePage {
    //Variables

    //Locators
    By productNameLocator = By.cssSelector(".product-information h2");
    By productCategorizationLocator = By.xpath("//div[@class='product-details']/descendant::p[contains(.,'Category')]");
    By productPriceLocator = By.xpath("(//div[@class='product-details']/descendant::span[contains(.,'Rs')])[2]");
    By productAvailabilityLocator = By.xpath("//div[@class='product-details']/descendant::p[contains(.,'Availability')]");
    By productConditionLocator = By.xpath("//div[@class='product-details']/descendant::p[contains(.,'Condition')]");
    By productBrandLocator = By.xpath("//div[@class='product-details']/descendant::p[contains(.,'Brand')]");
    By productQuantityTextBox = By.id("quantity");
    By addToCartButton = RelativeLocator.with(By.tagName("button")).near(productQuantityTextBox);
    By continueShoppingButton = By.xpath("//button[@data-dismiss='modal']");
    By viewCartButton = By.xpath("//div[@id='cartModal']/descendant::a");

    //Constructor
    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    @Step("Add Product To Cart")
    public ProductDetailsPage addProductToCart() throws IOException {
        bot.press(addToCartButton);
        return this;
    }

    @Step("Click On Continue Shopping")
    public ProductDetailsPage continueShopping() throws IOException {
        bot.press(continueShoppingButton);
        return this;
    }

    @Step("Click On View Cart")
    public CartPage viewCart() throws IOException {
        bot.press(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Set Product Quantity")
    public ProductDetailsPage setProductQuantity(String quantity) throws IOException {
        bot.type(productQuantityTextBox,quantity);
        return this;
    }

    //Validations
    @Step("Verify All Product Details")
    private ProductDetailsPage verifyAllProductDetails(String name,String price,String availability,
                                                       String condition,String brand,String category,
                                                       String subCategory) throws IOException {
        verifyProductName(name).
                verifyProductPrice(price).
                verifyProductAvailability(availability).
                verifyProductCondition(condition).
                verifyProductBrand(brand).
                verifyProductCategory(category).
                verifyProductSubCategory(subCategory);
        return this;
    }

    @Step("Verify Product Details Page is Opened")
    public ProductDetailsPage verifyProductDetailsPageIsOpened(String pageTitle) {
        verifyProductPageTitle(pageTitle);
        return this;
    }

    //Private Methods
    @Step("Verify Product Name")
    private ProductDetailsPage verifyProductName(String productName) throws IOException {
        CustomSoftAssert.softAssert.assertEquals(
                bot.readText(productNameLocator),productName
        );
        return this;
    }

    @Step("Verify Product Price")
    private ProductDetailsPage verifyProductPrice(String price) throws IOException {
        CustomSoftAssert.softAssert.assertEquals(
                bot.readText(productPriceLocator),price
        );
        return this;
    }

    @Step("Verify Product Availability")
    private ProductDetailsPage verifyProductAvailability(String availability) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productAvailabilityLocator).contains(availability)
        );
        return this;
    }

    @Step("Verify Product Condition")
    private ProductDetailsPage verifyProductCondition(String condition) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productConditionLocator).contains(condition)
        );
        return this;    }

    @Step("Verify Product Brand")
    private ProductDetailsPage verifyProductBrand(String brand) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productBrandLocator).contains(brand)
        );
        return this;    }

    @Step("Verify Product Category")
    private ProductDetailsPage verifyProductCategory(String category) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productCategorizationLocator).contains(category)
        );
        return this;    }

    @Step("Verify Product SubCategory")
    private ProductDetailsPage verifyProductSubCategory(String subCategory) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productCategorizationLocator).contains(subCategory)
        );
        return this;
    }

    @Step("Verify Product Page Title")
    private ProductDetailsPage verifyProductPageTitle(String title) {
        CustomSoftAssert.softAssert.assertTrue(driver.getTitle().contains(title));
        return this;
    }

}
