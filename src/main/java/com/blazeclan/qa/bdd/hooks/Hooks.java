package com.blazeclan.qa.bdd.hooks;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;
import io.cucumber.java.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class Hooks extends CommonFunctions {

    public static Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        Hooks.scenario = scenario;
            Properties prop = init_properties(IConstants.TEST_CONFIG_PATH);
            invokeApplicationURL(prop.getProperty("BrowserName"), prop.getProperty("Url"));
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
            scenario.attach(fileContent, "image/png", scenario.getName());
        }

            browserKill();
    }
}
