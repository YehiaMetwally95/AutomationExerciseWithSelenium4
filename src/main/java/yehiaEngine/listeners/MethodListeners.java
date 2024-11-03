package yehiaEngine.listeners;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import yehiaEngine.assertions.SoftAssertHelper;
import yehiaEngine.loggers.AllureReportLogger;
import yehiaEngine.utilities.DeleteDirectoryFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static yehiaEngine.driverManager.BrowserFactory.getDriver;
import static yehiaEngine.loggers.LogHelper.setLogFileName;
import static yehiaEngine.loggers.Screenshot.captureFailure;
import static yehiaEngine.loggers.Screenshot.captureSuccess;

public class MethodListeners implements IInvokedMethodListener {

    static String beforeMethod = null;
    static String afterMethod= null;
    static String beforeClass= null;
    static String afterClass= null;
    static String beforeSuite= null;
    static String afterSuite= null;
    static String beforeGroup= null;
    static String afterGroup= null;
    static String beforeTest= null;
    static String afterTest= null;

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod())
            setLogFileName("Test - "+method.getTestMethod().getMethodName());
        if(method.isConfigurationMethod())
            setLogFileName("Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        if(method.getTestMethod().isBeforeMethodConfiguration())
            beforeMethod = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isAfterMethodConfiguration())
            afterMethod = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isBeforeClassConfiguration())
            beforeClass = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isAfterClassConfiguration())
            afterClass = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isBeforeSuiteConfiguration())
            beforeSuite = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isAfterSuiteConfiguration())
            afterSuite = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isBeforeTestConfiguration())
            beforeTest = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isAfterTestConfiguration())
            afterTest = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isBeforeGroupsConfiguration())
            beforeGroup = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();
        if(method.getTestMethod().isAfterGroupsConfiguration())
            afterGroup = "Configuration - "+method.getTestMethod().getMethodName()+"-"+testResult.getTestClass().getRealClass().getSimpleName()+"-"+testResult.getStartMillis();

        if(method.isTestMethod())
        {
            //Log Screenshots for Successful and Failed Tests
            ITestContext context = testResult.getTestContext();
            ThreadLocal<RemoteWebDriver> driver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedDriver");
            //Take Screenshot after every succeeded test
            if (ITestResult.SUCCESS == testResult.getStatus() && driver != null)
                captureSuccess(getDriver(driver),testResult);

                //Take Screenshot after every failed test
            else if (ITestResult.FAILURE == testResult.getStatus() && driver != null)
                captureFailure(getDriver(driver),testResult);

            //Log Summery Report for Soft Assertion Errors after Every Run
            SoftAssertHelper.reportSoftAssertionErrors(method);

            //Upload the Log Files to Allure Report
            AllureReportLogger.uploadLogFileIntoAllure(beforeSuite);
            AllureReportLogger.uploadLogFileIntoAllure(beforeGroup);
            AllureReportLogger.uploadLogFileIntoAllure(beforeTest);
            AllureReportLogger.uploadLogFileIntoAllure(beforeClass);
            AllureReportLogger.uploadLogFileIntoAllure(beforeMethod);
            AllureReportLogger.uploadLogFileIntoAllure("Test - "+method.getTestMethod().getMethodName());
            AllureReportLogger.uploadLogFileIntoAllure(afterMethod);
            AllureReportLogger.uploadLogFileIntoAllure(afterClass);
            AllureReportLogger.uploadLogFileIntoAllure(afterTest);
            AllureReportLogger.uploadLogFileIntoAllure(afterGroup);
            AllureReportLogger.uploadLogFileIntoAllure(afterSuite);
        }
    }
}
