package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.CustomSoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PaymentDonePage extends HomePage {
    //Variables

    //Locators
    By downloadInvoice = By.partialLinkText("Invoice");
    By continueButton = By.partialLinkText("Continue");
    By orderConfirmMessageLocator = By.xpath("//h2[@data-qa='order-placed']");

    //Constructor
    public PaymentDonePage(WebDriver driver) {
        super(driver);
    }

    //Actions
    @Step("Continue Shopping")
    public HomePage continueShopping() throws IOException {
        bot.press(continueButton);
        return new HomePage(driver);
    }

    @Step("Download Invoice")
    public PaymentDonePage downloadInvoice() throws IOException {
        bot.press(downloadInvoice);
        return this;
    }

    //Validation
    @Step("Assert Order Confirm Message")
    public PaymentDonePage assertOrderConfirmMessage(String message) throws IOException {
        Assert.assertEquals(bot.readText(orderConfirmMessageLocator),message);
        return this;
    }

    @Step("Verify Invoice is Downloaded")
    public PaymentDonePage verifyInvoiceIsDownloaded(String folderPath, String fileNameWithExt) throws IOException {

       //String folderPath  = System.getProperty( "user.home" ) + "\\Downloads" ;
        boolean  isDownloadComplete  =  false ;
        int  waitTimeInSeconds  =  60 ; // Maximum time given for downloading
        int  elapsedTime  =  0 ; //Time counter
        while (!isDownloadComplete && elapsedTime < waitTimeInSeconds) {
            File downloadedFile  =  new  File (folderPath+ "\\" + fileNameWithExt);
            if (downloadedFile.exists()) {
                isDownloadComplete = true ;
            }
            else {
                elapsedTime++;
            }
        }
        CustomSoftAssert.softAssert.assertTrue(isDownloadComplete);
        return this;
    }
}
