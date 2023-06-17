package Appium.generic;
import Appium.reports.ExtentLogger;
import Appium.utils.Constants;
import io.appium.java_client.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import java.time.Duration;
import java.util.Collections;
import java.util.List;


public class CommonFunctions implements Constants {

    public AppiumDriver driver;
   public static PointerInput finger1;
    public static  Sequence sequence;

    public CommonFunctions(AppiumDriver driver) {
        this.driver = driver;
    }

    public  void sendKeys(WebElement element, String data,String elementName)  {
        try {
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
     * This function will wait for the element to be visible and clickable and then clicks on it
     *
     */

    public void waitAndClick(WebElement element, String elementName) throws InterruptedException {
        Thread.sleep(3000);
        element.click();
        ExtentLogger.info(elementName + " is Clicked");


    }

    /**
     * This Function is to generateXpath using text and Check element is present using assert
     *
     * @author Prashanth
     * @param: String
     */
    public boolean generateTextXpathIsElementPresent(String text) {
        boolean flag = false;
        List<WebElement> elements = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + text + "')]"));
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
    public boolean isElementDisplayed(WebElement locator) {
        try {
            if (locator.isDisplayed())
                System.out.println("Element present on screen ***********" + locator);
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element not present on screen **************" + locator);
            return false;
        }
    }

    public WebElement element(WebElement element) {
        try {
            Thread.sleep(2000);
        } catch (NoSuchElementException | TimeoutException | InterruptedException e) {

        }
        return element;
    }

    /**
     * This Function will pause the execution for given secs.
     *
     * @param secs : No of seconds to be paused.
     */
    public void waitInSec(long secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public boolean isElementPresent(WebElement locator) throws NoSuchElementException{

        try {
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
    public static void swipe(AppiumDriver driver, DIRECTION direction) {
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

                 finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
                 sequence = new Sequence(finger1, 1)
                        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(new Pause(finger1, Duration.ofSeconds(2)))
                        .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, startY))
                        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(sequence));

                break;

            case LEFT:
                startY = (int) (size.height / 2);
                startX = (int) (size.width * 0.05);
                endX = (int) (size.width * 0.90);
                 sequence = new Sequence(finger1, 1)
                        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(new Pause(finger1, Duration.ofSeconds(2)))
                        .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, startY))
                        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(sequence));

                break;

            case UP:
                endY = (int) (size.height * 0.70);
                startY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                sequence = new Sequence(finger1, 1)
                        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(new Pause(finger1, Duration.ofSeconds(2)))
                        .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY))
                        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(sequence));
                break;


            case DOWN:
                startY = (int) (size.height * 0.70);
                endY = (int) (size.height * 0.30);
                startX = (size.width / 2);
                sequence = new Sequence(finger1, 1)
                        .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                        .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                        .addAction(new Pause(finger1, Duration.ofSeconds(2)))
                        .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY))
                        .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(sequence));

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
    public void scrollToMobileElement(WebElement element, String scrollCount) {
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
