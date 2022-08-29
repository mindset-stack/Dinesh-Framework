package com.blazeclan.qa.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setDriver(WebDriver driver) {
		DriverManager.driver.set(driver);
	}

	public static WebDriverWait getWebDriverWait() {
		return wait.get();
	}

	public static void setWebDriverWait(WebDriverWait wait) {
		DriverManager.wait.set(wait);
	}
}
