package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.CustomSoftAssert;

import java.io.IOException;

public class ContactusPage extends HomePage {
    //Variables

    //Locators
    By getInTouchHeaderLocator = By.cssSelector(".contact-form h2");
    By nameLocator = By.name("name");
    By emailLocator = By.name("email");
    By subjectLocator = By.name("subject");
    By messageLocator = By.name("message");
    By submitButton = By.name("submit");
    By successMassageLocator = By.cssSelector(".contact-form .alert-success");
    By homeButton = By.xpath("//div[@id='form-section']//a[.=' Home']");
    By uploadFileControl= By.name("upload_file");

    //Constructor
    public ContactusPage(WebDriver driver) {
        super(driver);
    }

    //Actions
    @Step("submitContactUsRequest")
    public ContactusPage submitContactUsRequest(String name, String email , String subject , String message, String filepath) throws IOException {
        enterContactDetails(name,email,subject,message,filepath)
                .clickOnSubmitButton();
        return this;
    }

    //Validations
    @Step("Verify Contact Us Page is Opened")
    public ContactusPage verifyContactUsPageIsOpened() throws IOException {
        verifyGetInTouchHeaderDisplayed();
        return this;
    }

    @Step("Assert Contact Success Massage")
    public ContactusPage assertContactSuccessMassage(String message) throws IOException {
        Assert.assertEquals(
                bot.readText(successMassageLocator)
                ,message
        );
        return this;
    }

    //Private Methods
    @Step("Enter Contact Details")
    private ContactusPage enterContactDetails(String name, String email , String subject , String message, String filepath) throws IOException {
        bot.
                type(nameLocator,name).
                type(emailLocator,email).
                type(subjectLocator,subject).
                type(messageLocator,message).
                type(uploadFileControl,filepath);
        return this;
    }

    @Step("Click on Submit Button")
    private ContactusPage clickOnSubmitButton() throws IOException {
        bot.
                press(submitButton).
                acceptAlert();
        return this;
    }

    @Step("Verify Get In Touch Header Displayed")
    private ContactusPage verifyGetInTouchHeaderDisplayed() throws IOException {
        CustomSoftAssert.softAssert.assertTrue(bot.isElementDisplayed(getInTouchHeaderLocator));
        return this;
    }

}
