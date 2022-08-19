package com.blazeclan.qa.utility;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.logging.Log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrossBrowser extends CommonFunctions {

    public static WebDriver selectDriver(String browser) {

        switch (browser) {
            case "chrome": //chrome driver
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                Log.info("chrome browser is selected");

                driver = WebDriverManager.chromedriver().capabilities(options).create();
                break;
            case "firefox"://firefox driver
                Log.info("firefox browser is selected");
                driver = WebDriverManager.firefoxdriver().create();
                break;
            case "edge"://edge driver
                Log.info("edge browser is selected");
                driver = WebDriverManager.edgedriver().create();
                break;
            default: System.out.println("Please provide browser name");
        }

        DriverManager.setDriver(driver);

        return DriverManager.getDriver();
    }
}
