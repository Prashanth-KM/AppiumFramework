package pages.Actions;

import com.aventstack.extentreports.ExtentTest;
import Appium.generic.CommonFunctions;
import Appium.listeners.TestListener;
import Appium.reports.ExtentTestFactory;
import Appium.utils.Constants;
import Appium.utils.ContextManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import pages.Objects.LoginPageObjects;

import java.util.NoSuchElementException;

public class LoginPageActions extends TestListener implements Constants {

    CommonFunctions commonFunction;
    AppiumDriver driver;
    LoginPageObjects loginPageObjects = new LoginPageObjects();
    ExtentTest extentTest = ExtentTestFactory.getExtentTest();

    public LoginPageActions() {
        this.driver = ContextManager.getDriver();
        commonFunction = new CommonFunctions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        PageFactory.initElements(new AppiumFieldDecorator(driver), loginPageObjects);

    }

    public MobileElement element(MobileElement element) {
        try {
            commonFunction.waitTillTheElementIsVisibleAndClickable(element);
        } catch (NoSuchElementException | TimeoutException e) {
            e.printStackTrace();
        }
        return element;

    }

    public void validateInValidLogin() {
            commonFunction.waitInSec(5);
            commonFunction.waitTillTheElementIsVisibleAndClickable(loginPageObjects.phoneNumber);
            element(loginPageObjects.phoneNumber).sendKeys(inValidNo);
            extentTest.info("Phone number is entered :" + inValidNo);
            commonFunction.isElementPresentAssertTrue(loginPageObjects.invalidLoginText);
            String inValidNoAlertMsg= loginPageObjects.invalidLoginText.getText();
            extentTest.pass("Invalid number alert message is displayed : "+inValidNoAlertMsg);
            element(loginPageObjects.pinCode).sendKeys(nonExistentPinCode);
            extentTest.info("InValid Pin code is entered : " +nonExistentPinCode);
            commonFunction.waitInSec(2);
            commonFunction.isElementPresentAssertTrue(loginPageObjects.invalidOtpError);
            String inValidOtpAlertMsg= loginPageObjects.invalidOtpError.getText();
            extentTest.pass("Invalid OTP alert message is displayed : "+inValidOtpAlertMsg );

            commonFunction.waitAndClick(loginPageObjects.submitBtn);
            commonFunction.waitInSec(3);
            commonFunction.isElementPresentAssertTrue(loginPageObjects.invalidLoginAlertTitle);
            String alertMsg= loginPageObjects.invalidLoginAlertTitle.getText();
            extentTest.pass("Invalid Login alert pop up message is displayed : "+alertMsg);
            loginPageObjects.invalidLoginAlertOkBtn.click();

    }

}
