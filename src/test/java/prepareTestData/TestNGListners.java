package prepareTestData;

import io.qameta.allure.Step;
import org.json.simple.parser.ParseException;
import org.testng.*;
import utils.CustomSoftAssert;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static utils.CustomSoftAssert.softAssert;
import static utils.DeleteDirectoryFiles.deleteFiles;

public class TestNGListners implements ITestListener , IInvokedMethodListener , ISuiteListener {

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

    public void onStart (ISuite suite) {
        //Load Properties File
        LoadPropertiesFile.loadPropertiesFile();

        //Load Test Data from DB & Set Json Files Test Data
        try {
            LoadProductsFromDB.prepareProductsFromDB();
        } catch (SQLException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        try {
            LoadUsersFromDB.prepareUsersFromDB();
        } catch (SQLException | IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        //Clear Old Screenshots & Allure Results before Every Run
        File file1 = new File("src/test/resources/Screenshots");
        deleteFiles(file1);
    }

    public void onFinish(ISuite suite) {
        //Print Out All Assertion Errors after Every Run
        CustomSoftAssert.reportSoftAssertionErrors();
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // not implemented
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }
}

