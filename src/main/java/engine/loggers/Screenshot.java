package engine.loggers;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.asserts.IAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static engine.loggers.LogHelper.logError;
import static engine.loggers.LogHelper.logInfo;

public class Screenshot {

    static int SuccessCodeForTestNG =1;
    static int FailureCodeForTestNG =2;

    public static void captureSuccess(WebDriver driver, ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus()==SuccessCodeForTestNG) {
            try {
                Thread.sleep(1000);
                File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("src/main/resources/screenshots/SuccessfulTests/" + result.getName() + ".png");
                FileHandler.copy(source, destination);

                var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(result.getName(), new ByteArrayInputStream(screenshot));
                logInfo("Capturing Screenshot for Succeeded Scenario");
            }catch (Exception e)
            {
                logError("Failed to Capture Screenshot for Succeeded Scenario",e);
            }

        }
    }

    public static void captureFailure(WebDriver driver, ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus() == FailureCodeForTestNG) {
            try {
            Thread.sleep(1000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/FailedTests/"+ result.getName() +".png");
            org.openqa.selenium.io.FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(result.getName(),new ByteArrayInputStream(screenshot));
                logInfo("Capturing Screenshot for Failed Scenario");
            }catch (Exception e)
            {
                logError("Failed to Capture Screenshot for Failed Scenario",e);
            }
        }
    }

    public static void captureSoftFailure(WebDriver driver, IAssert<?> assertCommand) throws IOException, InterruptedException {

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/SoftAssertionFailures/"+ assertCommand.getExpected() +".png");
            FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(String.valueOf(assertCommand.getExpected()),new ByteArrayInputStream(screenshot));
            logInfo("Capturing Screenshot for Soft Assertion Failure");
        }catch (Exception e)
        {
            logError("Failed to Capture Screenshot for Soft Assertion Failure",e);
        }
    }

    public static void takeElementScreenshot(WebDriver driver, By locator , String targetPath ,
                                             String fileName) throws IOException {
        try {
        File source = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
        File destination = new File (targetPath+fileName+".png");
        FileHandler.copy(source,destination);
            logInfo("Capturing Screenshot for Element");
        }catch (Exception e)
        {
            logError("Failed to Capture Screenshot for Element",e);
        }
    }

    public static AllureRestAssured logApiRequestsToAllureReport(){
        return new AllureRestAssured()
                .setRequestAttachmentName("Request Details")
                .setResponseAttachmentName("Response Details");
    }
}

