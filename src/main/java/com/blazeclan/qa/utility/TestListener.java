package com.blazeclan.qa.utility;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.logging.Log;
import com.blazeclan.qa.reports.ExtentManager;
import com.blazeclan.qa.reports.ExtentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;



public class TestListener extends CommonFunctions implements ITestListener {

    public void onStart(ITestContext context) {
        System.out.println("*** Test Suite " + context.getName() + " started ***");
    }

    public void onTestStart(ITestResult result) {
        Log.info(result.getName() + " test is starting.");
        ExtentTestManager.startTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
    }

    public void onFinish(ITestContext testContext) {
        Log.info("I am in onFinish method " + testContext.getName());
        ExtentManager.createExtentReports().flush();
    }

    /**
     * onTestFailure method is used to perform some action which are given after test case is failed
     *
     * @param result ITestResult interface is used to get results of the testcase
     */
    public void onTestFailure(ITestResult result) {
        System.out.println("Method failed " + result.getName());
        Log.error(result.getName() + " Test is failed");
        System.out.println(Arrays.toString(result.getParameters()));
        String screenshotPath = captureScreenshot(result.getName(), "failed");

        try {
            ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
            MediaEntityBuilder screenshot = MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath);
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed", screenshot.build());
            ExtentTestManager.getTest().info(MarkupHelper.createLabel("failed", ExtentColor.RED));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onTestSuccess method is used to perform some action which are given after test case is passed
     * @param result ITestResult interface is used to get results of the testcase
     */
    public void onTestSuccess(ITestResult result) {
        Log.info(result.getName() + " Test is passed");
        Log.info(result.getName() +" " + result.getInstanceName()+"  "+result.getHost()+" "+result.id()+"  "+ Arrays.toString(result.getFactoryParameters()));
        String screenshotPath = captureScreenshot(result.getName(), "success");
        MediaEntityBuilder screenshot = MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath);
        ExtentTestManager.getTest().log(Status.PASS, "Test passed", screenshot.build());
    }

    /**
     * onTestSuccess method is used to perform some action which are given after test case is skipped or ignored
     * @param result ITestResult interface is used to get results of the testcase
     */
    public void onTestSkipped(ITestResult result) {
        System.out.println("Method skipped " + result.getName());
        Log.warn(result.getName() + " Test is skipped");
        Log.info(Arrays.toString(result.getParameters()));
        String screenshotPath = captureScreenshot(result.getName(), "skipped");
        MediaEntityBuilder screenshot = MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath);
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped", screenshot.build());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio " + iTestResult.getName());
    }
}
