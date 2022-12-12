package com.blazeclan.qa.test.testng;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;
import com.blazeclan.qa.objectManager.ObjectManager;
import com.blazeclan.qa.pages.HomePage;
import com.blazeclan.qa.pages.LoginPage;
import com.blazeclan.qa.pages.RegisterPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseTest extends CommonFunctions {

    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginPage loginPage;
    @BeforeTest
    public void setUp() {
        Properties prop = init_properties(IConstants.TEST_CONFIG_PATH);
        invokeBrowser(prop.getProperty("BrowserName"));
        invokeApplicationURL(prop.getProperty("Url"));
        homePage = new HomePage(driver);
    }

    @AfterTest
    public void tearDown(){
        browserKill();
    }

    public static void main(String[] args) {

    }
}
