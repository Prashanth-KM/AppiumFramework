package Appium.generic;
import Appium.reports.ExtentTestFactory;
import Appium.utils.Constants;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.sql.*;
import java.util.List;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofSeconds;

public class CommonFunctions implements Constants {

    public AppiumDriver driver;
    WebDriverWait wait = null;
    ExtentTest extentTest = ExtentTestFactory.getExtentTest();

    public CommonFunctions(AppiumDriver driver) {
        this.driver = driver;
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

    public void waitAndClick(MobileElement element) {
        waitTillTheElementIsVisibleAndClickable(element);
        element.click();

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
        return flag;
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
     * Asserts that a condition is true. If it isn't,
     * an AssertionError is thrown.
     *
     */
    public boolean isElementPresentAssertTrue(MobileElement element) {
        try {
            Assert.assertTrue(isElementDisplayed(element));
        } catch (Exception e) {
            extentTest.info(element + " The Element is not found, Assertion failed");
        }
        return false;
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

    /**
     * Perform swipe action vertically from point X to point Y on any screen
     *
     * @author :Prashanth
     */
    public void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int starty = (int) (size.height * 0.8);
        int endy = (int) (size.height * 0.3);
        int startx = (int) (size.width / 2.2);
        try {
            waitInSec(2);
            System.out.println("Trying to swipe up from x:" + startx + " y:" + starty + ", to x:" + startx + " y:" + endy);
            new TouchAction((PerformsTouchActions) driver).press(point(startx, starty)).waitAction(waitOptions(ofSeconds(3)))
                    .moveTo(point(startx, endy)).release().perform();
        } catch (Exception e) {
            System.out.println("Swipe action was not successful.");
        }
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
                    swipeUp();
                }
            }
        } catch (Exception e) {
            System.out.println("Scroll to mobile element failed");
        }
    }

}
