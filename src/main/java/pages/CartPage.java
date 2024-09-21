package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;
import utils.WebElementsActionBot;

import java.io.IOException;

public class CartPage extends HomePage {
    //Variables

    //Locators
    By checkOutButton = By.partialLinkText("Checkout");
    By productNameLocator;
    By productPriceLocator;
    By productQuantityLocator;
    By productTotalPriceLocator;
    By removeProductFromCartButton;

    //Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    private void setLocatorsByProductName(String productName)
    {
        By productNameLocator = By.partialLinkText(productName);
        By productPriceLocator = RelativeLocator.with(By.tagName("p")).toRightOf(productNameLocator);
        By productQuantityLocator = RelativeLocator.with(By.tagName("button")).toRightOf(productPriceLocator);
        By productTotalPriceLocator = RelativeLocator.with(By.tagName("p")).toRightOf(productQuantityLocator);
        By removeProductFromCartButton = RelativeLocator.with(By.tagName("a")).toRightOf(productTotalPriceLocator);
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

    //Validations
    @Step("Verify Cart Page Is Opened")
    public CartPage verifyCartPageIsOpened(String pageTitle) throws IOException {
        verifyCartPageTitle(pageTitle);
        return this;
    }

    @Step("Verify All Product Details")
    public CartPage verifyAllProductDetails(String productName,String price,String quantity) throws IOException {
        verifyProductName(productName).
                verifyProductPrice(productName,price).
                verifyProductQuantity(productName,quantity).
                verifyProductTotalPrice(productName);
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
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productPriceLocator).contains(productPrice)
        );
        return this;
    }

    @Step("Verify Product Quantity")
    private CartPage verifyProductQuantity(String productName,String productQuantity) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productQuantityLocator).contains(productQuantity)
        );
        return this;
    }

    @Step("Verify Product Total Price")
    private CartPage verifyProductTotalPrice(String productName) throws IOException {
        setLocatorsByProductName(productName);
        int price = Integer.parseInt(bot.readText(productPriceLocator).split("Rs. ",2)[1]);
        int quantity = Integer.parseInt(bot.readText(productQuantityLocator));
        String totalPrice = Integer.toString(price*quantity);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productTotalPriceLocator).contains(totalPrice)
        );
        return this;
    }

    @Step("Verify Cart Page Title")
    private CartPage verifyCartPageTitle(String title) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(bot.getPageTitle().contains(title));
        return this;
    }

}
