package engine.listeners;

import engine.loggers.CustomSoftAssert;
import engine.managers.PropertiesManager;
import lombok.SneakyThrows;
import org.testng.*;
import org.testng.internal.BaseTestMethod;

import java.io.File;
import java.io.IOException;

import static engine.driverManager.BrowserFactory.getDriver;
import static engine.loggers.Screenshot.captureFailure;
import static engine.loggers.Screenshot.captureSuccess;
import static engine.utilities.DeleteDirectoryFiles.deleteFiles;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {
    static String propertiesFilePath = "src/main/resources/propertiesFiles/MobileApp.properties";
    public void onTestStart(ITestResult result) {
        // not implemented
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

    public void onFinish(ISuite suite) {
        //Log All Soft Assertion Errors after Every Run with screenshot
        CustomSoftAssert.reportSoftAssertionErrors();
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // not implemented
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}

