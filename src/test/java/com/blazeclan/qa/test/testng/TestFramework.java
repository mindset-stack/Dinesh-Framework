package com.blazeclan.qa.test.testng;

import com.blazeclan.qa.pages.LoginPage;
import com.blazeclan.qa.utility.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(TestListener.class)
public class TestFramework extends BaseTest {


//    @Test
//    public void testFramework() {
//        String text = "apple";
//        takeScreenshotOfElement(homePage.searchBox, "./src/test/element", "searchBox");
//        enterText(homePage.searchBox, text);
//        Log.info("entered the tex");
//        clickElement(homePage.searchBtn);
//        String Url = getCurrentUrl();
//        String expected = "https://naveenautomationlabs.com/opencart/index.php?route=product/search&search=" + text;
//        Assert.assertEquals(Url, expected);
//    }
////
//    @Test(priority = 1)
//    public void openRegisterPage() {
//        clickElement(homePage.register);
//
//        String actualTitle = getCurrentUrl();
//        String expectedTitle = "nopCommerce demo store. Register";
//
//        Assert.assertEquals(actualTitle, expectedTitle);
//    }

//    @Test
//    public void navigateToRegisterPage() {
//        clickElement(homePage.myAccount);
//        registerPage = (RegisterPage) clickNavigateToNewPage(new RegisterPage(driver), homePage.register);
//        String expected = "http://tutorialsninja.com/demo/index.php?route=account/register";
//        Assert.assertEquals(getCurrentUrl(), expected);
//    }
//
//    @Test(priority = 1)
//    public void registerToApplication() {
//        enterText(registerPage.fName, "Dinesh");
//        enterText(registerPage.lName, "Kumar");
//        enterText(registerPage.email, "dineshkumar.icon.dk@gmail.com");
//        enterText(registerPage.mNumber, "9542409637");
//        enterText(registerPage.password, "1234567890");
//        enterText(registerPage.confirmPass, "1234567890");
//        clickElement(registerPage.agree);
//        clickElement(registerPage.continueBtn);
//        String actualText = getText(registerPage.successFullMsg);
//        String expectedText ="Your Account Has Been Created!";
//        Assert.assertEquals(actualText, expectedText);
//    }
//
    @Test(priority = 2)
    public void navigateToLoginPage() {
        moveAndClick(homePage.myAccount);

        loginPage = (LoginPage) clickNavigateToNewPage( new LoginPage(driver),homePage.loginBtn);
        waitForLoading();
        String expectedUrl = "https://naveenautomationlabs.com/opencart/index.php?route=account/login";
        String actualUrl = getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test(priority = 3)
    public void loginToApplication() {
        clearAllCookies();
        enterText(loginPage.loginEmail,"dineshkumar.icon.dk@gmail.com");
        enterText(loginPage.loginPassword, "Dinnu@4318");
        clickElement(loginPage.loginBtn);
        addCookiesToFile("D:\\Framework\\BlazeclanAutomationFramework", "cookies");
        waitForLoading();
        String actualTitle = getTitle();
        String expectedTitle = "My Account";
        Assert.assertEquals(actualTitle,expectedTitle);
    }

}
