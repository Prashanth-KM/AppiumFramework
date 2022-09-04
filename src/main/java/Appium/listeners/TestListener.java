package Appium.listeners;

import Appium.configurations.BaseTestConfig;
import Appium.reports.ExtentTestFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

public class TestListener extends BaseTestConfig implements ITestListener {


    @Override
    public void onFinish(ITestContext iTestContext) {

        Iterator<ITestResult> skippedTestCases = iTestContext.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (iTestContext.getSkippedTests().getResults(method).size() > 0) {
                System.out.println("Removing:" + skippedTestCase.getTestClass().toString());
                skippedTestCases.remove();
            }
        }

    }


    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        if (iTestResult.getThrowable().getMessage().contains("Skipped By retry")) {
            ExtentTestFactory.getExtentTest().getExtent().removeTest(ExtentTestFactory.getExtentTest());
        } else {

            StringWriter exceptionInfo = new StringWriter();
            iTestResult.getThrowable().printStackTrace(new PrintWriter(exceptionInfo));

            String methodClassName = iTestResult.getThrowable().getMessage();
            for (StackTraceElement stack : iTestResult.getThrowable().getStackTrace()) {
                if (stack.getClassName().contains("pages.Actions")) {
                    methodClassName = methodClassName + "   Failed in Class: " + stack.getClassName() +
                            ",  in Method : " + stack.getMethodName() +
                            ",  and Line : " + stack.getLineNumber() + " This test case will be rerun, Since it failed in the first attempt";
                    break;
                }
            }
            ExtentTestFactory.getExtentTest().skip(methodClassName);
            ExtentTestFactory.getExtentTest().addScreenCaptureFromBase64String(getBase64());

        }
    }


}