package engine.loggers;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.asserts.IAssert;

import java.io.File;
import java.io.IOException;

import static engine.loggers.AllureReportLogger.*;
import static engine.loggers.LogHelper.*;

public class Screenshot {

    public static void captureSuccess(WebDriver driver, ITestResult result) {
       String screenshotName = "Successful Screenshot for [" + result.getMethod().getMethodName()+"]";
        try {
            Thread.sleep(1000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/SuccessfulTests/" + result.getMethod().getMethodName() + ".png");
            FileHandler.copy(source, destination);

            logScreenshotIntoAllure(driver, screenshotName,null);
            logInfoStep("Capturing Screenshot for Succeeded Scenario");
        } catch (Exception e) {
            logErrorStep("Failed to Capture Screenshot for Successes Scenario", e);
        }
    }

    public static void captureFailure(WebDriver driver, ITestResult result){
        String screenshotName = "Failed Screenshot for [" + result.getMethod().getMethodName()+"]";
        try {
            Thread.sleep(1000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/FailedTests/"+ result.getMethod().getMethodName() +".png");
            FileHandler.copy(source, destination);

            logScreenshotIntoAllure(driver, screenshotName,null);
            logInfoStep("Capturing Screenshot for Failed Scenario");
            } catch (Exception e)
            {
                logErrorStep("Failed to Capture Screenshot for Failed Scenario",e);
            }
    }

    public static void captureSoftFailure(WebDriver driver,IAssert<?> assertCommand,String error){
        String screenshotName = "Failed Soft Assertion Screenshot";
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File("src/main/resources/screenshots/SoftAssertionFailures/"+ assertCommand.getExpected() +".png");
            FileHandler.copy(source, destination);

            logScreenshotIntoAllure(driver, screenshotName,error);
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




}

