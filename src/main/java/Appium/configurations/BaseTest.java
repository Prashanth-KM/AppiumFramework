package Appium.configurations;


import Appium.listeners.TestListener;
import Appium.reports.ExtentManager;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.lang.reflect.Method;

@Listeners({TestListener.class})
public class BaseTest extends TestListener {


    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
//        commandToRun("adb logcat -c");
        ExtentManager.initReports();

    }
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        ExtentManager.configExtentTest(getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1));
        startAppiumServer();
        launchApplication();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method name) {
        ExtentManager.startTest(name);
    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws NullPointerException {
        testResultCapture(result);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeApplication();
        stopAppiumServer();
    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws Exception {
        System.out.println("After Suite");
//        writeAdbLogs();
//        unInstallApp();
        ExtentManager.flushReports();
    }
}
