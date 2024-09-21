package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.CustomSoftAssert;

import java.io.IOException;
import java.util.List;

public class CheckoutPage extends HomePage{
    //Variables

    //Locators
    By productNameLocator;
    By productPriceLocator;
    By productQuantityLocator;
    By productTotalPriceLocator;
    By totalPriceTextLocator = By.xpath("//b[contains(.,'Total')]");
    By totalPriceLocator = RelativeLocator.with(By.tagName("p")).toRightOf(totalPriceTextLocator);
    By deliveryAddressBox = By.id("address_delivery");
    By fullNameLocator = RelativeLocator.with(By.tagName("li")).below(deliveryAddressBox).below(deliveryAddressBox);
    By companyLocator = RelativeLocator.with(By.tagName("li")).below(fullNameLocator);
    By address1Locator = RelativeLocator.with(By.tagName("li")).below(companyLocator);
    By address2Locator = RelativeLocator.with(By.tagName("li")).below(address1Locator);
    By stateCityLocator = RelativeLocator.with(By.tagName("li")).below(address2Locator);
    By countryLocator = RelativeLocator.with(By.tagName("li")).below(stateCityLocator);
    By mobileNumberLocator = RelativeLocator.with(By.tagName("li")).below(countryLocator);
    By productPriceLocator2 = By.xpath("//tr[contains(@id,'product')]/descendant::p[@class='cart_total_price']");

    By placeOrderButton = By.partialLinkText("Order");

    //Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    private void setLocatorsByProductName(String productName)
    {
        By productNameLocator = By.partialLinkText(productName);
        By productPriceLocator = RelativeLocator.with(By.tagName("p")).toRightOf(productNameLocator);
        By productQuantityLocator = RelativeLocator.with(By.tagName("button")).toRightOf(productPriceLocator);
        By productTotalPriceLocator = RelativeLocator.with(By.tagName("p")).toRightOf(productQuantityLocator);
    }

    @Step("Place Order")
    public PaymentPage placeOrder() throws IOException {
        bot.press(placeOrderButton);
        return new PaymentPage(driver);
    }

    //Validations
    @Step("Verify CheckOut Page Is Opened")
    public CheckoutPage verifyCheckOutPageIsOpened(String pageTitle) throws IOException {
        verifyCheckOutPageTitle(pageTitle);
        return this;
    }

    @Step("Verify All Product Details")
    public CheckoutPage verifyAllProductDetails(String productName,String price,String quantity) throws IOException {
        verifyProductName(productName).
                verifyProductPrice(productName,price).
                verifyProductQuantity(productName,quantity).
                verifyProductTotalPrice(productName);
        return this;
    }

    @Step("Verify Total Price of Products")
    public CheckoutPage verifyTotalPriceOfProducts() throws IOException {
        int total = 0;
        String totalPrice;
        List<WebElement> elements = bot.getAllMatchedElements(productPriceLocator2);
        for (WebElement element : elements) {
            total = total + Integer.parseInt(element.getText().split("Rs. ",2)[1]);
        }
        totalPrice = Integer.toString(total);
        CustomSoftAssert.softAssert.assertTrue(bot.readText(totalPriceLocator).contains(totalPrice));
        return this;
    }

    @Step("Verify Address Details")
    public CheckoutPage verifyAddressDetails(String firstName,String lastName,String company,
                                             String address1,String address2,String state,String city,
                                             String zipCode,String country,String mobileNumber) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(bot.readText(fullNameLocator).contains(firstName));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(fullNameLocator).contains(lastName));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(companyLocator).contains(company));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(address1Locator).contains(address1));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(address2Locator).contains(address2));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(state));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(city));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(zipCode));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(countryLocator).contains(country));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(mobileNumberLocator).contains(mobileNumber));
        return this;
    }

    @Step("Verify Product is Added to Cart")
    public CheckoutPage verifyProductIsAddedToCart(String productName) {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(productNameLocator));
        return this;
    }

    @Step("Verify Product is Removed from Cart")
    public CheckoutPage verifyProductIsRemovedFromCart(String productName) {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertFalse(bot.isElementDisplayed(productNameLocator));
        return this;
    }

    //Private Methods
    @Step("Verify CheckOut Page Title")
    private CheckoutPage verifyCheckOutPageTitle(String title) throws IOException {
        CustomSoftAssert.softAssert.assertTrue(bot.getPageTitle().contains(title));
        return this;
    }

    @Step("Verify Product Name")
    private CheckoutPage verifyProductName(String productName) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productNameLocator).contains(productName)
        );
        return this;
    }

    @Step("Verify Product Price")
    private CheckoutPage verifyProductPrice(String productName,String productPrice) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productPriceLocator).contains(productPrice)
        );
        return this;
    }

    @Step("Verify Product Quantity")
    private CheckoutPage verifyProductQuantity(String productName,String productQuantity) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productQuantityLocator).contains(productQuantity)
        );
        return this;
    }

    @Step("Verify Product Total Price")
    private CheckoutPage verifyProductTotalPrice(String productName) throws IOException {
        setLocatorsByProductName(productName);
        int price = Integer.parseInt(bot.readText(productPriceLocator).split("Rs. ",2)[1]);
        int quantity = Integer.parseInt(bot.readText(productQuantityLocator));
        String totalPrice = Integer.toString(price*quantity);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productTotalPriceLocator).contains(totalPrice)
        );
        return this;
    }

}
