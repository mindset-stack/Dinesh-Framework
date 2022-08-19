package com.blazeclan.qa.bdd.hooks;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.reports.CucumberExtentReports;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.*;
import org.testng.annotations.AfterSuite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Hooks extends CommonFunctions {

    @Before
    public void setUp() {
        invokeApplicationURL("chrome", "https://naveenautomationlabs.com/opencart/");
        waitForLoading();
    }

    @AfterStep
    public void after(Scenario scenario) throws IOException {
        File fi = new File(captureScreenshot(scenario.getName(), "stepScreenshot"));
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        scenario.attach(fileContent, "image/png", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.getStatus().equals(Status.FAILED)) {
            File fi = new File(captureScreenshot(scenario.getName(), "failed"));
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            scenario.attach(fileContent, "image/png", scenario.getName());
        } else if (scenario.getStatus().equals(Status.SKIPPED)){
            File fi = new File(captureScreenshot(scenario.getName(), "skipped"));
            byte[] fileContent = Files.readAllBytes(fi.toPath());
            scenario.attach(fileContent, "image/png", scenario.getName());
        } else {
            File fi = new File(captureScreenshot(scenario.getName(), "passed"));
            byte[] fileContent = Files.readAllBytes(fi.toPath());
        }

        browserKill();
    }


}
