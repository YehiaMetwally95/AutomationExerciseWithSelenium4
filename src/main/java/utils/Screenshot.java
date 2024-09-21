package utils;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.asserts.IAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Screenshot {

    static int SuccessCodeForTestNG =1;
    static int FailureCodeForTestNG =2;

    public static void captureSuccess(WebDriver driver, ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus()==SuccessCodeForTestNG) {

            Thread.sleep(1000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/test/resources/Screenshots/SuccessfulTests/"+ result.getName() +".png");
            org.openqa.selenium.io.FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(result.getName(),new ByteArrayInputStream(screenshot));
        }
    }

    public static void captureFailure(WebDriver driver, ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus() == FailureCodeForTestNG) {

            Thread.sleep(1000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/test/resources/Screenshots/FailedTests/"+ result.getName() +".png");
            org.openqa.selenium.io.FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(result.getName(),new ByteArrayInputStream(screenshot));
        }
    }

    public static void captureSuccess(WebDriver driver, Scenario cucumberResult) throws IOException {
        //Increment ScreenshotFileNumber
        if (!cucumberResult.isFailed()) {

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/test/resources/Screenshots/SuccessfulTests/"+cucumberResult.getName()+".png");
            org.openqa.selenium.io.FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(cucumberResult.getName(),new ByteArrayInputStream(screenshot));
        }
    }

    public static void captureFailure(WebDriver driver, Scenario cucumberResult) throws IOException {
        if (cucumberResult.isFailed()) {

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/test/resources/Screenshots/FailedTests/"+cucumberResult.getName()+".png");
            org.openqa.selenium.io.FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(cucumberResult.getName(),new ByteArrayInputStream(screenshot));
        }
    }

    public static void captureSoftFailure(WebDriver driver, IAssert<?> assertCommand) throws IOException, InterruptedException {

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File("src/test/resources/Screenshots/SoftAssertionFailures/"+ assertCommand.getExpected() +".png");
        FileHandler.copy(source, destination);

        var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(String.valueOf(assertCommand.getExpected()),new ByteArrayInputStream(screenshot));
    }

    public static void takeElementScreenshot(WebDriver driver, By locator , String targetPath ,
                                             String fileName) throws IOException {
        File source = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
        File destination =
                new File (targetPath+fileName+".png");

        FileHandler.copy(source,destination);
    }

    public static void takeElementScreenshot(WebDriver driver, WebElement element , String targetPath ,
                                             String fileName) throws IOException {
        File source = element.getScreenshotAs(OutputType.FILE);
        File destination =
                new File (targetPath+fileName+".png");

        FileHandler.copy(source,destination);
    }
    }

