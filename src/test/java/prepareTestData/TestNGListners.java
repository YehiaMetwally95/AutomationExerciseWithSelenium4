package prepareTestData;

import lombok.SneakyThrows;
import org.testng.*;
import utils.CustomSoftAssert;
import utils.PropertiesManager;

import java.io.File;
import java.io.IOException;

import static prepareTestData.LoadProductsFromDB.loadProductsFromDB;
import static prepareTestData.LoadUsersFromDB.loadUsersFromDB;
import static utils.DeleteDirectoryFiles.deleteFiles;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {
    static String propertiesFilePath = "src/main/resources/Configurations.properties";

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
        PropertiesManager.filePath = propertiesFilePath;
        try {
            PropertiesManager.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Sync with Database to Load Latest Products and Users and Update Test Data Json Files
        if (System.getProperty("syncWithDB").equalsIgnoreCase("true")) {
            loadProductsFromDB();
            loadUsersFromDB();
        }

        //Clear Old Screenshots & Allure Results before Every Run
        File file1 = new File("src/test/resources/Screenshots");
        File file2 = new File("allure-results");
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

