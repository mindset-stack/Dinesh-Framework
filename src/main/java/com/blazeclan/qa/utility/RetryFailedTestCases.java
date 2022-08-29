package com.blazeclan.qa.utility;

import com.blazeclan.qa.logging.Log;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTestCases implements IRetryAnalyzer {
    private int retryCnt = 0;
    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCnt = 1;
        if (retryCnt < maxRetryCnt) {
            Log.info("Retrying " + iTestResult.getName() + " again and the count is " + (retryCnt + 1));
            retryCnt++;
            return true;
        }
        return false;
    }
}
