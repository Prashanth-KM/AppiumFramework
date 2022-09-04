package Appium.utils;


import io.appium.java_client.AppiumDriver;

/**
 *
 * @author: Prashanth
 * Purpose:	To set the configuration for type of drivers
 */

public class ContextManager {

    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    public static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }


}

