package com.blazeclan.qa.bdd.stepsdefinition;

import com.blazeclan.qa.objectManager.ObjectManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class Home extends ObjectManager {


    @Given("When user open Homepage")
    public void whenUserOpenHomepage() {
        System.out.println("user is in login page");
    }

    @When("User search for {string} product")
    public void userSearchForProduct(String arg0) {
        enterText(homePage.searchBox, arg0);
        clickElement(homePage.searchBtn);
    }

    @Then("Navigate to Searched Product Page url as {string}")
    public void navigateToSearchedProductPageUrlAs(String arg0) {
        Assert.assertEquals(getCurrentUrl(),arg0);
    }
}
