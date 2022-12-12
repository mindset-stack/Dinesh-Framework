package com.blazeclan.qa.test.bdd;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;
import com.blazeclan.qa.reports.CucumberExtentReports;
import com.blazeclan.qa.utility.JsonUtility;
import static com.blazeclan.qa.bdd.hooks.Hooks.testData;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Properties;


@CucumberOptions(features = {"src/main/java/com/blazeclan/qa/bdd/features"},
        plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/MyReports/cucumber.json", "testng:target/MyReports/report.xml",
        "html:target/cucumber-html-report.html","rerun:target/failedRerun.txt"},
        monochrome = true,
//        dryRun = false,
        glue = {"com.blazeclan.qa.bdd.stepsdefinition","com.blazeclan.qa.bdd.hooks"})
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeSuite
    public void setUp() {
        JsonUtility jsonUtility = new JsonUtility();
        testData = jsonUtility.readJson();
    }

    @AfterSuite
    public void afterSuite() {
        Properties prop = CommonFunctions.init_properties(IConstants.CUCUMBER_CONFIG_PATH);
        CucumberExtentReports.cucumberReports(prop.getProperty("OutputDirectory"));
//        String filePath = EmailUtil.prop.getProperty("ReportPath");
//        String fileName = EmailUtil.prop.getProperty("FileName");
//        EmailUtil.sendMail(filePath, fileName);
    }

////    @Override
////    @DataProvider(parallel = true)
////    public Object[][] scenarios() {
////        return super.scenarios();
////    }
}
