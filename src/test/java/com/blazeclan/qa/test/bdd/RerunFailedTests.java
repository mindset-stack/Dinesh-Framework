package com.blazeclan.qa.test.bdd;

import com.blazeclan.qa.reports.CucumberExtentReports;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

@CucumberOptions(features = {"@target/failedrerun.txt"},
        plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/MyReports/cucumber.json", "testng:target/MyReports/report.xml",
                "html:target/cucumber-html-report.html","rerun:target/failedRerun.txt"},
        monochrome = true,
//        dryRun = true,
        glue = {"com.blazeclan.qa.bdd.stepsdefinition","com.blazeclan.qa.bdd.hooks"})
public class RerunFailedTests extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void afterSuite() {
        CucumberExtentReports.cucumberReports();
//        String filePath = EmailUtil.prop.getProperty("ReportPath");
//        String fileName = EmailUtil.prop.getProperty("FileName");
//        EmailUtil.sendMail(filePath, fileName);
    }
}