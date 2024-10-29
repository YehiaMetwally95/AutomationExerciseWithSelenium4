package engine.loggers;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.asserts.IAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static engine.loggers.LogHelper.*;

public class Screenshot {

    public static WebDriver screenshotDriver;

    public static void captureSuccess(ITestResult result){
            try {
                Thread.sleep(1000);
                File source = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.FILE);
                File destination = new File("src/main/resources/screenshots/SuccessfulTests/" + result.getMethod().getMethodName() + ".png");
                FileHandler.copy(source, destination);

                var screenshot = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Successful Screenshot for [" + result.getMethod().getMethodName()+"]", new ByteArrayInputStream(screenshot));
                logInfoStep("Capturing Screenshot for Succeeded Scenario");
            }catch (Exception e)
            {
                logErrorStep("Failed to Capture Screenshot for Succeeded Scenario",e);
            }
    }

    public static void captureFailure(ITestResult result){
            try {
            Thread.sleep(1000);
            File source = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/FailedTests/"+ result.getMethod().getMethodName() +".png");
            FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure Screenshot for ["+result.getMethod().getMethodName()+"]",new ByteArrayInputStream(screenshot));
                logInfoStep("Capturing Screenshot for Failed Scenario");
            }catch (Exception e)
            {
                logErrorStep("Failed to Capture Screenshot for Failed Scenario",e);
            }
    }

    public static void captureSoftFailure(IAssert<?> assertCommand,String error){

        try {
            File source = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/SoftAssertionFailures/"+ assertCommand.getExpected() +".png");
            FileHandler.copy(source, destination);

            var screenshot = ((TakesScreenshot) screenshotDriver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failed Soft Assertion Screenshot",new ByteArrayInputStream(screenshot));
            Allure.step(error);
            logWarningStep(error);
            logWarningStep("Capturing Screenshot for Soft Assertion Failure");
        }catch (Exception e)
        {
            logErrorStep("Failed to Capture Screenshot for Soft Assertion Failure",e);
        }
    }

    public static void takeElementScreenshot(WebDriver driver, By locator , String targetPath ,
                                             String fileName) throws IOException {
        try {
        File source = driver.findElement(locator).getScreenshotAs(OutputType.FILE);
        File destination = new File (targetPath+fileName+".png");
        FileHandler.copy(source,destination);
            logInfoStep("Capturing Screenshot for Element");
        }catch (Exception e)
        {
            logErrorStep("Failed to Capture Screenshot for Element",e);
        }
    }

    public static AllureRestAssured logApiRequestsToAllureReport(){
        return new AllureRestAssured()
                .setRequestAttachmentName("Request Details")
                .setResponseAttachmentName("Response Details");
    }
}

