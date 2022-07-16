package Appium.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestFactoryParent {

    private static ThreadLocal<ExtentTest> extentPoolParent = new ThreadLocal<ExtentTest>();

    public static ExtentTest getExtentTest() {
        return extentPoolParent.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        extentPoolParent.set(extentTest);
    }
}
