package com.blazeclan.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(xpath = "//input[@placeholder='Search']")
    public WebElement searchBox;
    public By searchBtn = By.xpath("//div[@id='search']/span");
    @FindBy(xpath = "//span[normalize-space()='My Account']")
    public WebElement myAccount;
    @FindBy(xpath= "(//a[normalize-space()='Register'])[1]")
    public WebElement register;
    @FindBy(xpath = "(//a[normalize-space()='Login'])[1]")
    public WebElement loginBtn;
    By home = By.xpath("");

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

}
