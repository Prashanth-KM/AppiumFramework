package Appium.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestFactory {
    private static ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();

    public static ExtentTest getExtentTest() {
        return extent.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        extent.set(extentTest);
    }

}
