package engine.browserActions;

import org.openqa.selenium.WebDriver;

import static engine.loggers.LogHelper.logError;
import static engine.loggers.LogHelper.logInfo;

public class WindowManager {

    public static void navigateForward(WebDriver driver) {
        try{
            driver.navigate().forward();
            logInfo("Navigating Forward to next Browser Page");
        }catch (Exception e){
            logError("Failed to Navigate Forward to next Browser Page",e);
        }
    }

    public static void navigateBackward(WebDriver driver)
    {
        try{
            driver.navigate().back();
            logInfo("Navigating Backward to previous Browser Page");
        }catch (Exception e){
            logError("Failed to Navigate Backward to previous Browser Page",e);
        }
    }

    public static void refreshWindow(WebDriver driver)
    {
        try{
            driver.navigate().refresh();
            logInfo("Refreshing the current Browser Window");
        }catch (Exception e){
            logError("Failed to Refresh the current Browser Window",e);
        }
    }

    public static void navigateToURL(WebDriver driver,String url)
    {
        try{
            driver.navigate().to(url);
            logInfo("Navigating to URL ["+url+"]");
        }catch (Exception e){
            logError("Failed to Navigate to URL ["+url+"]",e);
        }
    }

    public static void maximizeWindow(WebDriver driver)
    {
        try{
            driver.manage().window().maximize();
            logInfo("Maximizing the Browser Window");
        }catch (Exception e){
            logError("Failed to Maximize the Browser Window",e);
        }
    }

    public static void minimizeWindow(WebDriver driver)
    {
        try{
            driver.manage().window().minimize();
            logInfo("Minimizing the Browser Window");
        }catch (Exception e){
            logError("Failed to Minimize the Browser Window",e);
        }
    }

    public static void fullScreenWindow(WebDriver driver)
    {
        try{
            driver.manage().window().fullscreen();
            logInfo("FullScreen Browser Window");
        }catch (Exception e){
            logError("Failed to FullScreen Browser Window",e);
        }
    }

    public static void closeCurrentWindow(WebDriver driver)
    {
        try{
            driver.close();
            logInfo("Closing the current Browser Window");
        }catch (Exception e){
            logError("Failed to Close the current Browser Window",e);
        }    }

    public static void closeAllWindows(WebDriver driver)
    {
        try{
            driver.quit();
            logInfo("Closing All Browser Windows");
        }catch (Exception e){
            logError("Failed to Close All Browser Windows",e);
        }
    }

}
