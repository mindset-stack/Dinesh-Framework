package com.blazeclan.qa.test.testng;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.constants.IConstants;
import com.blazeclan.qa.pages.HomePage;
import com.blazeclan.qa.pages.LoginPage;
import com.blazeclan.qa.pages.RegisterPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest extends CommonFunctions {

    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginPage loginPage;
    @BeforeTest
    public void setUp() {
        invokeApplicationURL(init_properties(IConstants.TEST_CONFIG_PATH).getProperty("BrowserName"), init_properties(IConstants.TEST_CONFIG_PATH).getProperty("Url"));
        homePage = new HomePage(driver);
    }

    @AfterTest
    public void tearDown(){
        browserKill();
    }
}
