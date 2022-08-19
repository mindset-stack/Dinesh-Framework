package com.blazeclan.qa.reports;


import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.*;

public class CucumberExtentReports extends CommonFunctions {

    public static void cucumberReports() {
        Properties prop = init_properties(IConstants.CUCUMBER_CONFIG_PATH);
        File reportOutputDirectory = new File(prop.getProperty("OutputDirectory"));
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/MyReports/cucumber.json");

        String buildNumber = prop.getProperty("BuildNumber");
        String projectName = prop.getProperty("ProjectName");
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);

        configuration.addClassifications("Browser", ((RemoteWebDriver)driver).getCapabilities().getBrowserName());
        configuration.addClassifications("Branch", prop.getProperty("Branch"));
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
//        configuration.setQualifier("sample", "Chrome 80, mobile");

        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

//        configuration.addCustomCssFiles(Collections.singletonList("src/test/resources/css/stackoverflow-light.min.css"));
//        configuration.addCustomJsFiles(Arrays.asList("src/test/resources/js/enable-highlighting.js", "src/test/resources/js/highlight.min.js"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
