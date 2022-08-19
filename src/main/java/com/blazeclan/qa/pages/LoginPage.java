package com.blazeclan.qa.pages;

import com.blazeclan.qa.base.CommonFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "input-email")
    public WebElement email;
    @FindBy(id = "input-password")
    public WebElement password;
    @FindBy(xpath = "//input[@value='Login']")
    public WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
