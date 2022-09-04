package Appium.generic;
import Appium.reports.ExtentLogger;
import Appium.utils.Constants;
import io.appium.java_client.*;
import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

import static io.appium.java_client.touch.offset.PointOption.point;

public class CommonFunctions implements Constants {

    public AppiumDriver driver;
    WebDriverWait wait = null;

    public CommonFunctions(AppiumDriver driver) {
        this.driver = driver;
    }

    public  void sendKeys(WebElement element, String data,String elementName)  {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(data);
            Thread.sleep(200);
            ExtentLogger.info(elementName + " is entered :" +data);
        } catch (Exception e) {
            System.out.println("Unable to find element: " + element);
            e.printStackTrace();
        }
    }

    /**
     * This function will wait for the element to be visible and clickable
     *
     */

    public void waitTillTheElementIsVisibleAndClickable(MobileElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(element));

        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This function will wait for the element to be visible and clickable and then clicks on it
     *
     */

    public void waitAndClick(MobileElement element, String elementName) {
        waitTillTheElementIsVisibleAndClickable(element);
        element.click();
        ExtentLogger.info(elementName + " is Clicked");


    }

    public MobileElement element(MobileElement element) {
        try {
            waitTillTheElementIsVisibleAndClickable(element);
        } catch (NoSuchElementException | TimeoutException e) {

        }
        return element;
    }
    /**
     * This Function is to generateXpath using text and Check element is present using assert
     *
     * @author Prashanth
     * @param: String
     */
    public boolean generateTextXpathIsElementPresent(String text) {
        boolean flag = false;
        List<MobileElement> elements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + text + "')]"));
        if (elements.size() > 0) {
            flag = true;
            System.out.println("Check the " + text + " element is present");
        }
        Assert.assertTrue(flag, "Element is not present");
        return true;
    }


    /**
     * This Function is to check the element is present or not
     *
     * @author Prashanth
     * @param: Mobile Element
     */
    public boolean isElementDisplayed(MobileElement locator) {
        try {
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }


    /**
     * This Function will pause the execution for given secs.
     *
     * @param secs : No of seconds to be paused.
     */
    public void waitInSec(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This Function is to hide keyboard
     *
     */
    public void hideKeyBoard() {
        try {
            ((AppiumDriver<MobileElement>) driver).hideKeyboard();
            System.out.println("Hide KeyBoard");
        } catch (Exception e) {
            System.out.println("KeyBoard not found to hide");
        }
    }

    /**
     * This function will wait for the element to be visible
     *
     */
    public MobileElement waitForElementToAppear(MobileElement id) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(id));
        return id;
    }
    /**
     * This Function is to accept Alert
     */
    public void acceptAlert(){
        try {
            Thread.sleep(2000);
            driver.switchTo().alert().accept();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isElementPresent(MobileElement locator) throws NoSuchElementException{

        try {
            waitForElementToAppear(locator);
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (Exception e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }


    public enum DIRECTION {
        DOWN, UP, LEFT, RIGHT;
    }
    public static void swipe(MobileDriver driver, DIRECTION direction) {
        Dimension size = driver.manage().window().getSize();

        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;

        switch (direction) {
            case RIGHT:
                startY = (int) (size.height / 2);
                startX = (int) (size.width * 0.90);
                endX = (int) (size.width * 0.05);
                new TouchAction(driver)
                        .press(point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                        .moveTo(point(endX, startY))
                        .release()
                        .perform();
                break;

            case LEFT:
                startY = (int) (size.height / 2);
                startX = (int) (size.width * 0.05);
                endX = (int) (size.width * 0.90);
                new TouchAction(driver)
                        .press(point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                        .moveTo(point(endX, startY))
                        .release()
                        .perform();

                break;

            case UP:
                endY = (int) (size.height * 0.70);
                startY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                new TouchAction(driver)
                        .press(point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                        .moveTo(point(startX, endY))
                        .release()
                        .perform();
                break;


            case DOWN:
                startY = (int) (size.height * 0.70);
                endY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                new TouchAction(driver)
                        .press(point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                        .moveTo(point(startX, endY))
                        .release()
                        .perform();

                break;

        }
    }


    public void swipeUp ()
    {
        swipe(driver, DIRECTION.UP);
    }
    public void swipeDown ()
    {
        swipe(driver, DIRECTION.DOWN);

    }
    public void swipeRight ()
    {
        swipe(driver, DIRECTION.RIGHT);

    }
    public void swipeLeft ()
    {
        swipe(driver, DIRECTION.LEFT);

    }



    /**
     * This Function is to Scroll to element
     *
     * @author Prashanth
     * @param: Mobile Element & String
     */
    public void scrollToMobileElement(MobileElement element, String scrollCount) {
        try {
            int count = Integer.parseInt(scrollCount);
            for (int i = 0; i < count; i++) {
                if (isElementDisplayed(element)) {
                    break;
                } else {
                    swipeDown();
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

}
