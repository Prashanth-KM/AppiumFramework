package Appium.reports;

import com.aventstack.extentreports.ExtentTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class ExtentTestFactory {
    private static ThreadLocal<ExtentTest> extentPool = new ThreadLocal<ExtentTest>();

    public static ExtentTest getExtentTest() {
        return extentPool.get();
    }

    public static void setExtentTest(ExtentTest extentTest) {
        extentPool.set(extentTest);
    }

}
