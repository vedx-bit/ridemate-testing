package com.ridemate.listeners;

import com.ridemate.base.DriverFactory;
import com.ridemate.utils.ScreenshotUtil;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Prints a clear PASS/FAIL line for each test and saves a screenshot when a test fails. */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("START : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASS  : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String path = ScreenshotUtil.capture(DriverFactory.getDriver(), result.getMethod().getMethodName());
        System.out.println("FAIL  : " + result.getMethod().getMethodName() + " | screenshot: " + path);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIP  : " + result.getMethod().getMethodName());
    }
}
