package com.blazeclan.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    @FindBy(id = "input-firstname")
    public WebElement fName;
    @FindBy(id = "input-lastname")
    public WebElement lName;
    @FindBy(id = "input-email")
    public WebElement email;
    @FindBy(id = "input-telephone")
    public WebElement mNumber;
    @FindBy(id = "input-password")
    public WebElement password;
    @FindBy(id = "input-confirm")
    public WebElement confirmPass;
    @FindBy(xpath = "//input[@name='agree']")
    public WebElement agree;
    @FindBy(css = "div[id='content'] h1")
    public WebElement successFullMsg;
    @FindBy(xpath = "//input[@value='Continue']")
    public WebElement continueBtn;

    public RegisterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

}
