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
    By totalPriceText = By.xpath("//b[.='Total Amount']");
    //By totalPriceLocator = RelativeLocator.with(By.className("cart_total_price")).toRightOf(totalPriceText);
    By totalPriceLocator = By.xpath("//h4[.='Total Amount']/parent::td/following-sibling::td/p[@class='cart_total_price']");
    By addressTitleLocator = By.className("address_title");
    //By fullNameLocator = RelativeLocator.with(By.className("address_firstname")).below(addressTitleLocator);
    By fullNameLocator = By.cssSelector("#address_delivery .address_firstname");
    //By companyLocator = RelativeLocator.with(By.className("address_address1")).below(addressTitleLocator);
    By companyLocator = By.xpath("(//ul[@id='address_delivery']/li[contains(@class,'address_address1')])[1]");
    By address1Locator = By.xpath("(//ul[@id='address_delivery']/li[contains(@class,'address_address1')])[2]");
    By address2Locator = By.xpath("(//ul[@id='address_delivery']/li[contains(@class,'address_address1')])[3]");
    //By stateCityLocator = RelativeLocator.with(By.className("address_city")).below(addressTitleLocator);
    By stateCityLocator = By.cssSelector("#address_delivery .address_city");
    //By countryLocator = RelativeLocator.with(By.className("address_country_name")).below(addressTitleLocator);
    By countryLocator = By.cssSelector("#address_delivery .address_country_name");
    //By mobileNumberLocator = RelativeLocator.with(By.className("address_phone")).below(addressTitleLocator);
    By mobileNumberLocator = By.cssSelector("#address_delivery .address_phone");
    By productPriceLocator2 = By.xpath("//tr[contains(@id,'product')]/descendant::p[@class='cart_total_price']");

    By placeOrderButton = By.xpath("//a[contains(@class,'check_out')]");

    //Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    private void setLocatorsByProductName(String productName)
    {
        productNameLocator = By.xpath("//td[contains(.,'"+productName+"')]");
        //productPriceLocator = RelativeLocator.with(By.className("cart_price")).toRightOf(productNameLocator);
        productPriceLocator = By.xpath("//td[contains(.,'"+productName+"')]/following-sibling::td[@class='cart_price']");
        //productQuantityLocator = RelativeLocator.with(By.tagName("button")).toRightOf(productNameLocator);
        productQuantityLocator = By.xpath("//td[contains(.,'"+productName+"')]/following-sibling::td[@class='cart_quantity']/button");
        //productTotalPriceLocator = RelativeLocator.with(By.className("cart_total_price")).toRightOf(productNameLocator);
        productTotalPriceLocator = By.xpath("//td[contains(.,'"+productName+"')]/following-sibling::td[@class='cart_total']/p");
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
    public CheckoutPage verifyAllProductDetails(String productName,String price,String quantity,String totalPrice) throws IOException {
        verifyProductName(productName).
                verifyProductPrice(productName,price).
                verifyProductQuantity(productName,quantity).
                verifyProductTotalPrice(productName,totalPrice);
        return this;
    }

    @Step("Verify Total Price of All Products")
    public CheckoutPage verifyTotalPriceOfAllProducts() throws IOException {
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
        CustomSoftAssert.softAssert.assertEquals(bot.readText(companyLocator),company);
        CustomSoftAssert.softAssert.assertEquals(bot.readText(address1Locator),address1);
        CustomSoftAssert.softAssert.assertEquals(bot.readText(address2Locator),address2);
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(state));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(city));
        CustomSoftAssert.softAssert.assertTrue(bot.readText(stateCityLocator).contains(zipCode));
        CustomSoftAssert.softAssert.assertEquals(bot.readText(countryLocator),country);
        CustomSoftAssert.softAssert.assertEquals(bot.readText(mobileNumberLocator),mobileNumber);
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
    private CheckoutPage verifyProductTotalPrice(String productName,String totalPrice) throws IOException {
        setLocatorsByProductName(productName);
        CustomSoftAssert.softAssert.assertTrue(
                bot.readText(productTotalPriceLocator).contains(totalPrice)
        );
        return this;
    }

}
