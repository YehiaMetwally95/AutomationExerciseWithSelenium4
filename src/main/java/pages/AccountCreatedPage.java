package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.CustomSoftAssert;

import java.io.IOException;

public class AccountCreatedPage extends HomePage{
        //Locator
        By continueButton = By.xpath("//a[contains(@data-qa,'continue')]");

        //Constructor
        public AccountCreatedPage(WebDriver driver) {
            super(driver);
        }

        //Actions
        @Step("Continue To Home Page")
        public HomePage continueToHomePage() throws IOException {
            bot.press(continueButton);
            return new HomePage(driver);
        }

        //Validation
        @Step("Verify Account Created Page is Opened")
        public AccountCreatedPage verifyAccountCreatedPageIsOpened(String pageTitle) {
            verifyAccountCreatedPageTitle(pageTitle);
            return this;
        }

        //Private Methods
        @Step("Verify Account Created Page Title")
        private AccountCreatedPage verifyAccountCreatedPageTitle(String title) {
        CustomSoftAssert.softAssert.assertTrue(bot.getPageTitle().contains(title));
        return this;
        }


}
