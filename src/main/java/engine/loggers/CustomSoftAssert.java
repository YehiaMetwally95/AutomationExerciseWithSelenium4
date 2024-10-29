package engine.loggers;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static engine.driverManager.BrowserFactory.getDriver;
import static engine.loggers.LogHelper.logWarningStep;

public class CustomSoftAssert extends SoftAssert{

    private final static List<String> errors = new ArrayList<>();
    public static CustomSoftAssert softAssert = new CustomSoftAssert();

    @SneakyThrows
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();
        ThreadLocal<RemoteWebDriver> driver = (ThreadLocal<RemoteWebDriver>) context.getAttribute("isolatedDriver");

        String errorMessage = "Soft Assertion Failed: " + ex.getMessage();
        Screenshot.captureSoftFailure(getDriver(driver),assertCommand,errorMessage);
        errors.add(errorMessage);
    }

    public static void reportSoftAssertionErrors(IInvokedMethod method)
    {
        try{
            if (!errors.isEmpty()) {
                String combinedError = String.join("\n",errors);
                logWarningStep("Soft Assertions Summary:\n" + combinedError);
                Allure.step("Soft Assertions Summary for "+method.getTestMethod().getMethodName()+": \n", () -> {
                    errors.forEach(Allure::step);
                });
            }
        }catch (Exception e)
            {
                LogHelper.logErrorStep("Failed to Log the Soft Assertion Errors",e);
            }
    }

}
