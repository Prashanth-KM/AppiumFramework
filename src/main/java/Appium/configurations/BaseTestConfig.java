package Appium.configurations;

import Appium.listeners.RetryAnalyzer;
import Appium.reports.ExtentTestFactory;
import Appium.utils.ConfigurationManager;
import Appium.utils.ContextManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;

import static com.google.common.collect.ObjectArrays.concat;

public class BaseTestConfig extends RetryAnalyzer {


    public ConfigurationManager prop;
    public AppiumDriverLocalService service;
    public AppiumDriver driver;
    private static ExtentReports extent;
    private static final String[] OS_MAC_RUNTIME = {"/bin/bash", "-l", "-c"};
    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    public static String filePath;

    /**
     * Constructor to load config.properties
     */
    public BaseTestConfig() {
        try {
            prop = ConfigurationManager.getInstance();
            this.driver = ContextManager.getDriver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start Appium Server
     */
    public void startAppiumServer() {
            try {
                service = AppiumDriverLocalService.buildDefaultService();
                writeAppiumServerLogsIntoFile();
                service.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void writeAppiumServerLogsIntoFile() {
        File logFile = new File(System.getProperty("user.dir") + "/logs/" + "AppiumServerLogs.txt");
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withLogFile(logFile));
    }

    /**
     * Stop Appium Server
     */
    public void stopAppiumServer() {
            service.stop();
    }

    /**
     *Code to Launch/Install Application from APK file
     */

    public void launchApplication() {
        try {
            System.out.println("EndPoint used is " + prop.getProperty("endpoint"));
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("newCommandTimeout", prop.getProperty("newCommandTimeout"));
            capabilities.setCapability("platformName", prop.getProperty("platformName"));
            capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
            capabilities.setCapability("appPackage", prop.getProperty("appPackage"));
            capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
            capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
            capabilities.setCapability("automationName", prop.getProperty("automationName"));
            capabilities.setCapability("systemPort", prop.getProperty("systemPort"));//remoteAppsCacheLimit
            capabilities.setCapability("uiautomator2ServerLaunchTimeout", "80000");
            capabilities.setCapability("adbExecTimeout", "80000");
//            capabilities.setCapability("app", System.getProperty("user.dir") + "/" + prop.getProperty("app"));
            capabilities.setCapability("enforceAppInstall", false);
            capabilities.setCapability("skipDeviceInitialization",true);
            capabilities.setCapability("noReset",true);
            capabilities.setCapability("skipServerInstallation",true);
            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
            driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("endpoint")), capabilities);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ContextManager.setDriver(driver);


    }
    /**
     *To Launch PlayStore
     */
    public void launchPlayStore() {
            try {
                System.out.println("EndPoint used is " + prop.getProperty("endpoint"));
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("newCommandTimeout", prop.getProperty("newCommandTimeout"));
                capabilities.setCapability("platformName", prop.getProperty("platformName"));
                capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
                capabilities.setCapability("appPackage", prop.getProperty("playstorepkg"));
                capabilities.setCapability("appActivity", prop.getProperty("playstoreActivity"));
                capabilities.setCapability("deviceName", prop.getProperty("deviceName"));
                capabilities.setCapability("noSign", prop.getProperty("noSign"));
                capabilities.setCapability("automationName", prop.getProperty("automationName"));
                capabilities.setCapability("systemPort", prop.getProperty("systemPort"));
                capabilities.setCapability("uiautomator2ServerLaunchTimeout", "80000");
                capabilities.setCapability("adbExecTimeout", "80000");
                capabilities.setCapability("skipDeviceInitialization", true);
                capabilities.setCapability("skipServerInstallation",true);
                capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
                driver = new AndroidDriver<MobileElement>(new URL(prop.getProperty("endpoint")), capabilities);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        ContextManager.setDriver(driver);
    }

    /**
     * Quiting the Driver
     */
    public void closeApplication() {
            if (ContextManager.getDriver() != null) {
                ContextManager.getDriver().quit();
            }

    }


    /**
     * Result Status Capture
     *
     * @param result
     */
    public void testResultCapture(ITestResult result) throws NullPointerException {

        System.out.println("===============================Check status======================================" + result.getStatus());
        /**
         * Success Block
         */
        if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentTestFactory.getExtentTest().log(Status.PASS, result.getMethod().getMethodName() + " Passed");
        }

        /**
         * Failure Block
         */
        try {

            if (result.getStatus() == ITestResult.FAILURE) {
                if (result.getThrowable().getMessage().contains("Skipped By retry")) {
                    ExtentTestFactory.getExtentTest().getExtent().removeTest(ExtentTestFactory.getExtentTest());
                } else {
                    StringWriter exceptionInfo = new StringWriter();
                    result.getThrowable().printStackTrace(new PrintWriter(exceptionInfo));

                    String methodClassName = result.getThrowable().getMessage();
                    for (StackTraceElement stack : result.getThrowable().getStackTrace()) {
                        if (stack.getClassName().contains("pages.Actions")) {
                            methodClassName = methodClassName + "   Failed in Class: " + stack.getClassName() +
                                    ",  in Method : " + stack.getMethodName() +
                                    ",  and Line : " + stack.getLineNumber();
                            break;
                        }
                    }

                    ExtentTestFactory.getExtentTest().fail(methodClassName);
                    ExtentTestFactory.getExtentTest().addScreenCaptureFromBase64String(getBase64());

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Capture screen shot
     * @return
     */
    public String getBase64() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    }
    /**
     * @Description : Un install app using adb commands
     */
    public void unInstallApp() {
        try {
            String command = "adb uninstall " + prop.getProperty("appPackage");
            String[] allCommand;
            String osName = System.getProperty("os.name");
            System.out.println("osName: " + osName);
            if (osName.contains("Win")) {
                allCommand = concat(WIN_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
                Thread.sleep(5000);
            } else if(osName.contains("Mac")) {
                allCommand = concat(OS_MAC_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
                Thread.sleep(5000);
            }

        } catch (NullPointerException | InterruptedException | IOException e) {
           e.printStackTrace();
        }
    }

    public void commandToRun(String command) {
        try {
            String[] allCommand;
            String osName = System.getProperty("os.name");
            if (osName.contains("Win")) {
                allCommand = concat(WIN_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
            } else if(osName.contains("Mac")) {
                allCommand = concat(OS_MAC_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
            }

        } catch (NullPointerException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAdbLogs() {
        File logs = new File(System.getProperty("user.dir") + "/logs/" + "AdbLogs.txt");
        try {
            String command = "adb logcat >"+logs;
            String[] allCommand;
            String osName = System.getProperty("os.name");
            if (osName.contains("Win")) {
                allCommand = concat(WIN_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
            } else if(osName.contains("Mac")) {
                allCommand = concat(OS_MAC_RUNTIME, command);
                ProcessBuilder pb = new ProcessBuilder(allCommand);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                p.waitFor();
            }
            Robot robot= new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_CONTROL);

        } catch (NullPointerException | InterruptedException | IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
