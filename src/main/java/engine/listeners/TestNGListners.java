package engine.listeners;

import engine.loggers.CustomSoftAssert;
import engine.managers.PropertiesManager;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static engine.driverManager.BrowserFactory.getDriver;
import static engine.loggers.AllureReportLogger.generateAllureReport;
import static engine.loggers.AllureReportLogger.uploadLogFileIntoAllure;
import static engine.loggers.Screenshot.*;
import static engine.utilities.DeleteDirectoryFiles.deleteFiles;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {
    static String propertiesFilePath = "src/main/resources/propertiesFiles/MobileApp.properties";
    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {

    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
        // not implemented
    }

    @SneakyThrows
    public void onStart (ISuite suite) {
        //Load Properties File
       PropertiesManager.loadProperties();

        //Clear Old Screenshots & Allure Results before Every Run
        File file1 = new File("src/main/resources/screenshots");
        File file2 = new File("target/allure-results");
        deleteFiles(file1);
        deleteFiles(file2);
    }

    public void onFinish (ISuite suite) {
        //Genarate the Allure Report after Suite Run
        generateAllureReport();
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    if (method.isTestMethod())
        {

        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
            if(method.isTestMethod())
            {
                //Log Screenshots for Successful and Failed Tests
                ITestContext context = testResult.getTestContext();
                ThreadLocal<RemoteWebDriver> driver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedDriver");
                //Take Screenshot after every succeeded test
                if (ITestResult.SUCCESS == testResult.getStatus() )
                    captureSuccess(getDriver(driver),testResult);

                //Take Screenshot after every failed test
                else if (ITestResult.FAILURE == testResult.getStatus())
                    captureFailure(getDriver(driver),testResult);

                //Log Summery Report for Soft Assertion Errors after Every Run
                CustomSoftAssert.reportSoftAssertionErrors(method);

                //Upload the Log File to Allure Report
                uploadLogFileIntoAllure();
            }     
    }
}

