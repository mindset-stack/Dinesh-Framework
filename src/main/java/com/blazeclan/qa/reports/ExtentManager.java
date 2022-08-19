package com.blazeclan.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;

import java.util.Properties;

public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();
    public synchronized static ExtentReports createExtentReports() {
        Properties prop = CommonFunctions.init_properties(IConstants.EXTENT_CONFIG_PATH);
        ExtentSparkReporter reporter = new ExtentSparkReporter(prop.getProperty("OutputDirectory"));
        reporter.config().setReportName(prop.getProperty("ReportName"));
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Branch Name", prop.getProperty("BranchName"));
        extentReports.setSystemInfo("Author", prop.getProperty("Author"));
        extentReports.setReportUsesManualConfiguration(true);
//        reporter.config().setJs("");
              reporter.viewConfigurer()
                .viewOrder()
                .as(new ViewName[] {
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG
                })
                .apply();
        return extentReports;
    }
}
