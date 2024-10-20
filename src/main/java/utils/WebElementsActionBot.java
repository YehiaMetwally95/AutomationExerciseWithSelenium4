package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class WebElementsActionBot {
    //Variables
    WebDriver driver;
    String textBoxesPath = "src/test/resources/Screenshots/TextBoxes/";
    String pressedButtonsPath ="src/test/resources/Screenshots/PressedButtons/";
    String retrievedTextPath ="src/test/resources/Screenshots/RetrievedTexts/";

    //Constructor
    public WebElementsActionBot(WebDriver driver) {
        this.driver = driver;
    }

    //************************    Basic Interactions    ************************//
        //ActionBot1 for Typing on TextBox & Printing Text & Take Screenshot for TextBox
    public WebElementsActionBot type (By locator , String text) throws IOException {
        Waits.getFluentWait(driver).until(f -> {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            return true;
        });

       // System.out.println("Typing " + text);
       // takeElementScreenshot(driver,locator,textBoxesPath,generateUniqueInteger());
        return this;
    }

        //ActionBot2 for Pressing on Button or Link & Printing Button Name & Take Screenshot for Button
    public WebElementsActionBot press(By locator) throws IOException {
            Waits.getFluentWait(driver).until(f ->
            {
                ((JavascriptExecutor)driver).
                        executeScript("arguments[0].click();", driver.findElement(locator));
                return true;
            });
            return this;
    }

    public WebElementsActionBot press(WebElement element) throws IOException {
        try {
            Waits.getFluentWait(driver).until(f -> {
                element.click();
                return true;
            });
        }catch (TimeoutException e)
        //If Webdriver Click fails and fluent wait throw Timeout Exception, Try to click using JS
        {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        }
        return this;
    }

        //ActionBot3 for Long Pressing on Button or Link & Printing Button Name & Take Screenshot for Button
    public WebElementsActionBot longPress(By locator) throws IOException {
        Waits.getFluentWait(driver).until(f -> {
            System.out.println("LongClicking On " + driver.findElement(locator).getText());
            /*try {
                takeElementScreenshot(driver,locator,pressedButtonsPath,generateUniqueInteger());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
            new Actions(driver).moveToElement(driver.findElement(locator)).clickAndHold();
            return true;
        });
        return this;
    }

        //ActionBot4 for Get Text from Element & Take Screenshot for Retrieved Text
    public String readText(By locator) throws IOException {
        Waits.getFluentWait(driver).until(f -> {
          driver.findElement(locator).getText();
            return true;
        });
        //takeElementScreenshot(driver,locator,retrievedTextPath,generateUniqueInteger());
        return driver.findElement(locator).getText().replace("\n","");
    }

    public String readText(WebElement element) throws IOException {
        //takeElementScreenshot(driver,locator,retrievedTextPath,generateUniqueInteger());
        return element.getText();
    }

        //ActionBot5  for Verify Element is Displayed
    public boolean isElementDisplayed(By locator){
        try{
            Waits.getFluentWait(driver).until(f -> {
                driver.findElement(locator).isDisplayed();
                return true;
            });
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

        //ActionBot6 for Verify Element is Enabled
    public boolean isElementEnabled(By locator){
        try{
            Waits.getFluentWait(driver).until(f -> {
                driver.findElement(locator).isEnabled();
                return true;
            });
        }catch(TimeoutException e){
            return false;
        }
        return true;
    }

        //ActionBot8 for Hover on Element
    public WebElementsActionBot hoverOnElement(By locator) {
        Waits.getFluentWait(driver).until(f -> {
            new Actions(driver)
                    .moveToElement(driver.findElement(locator))
                    .pause(Duration.ofMillis(500))
                    .perform();
            return true;
        });
        return this;
    }

        //ActionBot9 for Drag and Drop
    public WebElementsActionBot dragAndDrop(By sourceLocator, By destinationLocator){

        Waits.getFluentWait(driver).until(f -> {
            new Actions(driver).dragAndDrop(
                    driver.findElement(sourceLocator),
                    driver.findElement(destinationLocator)
            ).build().perform();
            return true;
        });
        return this;
    }

    //ActionBot10 for Get All Matched Elements
    public List<WebElement> getAllMatchedElements(By locator){

        Waits.getFluentWait(driver).until(f -> {
            driver.findElements(locator);
            return true;
        });
        return driver.findElements(locator);
    }

    //************************    Interactions with DropDowns    ************************//

    public WebElementsActionBot selectFromDropdownByValue(By dropdown , String value)
    {
        dropDownElement(dropdown).selectByValue(value);
        return this;
    }

    public WebElementsActionBot selectFromDropdownByIndex(By dropdown , int index)
    {
        dropDownElement(dropdown).selectByIndex(index);
        return this;
    }

    public WebElementsActionBot selectFromDropdownByText(By dropdown , String text)
    {
        dropDownElement(dropdown).selectByVisibleText(text);
        return this;
    }

    public List<String> getAllOptionsAsString(By dropdown)
    {
        List<WebElement> options = dropDownElement(dropdown).getOptions();
        return options.stream().map(e->e.getText()).collect(Collectors.toList());
    }

    public String getSelectedOption(By dropdown)
    {
        return dropDownElement(dropdown).getFirstSelectedOption().getText();
    }

    public WebElementsActionBot deselectAllOptions(By dropdown)
    {
        dropDownElement(dropdown).deselectAll();
        return this;
    }

    private Select dropDownElement(By dropdownLocator)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        return new Select(driver.findElement(dropdownLocator));
    }

    //************************    Interactions with Scrolling    ************************//

    public WebElementsActionBot scrollToElementIntoView(By elementLocator) {
        WebElement table = driver.findElement(elementLocator);
        new Actions(driver).scrollToElement(table).perform();
        return this;
    }

    public WebElementsActionBot scrollByGivenAmountFromElement(By elementLocator , int deltaX, int deltaY)
    {
        WheelInput.ScrollOrigin myOrigin =
                WheelInput.ScrollOrigin.fromElement(driver.findElement(elementLocator));
        new Actions(driver).scrollFromOrigin(myOrigin,deltaX,deltaY).perform();
        return this;
    }

    public WebElementsActionBot scrollDownTillElementDisplayed (By targetElement , int scrollStep) {
        Waits.getFluentWait(driver).until(f->{
            new Actions(driver).scrollByAmount(0,scrollStep).perform();
            driver.findElement(targetElement).isDisplayed();
            return true;
        });
        return this;
    }

    //************************    Interactions with Alerts    ************************//

    public void acceptAlert()
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public  void dismissAlert()
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    public void typeTextInAlert(String text)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }

    public void typeTextInAlert(String text1,String text2)
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        Actions action = new Actions(driver);
        driver.switchTo().alert().sendKeys(text1);
        action.keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
        driver.switchTo().alert().sendKeys(text2);
    }

    public String getTextInAlert()
    {
        Waits.getFluentWait(driver).until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }
}
