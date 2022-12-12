package com.blazeclan.qa.objectManager;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.pages.HomePage;
import com.blazeclan.qa.pages.LoginPage;

public class ObjectManager extends CommonFunctions {

   public HomePage homePage = new HomePage(driver);
   public LoginPage loginPage =  new LoginPage(driver);
}
