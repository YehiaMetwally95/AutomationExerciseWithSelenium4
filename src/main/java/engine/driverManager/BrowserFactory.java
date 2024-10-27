package engine.driverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static engine.loggers.LogHelper.logError;
import static engine.loggers.LogHelper.logInfo;

public class BrowserFactory {

    private static final String browserType = System.getProperty("browserType");
    private static final String executionType = System.getProperty("executionType");
    private static final String remoteExecutionHost = System.getProperty("remoteExecutionHost");
    private static final String remoteExecutionPort = System.getProperty("remoteExecutionPort");

    public static WebDriver openBrowser() throws MalformedURLException {
        RemoteWebDriver driver = null;

        if (executionType.equalsIgnoreCase("Local") || executionType.equalsIgnoreCase("LocalHeadless"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new ChromeDriver(getChromeOptions());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;

                case "Firefox" :
                    driver = new FirefoxDriver(getFireFoxOptions());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;

                case "Edge" :
                    driver = new EdgeDriver(getEdgeOptions());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;
                default:
                    logError("Failed to Start Browser, The Input Browser Name is Incorrect");
            }
        }

        else if (executionType.equalsIgnoreCase("Remote"))
        {
            switch (browserType)
            {
                case "Chrome" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getChromeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;

                case "Firefox" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getFireFoxOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;

                case "Edge" :
                    driver = new RemoteWebDriver(
                            new URL("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub")
                            ,getEdgeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    logInfo("Starting "+ browserType +" Browser ............");
                    break;
                default:
                    logError("Failed to Start Browser, The Input Browser Name is Incorrect");
            }
        }
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
