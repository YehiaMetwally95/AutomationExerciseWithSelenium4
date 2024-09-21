package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;
import utils.CustomSoftAssert;
import utils.WebElementsActionBot;

import java.io.IOException;

public class HomePage {
    //Variables
    WebDriver driver;
    WebElementsActionBot bot;

    //Locators of Header
    By logoButton = By.xpath("//div[contains(@class,'logo')]/descendant::img");
    By homePageButton = RelativeLocator.with(By.tagName("a")).toRightOf(logoButton);
    By productsPageButton = By.partialLinkText("Products");
    By cartPageButton = By.partialLinkText("Cart");
    By signupLoginPageButton = By.partialLinkText("Login");
    By contactusPageButton = By.partialLinkText("Contact us");
    By logOutButton = By.partialLinkText("Logout");
    By deleteButton = By.partialLinkText("Delete Account");
    By loggedInButton = By.partialLinkText("Logged in");

    //Locators of Footer
    By subscriptionText = By.id("susbscribe_email");
    By subscriptionButton = By.id("subscribe");
    By subscriptionMassage = By.cssSelector("#success-subscribe .alert-success");

    //Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        bot = new WebElementsActionBot(driver);
    }

    //Actions
    @Step("Open Home Page")
    public HomePage openHomePage() throws IOException {
        bot.press(homePageButton);
        return this;
    }

    @Step("Open Products Page")
    public ProductsPage openProductsPage() throws IOException {
        bot.press(productsPageButton);
        return new ProductsPage(driver);
    }

    @Step("Open Cart Page")
    public CartPage openCartPage() throws IOException {
        bot.press(cartPageButton);
        return new CartPage(driver);
    }

    @Step("Open Login/Signup Page")
    public LoginSignupPage openLoginSignupPage() throws IOException {
        bot.press(signupLoginPageButton);
        return new LoginSignupPage(driver);
    }

    @Step("Open ContactUs Page")
    public ContactusPage openContactUsPage() throws IOException {
        bot.press(contactusPageButton);
        return new ContactusPage(driver);
    }

    @Step("Click On Logout")
    public LoginSignupPage logout() throws IOException {
        bot.press(logOutButton);
        return new LoginSignupPage(driver);
    }

    @Step("Click On Delete Account")
    public DeleteAccountPage deleteAccount() throws IOException {
        bot.press(deleteButton);
        return new DeleteAccountPage(driver);
    }

    @Step("Submit Subscription")
    public HomePage submitEmailSubscription(String email) throws IOException {
        bot.scrollToElementIntoView(subscriptionText).
                type(subscriptionText,email).
                press(subscriptionButton);
        return this;
    }

    //Validations
    @Step("Verify Home Page is Opened")
    public HomePage verifyHomePageIsOpened() throws IOException {
        verifySiteLogoIsDisplayed();
        return this;
    }

    @Step("Assert User is Logged In")
    public HomePage assertUserIsLoggedIn(String username) throws IOException {
        Assert.assertTrue(
                bot.readText(loggedInButton).contains(username)
        );
        return this;
    }

    @Step("Assert Subscription Massage")
    public HomePage assertSubscriptionMassage(String massage) throws IOException {
        Assert.assertEquals(
                bot.readText(subscriptionMassage)
                ,massage
        );
        return this;
    }

    //Private Methods
    @Step("Verify Site Logo")
    private HomePage verifySiteLogoIsDisplayed() throws IOException {
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(logoButton));
        return this;
    }
}
