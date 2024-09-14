package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    //Variables
    WebDriver driver;

    //Locators
    By loginEmail = By.xpath("(//div[@class='figure']/img)[1]");
    By loginPassword = By.xpath("//div[@class='figcaption']/a[@href='/users/1']");
    By signUpName = By.xpath("//div[@class='figcaption']/a[@href='/users/1']");
    By signUpEmail = By.xpath("//div[@class='figcaption']/a[@href='/users/1']");
    By loginButton = By.xpath("//div[@class='figcaption']/a[@href='/users/1']");
    By signUpButton = By.xpath("//div[@class='figcaption']/a[@href='/users/1']");


    //Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    @Step
    public void test()
    {

    }

}
