package utils;

import org.openqa.selenium.WebDriver;

public class ThreadDriver {

    public static WebDriver getDriver(ThreadLocal<WebDriver> isolatedDriver)
    {
        return isolatedDriver.get();
    }

    public static void isolateWebDriver(WebDriver driver , ThreadLocal<WebDriver> isolatedDriver)
    {
        isolatedDriver.set(driver);
    }

    public static void removeIsolatedDriver (ThreadLocal<WebDriver> isolatedDriver)
    {
        isolatedDriver.remove();
    }
}
