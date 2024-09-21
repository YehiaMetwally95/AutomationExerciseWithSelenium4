package utils;

import io.appium.java_client.AppiumDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class CustomSoftAssert extends SoftAssert{

    public static WebDriver softAssertDriver;
    public static CustomSoftAssert softAssert = new CustomSoftAssert();

    @SneakyThrows
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        Screenshot.captureSoftFailure(softAssertDriver,assertCommand);
    }
}
