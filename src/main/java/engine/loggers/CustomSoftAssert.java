package engine.loggers;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import static engine.loggers.LogHelper.logError;

public class CustomSoftAssert extends SoftAssert{

    public static WebDriver softAssertDriver;
    public static CustomSoftAssert softAssert = new CustomSoftAssert();

    @SneakyThrows
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        Screenshot.captureSoftFailure(softAssertDriver,assertCommand);
    }

    public static void reportSoftAssertionErrors()
    {
        softAssert.assertAll("The Soft Assertion Errors are listed below: ");
    }
}
