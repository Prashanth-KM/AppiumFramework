package Appium.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 *
 * To implement the test driven through the extent test
 * to re run test cases
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (counter < retryLimit) {
            counter++;
            result.setThrowable(new RuntimeException("Skipped By retry"));
            System.out.println("Retry #" + counter + " for test: " + result.getMethod().getMethodName() + ", on thread: " + Thread.currentThread().getName());
            return true;
        }
        return false;
    }

}