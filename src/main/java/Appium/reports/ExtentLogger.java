package Appium.reports;

import com.aventstack.extentreports.markuputils.MarkupHelper;

import java.util.List;
import java.util.Set;

public class ExtentLogger {

    public static void pass(String message){
        ExtentTestFactory.getExtentTest().pass(message);
    }

    public static void fail(String message){
        ExtentTestFactory.getExtentTest().fail(message);
    }

    public static void info(String message){
        ExtentTestFactory.getExtentTest().info(message);
    }

    public static void skip(String message){
        ExtentTestFactory.getExtentTest().skip(message);
    }

    public static void info(List<String> list){
        ExtentTestFactory.getExtentTest().info(MarkupHelper.createOrderedList(list));
    }

    public static void pass(List<String> list){
        ExtentTestFactory.getExtentTest().pass(MarkupHelper.createOrderedList(list));
    }

    public static void info(Set<String> set){
        ExtentTestFactory.getExtentTest().info(MarkupHelper.createOrderedList(set));
    }
}
