package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;
import utils.WebElementsActionBot;

import java.io.IOException;
import java.util.List;

public class CartPage extends HomePage {
    //Variables

    //Locators
    By checkOutButton = By.partialLinkText("Checkout");
    By productNameLocator;
    By productPriceLocator;
    By productQuantityLocator;
    By productTotalPriceLocator;
    By removeProductFromCartButton;
    By removeAllProductsFromCartButton = By.className("cart_quantity_delete");

    //Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    private void setLocatorsByProductName(String productName)
    {
        productNameLocator = By.xpath("//td[contains(.,'"+productName+"')]");
        productPriceLocator = RelativeLocator.with(By.className("cart_price")).toRightOf(productNameLocator);
        productQuantityLocator = RelativeLocator.with(By.tagName("button")).toRightOf(productNameLocator);
        productTotalPriceLocator = RelativeLocator.with(By.className("cart_total_price")).toRightOf(productNameLocator);
        removeProductFromCartButton = RelativeLocator.with(By.className("cart_quantity_delete")).toRightOf(productNameLocator);
    }

    @Step("Proceed to CheckOut")
    public CheckoutPage proceedToCheckOut() throws IOException {
        bot.press(checkOutButton);
        return new CheckoutPage(driver);
    }

    @Step("Remove Product from Cart")
    public CartPage removeProductFromCart(String productName) throws IOException {
        setLocatorsByProductName(productName);
        bot.press(removeProductFromCartButton);
        return this;
    }

    @Step("Remove All Old Products from Cart")
    public CartPage removeAllOldProductsFromCart() throws IOException {
        List<WebElement> elements = bot.getAllMatchedElements(removeAllProductsFromCartButton);
        for (WebElement element : elements) {
            bot.press(element);
        }
        return this;
    }

    //Validations
    @Step("Verify Cart Page Is Opened")
    public CartPage verifyCartPageIsOpened(String pageTitle) throws IOException {
        verifyCartPageTitle(pageTitle);
        return this;
    }

    @Step("Verify All Product Details")
    public CartPage verifyAllProductDetails(String productName,String price,String quantity,String totalPrice) throws IOException {
        verifyProductName(productName).
                verifyProductPrice(productName,price).
                verifyProductQuantity(productName,quantity).
                verifyProductTotalPrice(productName,totalPrice);
        return this;
    }

    @Step("Verify Product is Added to Cart")
    public CartPage verifyProductIsAddedToCart(String productName) {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(productNameLocator));
        return this;
    }

    @Step("Verify Product is Removed from Cart")
    public CartPage verifyProductIsRemovedFromCart(String productName) {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertFalse(bot.isElementDisplayed(productNameLocator));
        return this;
    }

    //Private Methods
    @Step("Verify Product Name")
    private CartPage verifyProductName(String productName) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productNameLocator).contains(productName)
        );
        return this;
    }

    @Step("Verify Product Price")
    private CartPage verifyProductPrice(String productName,String productPrice) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertEquals(
                bot.readText(productPriceLocator),productPrice
        );
        return this;
    }

    @Step("Verify Product Quantity")
    private CartPage verifyProductQuantity(String productName,String productQuantity) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertEquals(
                bot.readText(productQuantityLocator),productQuantity
        );
        return this;
    }

    @Step("Verify Product Total Price")
    private CartPage verifyProductTotalPrice(String productName,String totalPrice) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productTotalPriceLocator).contains(totalPrice)
        );
        return this;
    }

    @Step("Verify Cart Page Title")
    private CartPage verifyCartPageTitle(String title) throws IOException {
        CustomSoftAssert.softAssert.assertEquals(bot.getPageTitle(),title);
        return this;
    }

}
