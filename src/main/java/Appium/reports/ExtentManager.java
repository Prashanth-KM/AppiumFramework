package Appium.reports;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.lang.reflect.Method;

public final class ExtentManager {
    private static ExtentReports extent;

    public static void initReports(){
        if(extent==null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("Reports/Spark.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Appium report");
            extent.attachReporter(spark);
        }

    }

    public static void flushReports() throws InterruptedException {
        Thread.sleep(200);
        extent.flush();
    }


    public static void configExtentTest(String className) {
        ExtentTest parent = extent.createTest(className);
        ExtentTestFactoryParent.setExtentTest(parent);
    }

    public static void startTest(Method name) {
        ExtentTest child = ExtentTestFactoryParent.getExtentTest()
                .createNode(name.getName());
        ExtentTestFactory.setExtentTest(child);
    }

}
