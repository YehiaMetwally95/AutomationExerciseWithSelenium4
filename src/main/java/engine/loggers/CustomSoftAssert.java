package engine.loggers;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static engine.loggers.LogHelper.logError;
import static engine.loggers.LogHelper.logWarning;

public class CustomSoftAssert extends SoftAssert{

    public static WebDriver softAssertDriver;
    private final static List<String> errors = new ArrayList<>();
    public static CustomSoftAssert softAssert = new CustomSoftAssert();

    @SneakyThrows
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        String errorMessage = "Soft Assertion Failed: " + ex.getMessage();
        Screenshot.captureSoftFailure(softAssertDriver,assertCommand,errorMessage);
        errors.add(errorMessage);
    }

    public static void reportSoftAssertionErrors(IInvokedMethod method)
    {
        try{
            if (!errors.isEmpty()) {
                String combinedError = String.join("\n",errors);
                logWarning("Soft Assertions Summary:\n" + combinedError);
                Allure.step("Soft Assertions Summary for "+method.getTestMethod().getMethodName()+": \n", () -> {
                    errors.forEach(Allure::step);
                });
            }
        }catch (Exception e)
            {
                logError("Failed to Log the Soft Assertion Errors",e);
            }
    }
}
