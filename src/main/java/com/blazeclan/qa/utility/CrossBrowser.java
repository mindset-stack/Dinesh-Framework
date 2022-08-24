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
                driver = WebDriverManager.chromedriver().capabilities(options).create();
                Log.info("chrome browser is selected");
                break;
            case "firefox"://firefox driver
                driver = WebDriverManager.firefoxdriver().create();
                Log.info("firefox browser is selected");
                break;
            case "edge"://edge driver
                driver = WebDriverManager.edgedriver().create();
                Log.info("edge browser is selected");
                break;
            default: System.out.println("Please provide browser name");
        }

        DriverManager.setDriver(driver);

        return DriverManager.getDriver();
    }
}
