package engine.driverManager;

import engine.loggers.CustomSoftAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;

import static engine.loggers.LogHelper.logErrorStep;
import static engine.loggers.LogHelper.logInfoStep;

public class BrowserFactory {
    private static final ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();;

    private static final String browserType = System.getProperty("browserType");
    private static final String executionType = System.getProperty("executionType");
    private static final String remoteExecutionHost = System.getProperty("remoteExecutionHost");
    private static final String remoteExecutionPort = System.getProperty("remoteExecutionPort");

    public static ThreadLocal<RemoteWebDriver> openBrowser() throws MalformedURLException {
        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();
        if (executionType.equalsIgnoreCase("Local") || executionType.equalsIgnoreCase("LocalHeadless"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver.set(new ChromeDriver(getChromeOptions()));
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;

                case "Firefox" :
                    driver.set(new FirefoxDriver(getFireFoxOptions()));
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;

                case "Edge" :
                    driver.set(new EdgeDriver(getEdgeOptions()));
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;
                default:
                    logErrorStep("Failed to Start Browser, The Input Browser Name is Incorrect");
            }
        }

        else if (executionType.equalsIgnoreCase("Remote"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver.set(new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getChromeOptions()));
                    driver.get().setFileDetector(new LocalFileDetector());
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;

                case "Firefox" :
                    driver.set(new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getFireFoxOptions()));
                    driver.get().setFileDetector(new LocalFileDetector());
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;

                case "Edge" :
                    driver.set(new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getEdgeOptions()));
                    driver.get().setFileDetector(new LocalFileDetector());
                    logInfoStep("Starting "+ browserType +" Browser ............");
                    break;
                default:
                    logErrorStep("Failed to Start Browser, The Input Browser Name is Incorrect");
            }
        }

        //Set the Logger Classes with the driver
        CustomSoftAssert.softAssertDriver = getDriver(driver);
        context.setAttribute("driver",getDriver(driver));
        return driver;
    }

    private static ChromeOptions getChromeOptions()
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--disable-infobars");
        option.addArguments("--start-maximized");
        if (executionType.equalsIgnoreCase("LocalHeadless") || executionType.equalsIgnoreCase("Remote"))
            option.addArguments("--headless");

        return option;
    }

    private static EdgeOptions getEdgeOptions()
    {
        EdgeOptions option = new EdgeOptions();
        option.addArguments("--disable-infobars");
        option.addArguments("--start-maximized");
        if (executionType.equalsIgnoreCase("LocalHeadless") || executionType.equalsIgnoreCase("Remote"))
            option.addArguments("--headless");

        return option;
    }

    private static FirefoxOptions getFireFoxOptions()
    {
        FirefoxOptions option = new FirefoxOptions();
        option.addArguments("--start-minimized");
        if (executionType.equalsIgnoreCase("LocalHeadless") || executionType.equalsIgnoreCase("Remote"))
            option.addArguments("--headless");

        return option;
    }

    public static WebDriver getDriver(ThreadLocal<RemoteWebDriver> isolatedDriver)
    {
        return isolatedDriver.get();
    }

    public static void removeIsolatedDriver (ThreadLocal<RemoteWebDriver> isolatedDriver)
    {
        isolatedDriver.remove();
    }
}
