package engine.browserActions;

import engine.loggers.LogHelper;
import org.openqa.selenium.WebDriver;

import static engine.loggers.LogHelper.logInfoStep;

public class WindowManager {

    public static void navigateForward(WebDriver driver) {
        try{
            driver.navigate().forward();
            logInfoStep("Navigating Forward to next Browser Page");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Navigate Forward to next Browser Page",e);
        }
    }

    public static void navigateBackward(WebDriver driver)
    {
        try{
            driver.navigate().back();
            logInfoStep("Navigating Backward to previous Browser Page");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Navigate Backward to previous Browser Page",e);
        }
    }

    public static void refreshWindow(WebDriver driver)
    {
        try{
            driver.navigate().refresh();
            logInfoStep("Refreshing the current Browser Window");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Refresh the current Browser Window",e);
        }
    }

    public static void navigateToURL(WebDriver driver,String url)
    {
        try{
            driver.navigate().to(url);
            logInfoStep("Navigating to URL ["+url+"]");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Navigate to URL ["+url+"]",e);
        }
    }

    public static void maximizeWindow(WebDriver driver)
    {
        try{
            driver.manage().window().maximize();
            logInfoStep("Maximizing the Browser Window");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Maximize the Browser Window",e);
        }
    }

    public static void minimizeWindow(WebDriver driver)
    {
        try{
            driver.manage().window().minimize();
            logInfoStep("Minimizing the Browser Window");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Minimize the Browser Window",e);
        }
    }

    public static void fullScreenWindow(WebDriver driver)
    {
        try{
            driver.manage().window().fullscreen();
            logInfoStep("FullScreen Browser Window");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to FullScreen Browser Window",e);
        }
    }

    public static void closeCurrentWindow(WebDriver driver)
    {
        try{
            driver.close();
            logInfoStep("Closing the current Browser Window");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Close the current Browser Window",e);
        }
    }

    public static void closeAllWindows(WebDriver driver)
    {
        try{
            driver.quit();
            logInfoStep("Closing All Browser Windows");
        }catch (Exception e){
            LogHelper.logErrorStep("Failed to Close All Browser Windows",e);
        }
    }

}
