package baseTest;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import prepareTestData.TestNGListners;
import utils.WebElementsActionBot;

import java.io.*;
import java.time.Duration;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;

@Listeners(TestNGListners.class)
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
       String yehia = System.getProperty("user.dir")+"/+src/test/resources/FilesToBeUploaded/Mattresses.pdf";
        System.out.println(yehia);
    }

    @Test
    public void test5() throws IOException, InterruptedException, ParseException {
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.navigate().to("https://automationexercise.com/products");
        By box = By.xpath("//div[contains(@class,'productinfo')]/descendant::*[.='Blue Top']");
        By button = By.xpath("//div[contains(@class,'overlay-content')]/p[.='Blue Top']/following-sibling::a[contains(@class,'add-to-cart')]");
        new WebElementsActionBot(driver).hoverOnElement
                (box);

    }

}
