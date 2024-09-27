package baseTest;

import objectModelsForAPIs.RegistrationRequestModel;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.JsonManager;

import java.io.*;
import java.time.Duration;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;

@Listeners(utils.TestNGListners.class)
public class LinearTests {

    @Test
    public void test1() throws IOException, InterruptedException {
        WebDriver driver = new EdgeDriver();
        driver.navigate().to("https://automationexercise.com/product_details/1");
        driver.manage().window().maximize();
        By modal = By.xpath("//button[@data-dismiss='modal']");
        By image= By.xpath("//img[@alt='ecommerce website products']");

        By productName= By.xpath("//div[contains(@class,'overlay-content')]/p[.='Fancy Green Top']");
        By viewProduct=RelativeLocator.with(By.partialLinkText("View Product")).below(productName);
        By addToCart =RelativeLocator.with(By.partialLinkText("Add to cart")).below(productName);
        By price = RelativeLocator.with(By.tagName("h2")).near(productName);
        By productPriceLocator= By.xpath("//div[contains(@class,'overlay-content')]/p[.='Fancy Green Top']/preceding-sibling::h2");
        By yehia = By.xpath("(//h2[.='Rs. 500'])[2]");
        By locator1=By.xpath("//div[@class='product-details']/descendant::p[contains(.,'Availability')]");
        By productQuantityTextBox = By.id("quantity");
        By addToCartButton = RelativeLocator.with(By.tagName("button")).near(productQuantityTextBox);
        System.out.println(driver.findElement(addToCartButton).getText());
        //driver.findElement(addToCart).click();
    }

    @Test
    public void test2() throws IOException, InterruptedException, ParseException {
        By logoButton = By.xpath("//div[contains(@class,'logo')]/descendant::img");
        By homePageButton = RelativeLocator.with(By.tagName("a")).toRightOf(logoButton);
        By productsPageButton = RelativeLocator.with(By.tagName("a")).toRightOf(homePageButton);
        By cartPageButton = By.partialLinkText("Cart");
        By signupLoginPageButton = By.partialLinkText("Signup");

        WebDriver driver = new EdgeDriver();
        driver.navigate().to("https://automationexercise.com/");
        driver.manage().window().maximize();
        String text = driver.findElement(signupLoginPageButton).getText();
        driver.findElement(signupLoginPageButton).click();

        System.out.println(text);
    }

    @Test
    public void test3() throws IOException, InterruptedException, ParseException {
        String jsonFilePath = "src/test/resources/TestDataJsonFiles/LoginTestData.json";
        JsonManager json = new JsonManager(jsonFilePath);
        String yehia = json.getData("Users");
        System.out.println(yehia);
    }

    @Test
    public void test4() throws IOException, InterruptedException, ParseException {
        new RegistrationRequestModel()
                .prepareRegistrationRequestBody()
                .sendRequestRegisterNewUser()
                .validateCodeFromResponse(201)
                .validateMassageFromResponse("User created!");
    }

    @Test
    public void test5() throws IOException, InterruptedException, ParseException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.navigate().to("https://www.99.co/");

     /* driver.findElement(By.cssSelector("a[href='/signin']")).click(); // Click on Log In button on Top

       driver.findElement(By.cssSelector("input[name='email_or_phone']")).sendKeys("jmetwallym@gmail.com"); // Enter Username
       driver.findElement(By.cssSelector("[name='password']")).sendKeys("123456789"); // Enter Password
       driver.findElement(By.xpath("//button/div[contains(text(),'Log In')]")).click(); // Click Login Button

        System.out.println(" Login Successful ...");*/


        // Method 1:
//        sessionManager.storeSessionFile("ninetynineco","jmetwallym@gmail.com");
//        sessionManager.usePreviousLoggedInSession("ninetynineco");
    }

}
