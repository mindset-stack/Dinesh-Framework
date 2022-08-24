package com.blazeclan.qa.bdd.stepsdefinition;

import com.blazeclan.qa.base.CommonFunctions;
import com.blazeclan.qa.pages.HomePage;
import com.blazeclan.qa.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class Login extends CommonFunctions {
    HomePage homePage = new HomePage(driver);
    @Given("When user open login page")
    public void whenUserOpenLoginPage() {
        clickElement(homePage.myAccount);
        clickElement(homePage.loginBtn);
    }

    @When("User login to application with {string} and {string} credentials")
    public void userLoginToApplicationWithAndCredentials(String arg0, String arg1) {
        LoginPage loginPage =  new LoginPage(driver);
        enterText(loginPage.loginEmail,arg0);
        enterText(loginPage.loginPassword, arg1);
        clickElement(loginPage.loginBtn);
    }

    @Then("Navigate to Home Page title as {string}")
    public void navigateToHomePageTitleAs(String arg0) {
        Assert.assertEquals(getTitle(), arg0);
    }
}
