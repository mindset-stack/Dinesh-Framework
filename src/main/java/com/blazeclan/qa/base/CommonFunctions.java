/**
 * @author Dinesh Kumar Peddakotla
 * @role QA Engineer
 * @since 27/06/2022
 */

package com.blazeclan.qa.base;

import com.blazeclan.qa.logging.Log;
import com.blazeclan.qa.utility.CrossBrowser;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.*;

public abstract class CommonFunctions {
    public static final int OBJ_SYNC_TIME = 2;
    public static final int PAGE_SYNC_TIME = 10;
    public static final int OBJ_SYNC_FAST_TIME = 0;
    public static final int ELEMENT_LOAD_TIME = 5;
    public static final int PAGE_LOAD_OUT_TIME_SEC = 10;
    public static WebDriver driver;
    private String parentWindowHandle;
    private String childWindowHandle;

    /**
     * MethodName : getDate
     * Description : To get the date in yyyy_MM_dd_hh_mm_ss format as a string
     * @return : String
     */
    public String getDate() {
        return new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
    }

    /**
     * @param browserName : it's name of a selected browser
     * @param url:        url of a testing application
     * MethodName : invokeApplicationURL
     * Description : this method open a selected browser and open an application in it
     */
    public static void invokeApplicationURL(String browserName, String url) {
        driver = CrossBrowser.selectDriver(browserName);
        Log.info("Open " + browserName + " browser");
        driver.get(url);
        Log.info("Open the url" + url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(PAGE_LOAD_OUT_TIME_SEC));
    }

    /**
     * MethodName : waitForLoading
     * Description : it's wait for application to load for certain period of time
     */
    public void waitForLoading() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_OUT_TIME_SEC));
            wait.until((ExpectedCondition<Boolean>) driver -> {
                assert driver != null;
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            });

            if (driver.getTitle().contains("unavailable"))
                Log.warn("Page is unavailable");
        } catch (Exception e) {
            Log.error(e.getMessage() + " " + driver.getCurrentUrl());
        }
    }

    /**
     * @return WebElement
     * MethodName : createWebElement
     * Description : to create web element using a locator
     */
    public WebElement createWebElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * @param element: web element used as a parameter to click
     * MethodName : clickElement
     * Description : to click on particular element in web application
     */
    public void clickElement(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_OUT_TIME_SEC));
            wait.until(ExpectedConditions.visibilityOf(element));
            Log.info("wait for until element " + element.toString() + " is visible");
            element.click();
            Log.info("Click on element: " + element);

        } catch (WebDriverException e) {
            Log.error(e.getMessage());
            Log.error("Fail to clicked on " + element.toString());
        }
    }

    /**
     * @param locator : locator is passed to create web element
     * MethodName : clickElement
     * Description : to click on particular element in web application by passing locator to create a Web element
     **/
    public void clickElement(By locator) {
        WebElement elm = createWebElement(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));

            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            elm.click();
            Log.info("Click on element: " + elm);
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString() + "  " + e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to find a web element
     * MethodName : verifyDisplayAndEnableAndClick
     * Description : it can verify the web element is displayed and enabled before click
     **/
    public void verifyDisplayAndEnableAndClick(By locator) {
        WebElement elm = createWebElement(locator);
        Log.info("Web element identified using this locator : " + locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));
            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isEnabled() && elm.isDisplayed()) {
                Log.info("element " + elm + " is enabled and displayed");
                elm.click();
                Log.info("Click on element: " + elm);
            }
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString() + "  " + e.getMessage());
        }
    }

    /**
     * @param elm : web element
     * MethodName : verifyDisplayAndEnableAndClick
     * Description : it can verify the web element is displayed and enabled before click
     **/
    public void verifyDisplayAndEnableAndClick(WebElement elm) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));

            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isEnabled() && elm.isDisplayed()) {
                Log.info("element " + elm + " is enabled and displayed");
                elm.click();
                Log.info("Click on element: " + elm);
            }
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString());
        }
    }

    /**
     * @param elm : Web element
     * MethodName : verifyDisplayedAndClick
     * Description : it can verify the web element is displayed before click
     **/
    public void verifyDisplayedAndClick(WebElement elm) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));
            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isDisplayed()) {
                Log.info("element : " + elm + " is displayed");
                elm.click();
                Log.info("Click on element: " + elm);
            }
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString());
        }
    }

    /**
     * @param locator : locator is used to find a web element
     * MethodName : verifyDisplayedAndClick
     * Description : it can verify the web element is displayed before click
     **/
    public void verifyDisplayedAndClick(By locator) {
        WebElement elm = createWebElement(locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));
            //Thread.sleep(3000);
            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isDisplayed()) {
                Log.info("element : " + elm + " is displayed");
                elm.click();
                Log.info("Click on element: " + elm);
            }

        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString());
        }
    }

    /**
     * @param elm : web element
     * MethodName : verifyEnabledAndClick
     * Description : it can verify the web element is enabled before click
     **/
    public void verifyEnabledAndClick(WebElement elm) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));
            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isEnabled()) {
                Log.info("element : " + elm + " is enabled");
                elm.click();
                Log.info("Click on element: " + elm);
            }
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString());
        }
    }

    /**
     * @param locator : locator is used to find a web element
     * MethodName : verifyEnabledAndClick
     * Description : it can verify the web element is enabled before click
     **/
    public void verifyEnabledAndClick(By locator) {
        WebElement elm = driver.findElement(locator);
        Log.info("element is identified using a locator : " + locator);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(OBJ_SYNC_TIME));
            wait.until(ExpectedConditions.visibilityOf(elm));
            Log.info("wait for until element " + elm.toString() + " is visible");
            if (elm.isEnabled()) {
                Log.info("element : " + elm + " is enabled");
                elm.click();
                Log.info("Click on element: " + elm);
            }
        } catch (WebDriverException e) {
            Log.error("Fail to clicked on " + elm.toString());
        }
    }


    /**
     * @param element        : web element of application
     * @param filePath       : location to save a screenshot
     * @param screenshotName : name of screenshot
     * MethodName : takeScreenshotOfElement
     * Description : it can take screenshot of a particular element and save as a given name at  a given file path.
     */
    public void takeScreenshotOfElement(@NotNull WebElement element, String filePath, String screenshotName) {
        File scrFile = element.getScreenshotAs(OutputType.FILE);
        takeScreenshotOfElement(scrFile, filePath, screenshotName);
    }

    /**
     * @param locator        : web element of application
     * @param filePath       : location to save a screenshot
     * @param screenshotName : name of screenshot
     * MethodName : takeScreenshotOfElement
     * Description : it can take screenshot of a particular element and save as a given name at  a given file path.
     */
    public void takeScreenshotOfElement(By locator, String filePath, String screenshotName) {
        File scrFile = createWebElement(locator).getScreenshotAs(OutputType.FILE);
        takeScreenshotOfElement(scrFile, filePath, screenshotName);
    }

    /**
     * @param scrFile    : screenshot is saved as file and passed as src file into this method
     * @param filePath   : location of a file path to save a screenshot
     * @param elmImgName : name of the screenshot of element
     * MethodName : takeScreenshotOfElement
     * Description : it is used to take screenshot of particular element and save as a png file at given location
     * with a given name
     **/
    private void takeScreenshotOfElement(File scrFile, String filePath, String elmImgName) {
        try {
            Log.info("Element screenshot is taken");
            FileUtils.copyFile(scrFile, new File(filePath + "/" + elmImgName + getDate() + ".png"));
            Log.info("screenshot is created at a location " + filePath + "with name as " + elmImgName);
        } catch (IOException e) {
            Log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param locator  : locator is used to identify the element
     * @param expected : expected data to enter
     * MethodName : verifyDisplayedAndEnterText
     * Description : it's verify the element text box is displayed and enter the text
     */
    public void verifyDisplayedAndEnterText(By locator, String expected) {
        WebElement element = driver.findElement(locator);
        Log.info("element is created using a locator : " + locator);
        isDisplayedAndEnterText(element, expected);
    }

    /**
     * @param element : web element of application
     * @param text    : text to enter the filed
     * MethodName : verifyDisplayedAndEnterText
     * Description : it's verify the text filed element is displayed or not and then enter the text
     **/
    public void verifyDisplayedAndEnterText(WebElement element, String text) {
        isDisplayedAndEnterText(element, text);
    }

    /**
     * @param element : web element of application
     * @param text    : text to enter the filed
     * MethodName : isDisplayedAndEnterText
     * Description : it's verify the text filed element is displayed or not and then enter the text
     **/
    private void isDisplayedAndEnterText(WebElement element, String text) {
        try {
            if (element.isDisplayed()) {
                Log.info("element : " + element + " is displayed");
                enterText(element, text);
            }
        } catch (WebDriverException e) {
            Log.error("Failed to write on " + element);
        }
    }

    /**
     * @param element : web element of application
     * @param text    : text to enter the filed
     * MethodName : enterText
     * Description : it's enter the text in text box
     **/
    public void enterText(WebElement element, String text) {
        try {
            element.clear();
            Log.info("Text in element : " + element + " is cleared");
            element.click();
            Log.info("Click on Text Box: " + element);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            element.sendKeys(text);
            Log.info("Enter text on " + element + " :" + text);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the element
     * @param text    : text to enter the filed
     * MethodName : enterText
     * Description : it's enter the text in text box
     **/
    public void enterText(By locator, String text) {
        try {
            WebElement element = driver.findElement(locator);
            element.clear();
            Log.info("Text in element : " + element + " is cleared");
            element.click();
            Log.info("Click on Text Box: " + element);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(OBJ_SYNC_TIME));
            element.sendKeys(text);
            Log.info("Enter text on " + element + " :" + text);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }


    /**
     * @param element  : locator is used to identify the
     * @param expected : text to enter the filed
     * MethodName : verifyDisplayedAndEnabledEnterText
     * Description : it's enter the text in text box
     **/
    public void verifyDisplayedAndEnabledEnterText(WebElement element, String expected) {
        isDisplayedAndEnabledEnterText(element, expected);
    }

    /**
     * @param locator : locator is used to identify the web element
     * @param text    : text to enter the filed
     * MethodName : verifyDisplayedAndEnabledEnterText
     * Description : it's enter the text in text box
     **/
    public void verifyDisplayedAndEnabledEnterText(By locator, String text) {
        WebElement element = createWebElement(locator);
        isDisplayedAndEnabledEnterText(element, text);
    }

    /**
     * @param element  : locator is used to identify the web element
     * @param expected : text to enter the filed
     * MethodName : isDisplayedAndEnabledEnterText
     * Description : it's enter the text in text box
     **/
    private void isDisplayedAndEnabledEnterText(WebElement element, String expected) {
        try {
            if (element.isDisplayed() && element.isEnabled()) {
                Log.info("Element is enabled and displayed to enter the text in : " + element);
                enterText(element, expected);
            }
        } catch (WebDriverException e) {
            Log.error("Failed to write on " + element);
        }
    }

    /**
     * @param element : web element
     * MethodName : waitForElementLoad
     * Description : it's wait for element to load for certain period of time
     */
    private void waitForElementLoad(WebElement element, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
            wait.until(driver -> element);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * MethodName : waitForElementLoading
     * Description : it's wait for element to load for certain period of time
     */
    public void waitForElementLoading(WebElement element) {
        waitForElementLoad(element, ELEMENT_LOAD_TIME);
    }

    /**
     * @param element      : web element
     * @param intervalTime : interval time to wait for element loading
     * MethodName : waitForElementLoading
     * Description : it's wait for element to load for certain period of time
     */
    public void waitForElementLoading(WebElement element, int intervalTime) {
        waitForElementLoad(element, intervalTime);
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : waitForElementLoading
     * Description : it's wait for element to load for certain period of time
     */
    public void waitForElementLoading(By locator) {
        WebElement element = createWebElement(locator);
        waitForElementLoad(element, ELEMENT_LOAD_TIME);
    }

    /**
     * @param locator      : locator is used to identify the web element
     * @param intervalTime : interval time to wait for element loading
     * MethodName : waitForElementLoading
     * Description : it's wait for element to load for certain period of time
     */
    public void waitForElementLoading(By locator, int intervalTime) {
        WebElement element = createWebElement(locator);
        waitForElementLoad(element, intervalTime);
    }

    /**
     * @param element : web element
     * @param text    : text to enter
     * MethodName : enterTextUsingJS
     * Description : it's used the javascript executor to enter the text on element
     */
    public void enterTextUsingJS(WebElement element, String text) {
        jsExecute("arguments[0].value='" + text +"';", element);
        Log.info("element : " + element + "is entered the text using js");
    }

    /**
     * @param locator : locator is used to identify the web element
     * @param text    : text to enter
     * MethodName : enterTextUsingJS
     * Description : it's used the javascript executor to enter the text on element
     */
    public void enterTextUsingJS(By locator, String text) {
        jsExecute("arguments[0].value='" + text+"';", createWebElement(locator));
        Log.info("locator : " + locator + "is entered the text using js");
    }

    /**
     * @param element : web element
     * MethodName : scrollToElement
     * Description : it's used the javascript executor to scroll into the view of a particular element
     */
    public void scrollToElement(WebElement element) {
        jsExecute("arguments[0].scrollIntoViewIfNeeded()", element);
        Log.info("scroll to the element " + element);
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : scrollToElement
     * Description : it's used the javascript executor to scroll into the view of a particular element
     */
    public void scrollToElement(By locator) {
        jsExecute("arguments[0].scrollIntoViewIfNeeded()", createWebElement(locator));
        Log.info("scroll to the element using locator " + locator);
    }

    /**
     * @param element : web element
     * MethodName : clickUsingJS
     * Description : it's used the javascript executor to click on element
     */
    public void clickUsingJS(WebElement element) {
        jsExecute("arguments[0].click();", element);
        Log.info("element: " + element + "is clicked");
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : clickUsingJS
     * Description : it's used the javascript executor to click on element
     */
    public void clickUsingJS(By locator) {
        jsExecute("arguments[0].click();", createWebElement(locator));
        Log.info("element by using this " + locator + " is created is is clicked");
    }

    /**
     * MethodName : scrollBottomOfPage
     * Description : this method is used to scroll the web page to the bottom of the page
     */
    public void scrollBottomOfPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight");
        Log.info("scroll to the bottom of page");
    }

    /**
     * @param script  : javascript  query is used.
     * @param element : web element
     * MethodName : jsExecute
     * Description : its execute javascript
     **/
    public void jsExecute(String script, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(script, element);
        Log.info("executed the javascript" + script + "with the element " + element);
    }

    /**
     * @param cookieName : name of a particular cookie
     * MethodName : deleteCookieByName
     * Description : it's used to delete a cookie by a name
     */
    public void deleteCookieByName(String cookieName) {
        driver.manage().deleteCookieNamed(getCookieByName(cookieName));
        Log.info("delete the cookie by its name : " + cookieName);
    }

    /**
     * @param cookieName : name of the cookie
     * @return cookie as a string
     * MethodName : getCookieByName
     * Description : it gets the particular cookie by its name from the list of cookies
     */
    public String getCookieByName(String cookieName) {
        String cookieNamed = "";
        try {
            Set<Cookie> cookList = driver.manage().getCookies();
            Log.info(cookList.toString());
            Log.info(String.valueOf(cookList.size()));
            for (Cookie cookie : cookList) {
                if (cookie.getName().equals(cookieName)) {
                    Log.info("cookie name in the list is matched with : " + cookieName);
                    cookieNamed = driver.manage().getCookieNamed(cookieName).toString();
                    Log.info(cookie.getName() + " is deleted");
                }
            }
        } catch (WebDriverException e) {
            Log.error(e.getMessage() + "cookie is not matched");
        }

        return cookieNamed;
    }

    /**
     * MethodName : clearAllCookies
     * Description : it's clears all cookies data
     */
    public void clearAllCookies() {
        try {
            driver.manage().deleteAllCookies();
            Log.info("deleted all cookies");

        } catch (Exception e) {
            Log.error("Failed to delete cookies");
            Log.error(e.getMessage());
        }
    }

    /**
     * @param ele : web element
     * MethodName : moveAndClick
     * Description : it's uses action class to move to a particular element and click it.
     */
    public void moveAndClick(WebElement ele) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(ele).click().perform();
            Log.info("move to the element : " + ele + " is clicked");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : moveToElement
     * Description : movie to element after its visible
     */
    public void moveToElement(By locator) {
        WebElement element = createWebElement(locator);
        Log.info("web element is identified: " + element);
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).build().perform();
            Log.info("movie to the element by using this locator : " + locator);
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * @param x       : x-axis location in web page
     * @param y       : y-axis location in web page
     * MethodName : moveAndClickOnLocation
     * Description : move to the particular element using the x and y location and click it
     */
    public void moveAndClickOnLocation(WebElement element, int x, int y) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element, x, y).click().build().perform();
            Log.info("move to the element : " + element + "using the " + x + " " + y + " location on web Page");

        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * MethodName : ClickElementUsingActionClass
     * Description : it's uses action class to move to a particular element and click it.
     */
    public void ClickElementUsingActionClass(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.click(element).build().perform();
            Log.info("click on web element : " + element + " using action class");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }

    }

    /**
     * @param element :  web element in application
     * MethodName : clickAndHold
     * Description : it's used to click and hold an element in web page
     */
    public void clickAndHold(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).perform();
            Log.info("click and hold on web element : " + element + " using action class");
        } catch (WebDriverException e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element : web element in application
     * MethodName : moveToEleAndDoubleClick
     * Description : it's used to move to a particular element and double on that web element
     */
    public void moveToEleAndDoubleClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).doubleClick().perform();
            Log.info("move and double click on web element : " + element + " using action class");
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }

    /**
     * @param element :web element in application
     * MethodName : doubleClickElement
     * Description : it's used to double-click on a particular web element
     */
    public void doubleClickElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
            Log.info("double click on web element : " + element + " using action class");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param source      : source of the web element to drag
     * @param destination : destination of the web element to drop
     * MethodName : dragAndDrop
     * Description : it's used to drag web element from one location to other location in web element
     */
    public void dragAndDrop(WebElement source, WebElement destination) {
        try {
            Actions actions = new Actions(driver);
            actions.dragAndDrop(source, destination).perform();
            Log.info("web element is drops from " + source + " to " + destination);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param source  : source of the web element to drag
     * @param xOffset : x-axis offset of destination of the web element to drop
     * @param yOffset : y-axis offset of destination of the web element to drop
     * MethodName : dragAndDropByLocation
     * Description : it's used to drag web element from one location to other location in web element
     */
    public void dragAndDropByLocation(WebElement source, int xOffset, int yOffset) {
        try {
            Actions actions = new Actions(driver);
            actions.dragAndDropBy(source, xOffset, yOffset);
            Log.info("web element drag from source : " + source + " to xOffset : " + xOffset + " to yOffset " + yOffset);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * @return x-axis location in integer
     * MethodName : getXAxisLocation
     * Description : get x-axis location of the web element
     */
    public int getXAxisLocation(WebElement element) {
        return element.getLocation().x;
    }

    /**
     * @param element : web element
     * @return y-axis location in integer
     * MethodName : getYAxisLocation
     * Description : get y-axis location of the web element
     */
    public int getYAxisLocation(WebElement element) {
        return element.getLocation().y;
    }

    /**
     * @param element : web element
     * @return x-axis and y-axis location as a point
     * MethodName : getLocation
     * Description : get x-axis and y-axis location in a point of the web element
     */
    public Point getLocation(WebElement element) {
        return element.getLocation();
    }

    /**
     * @param locator : locator is used to find web element
     * @return x-axis location in integer
     * MethodName : getXAxisLocation
     * Description : get x-axis location of the web element
     */
    public int getXAxisLocation(By locator) {
        return createWebElement(locator).getLocation().x;
    }

    /**
     * @param locator : locator is used to find web element
     * @return y-axis location in integer
     * MethodName : getYAxisLocation
     * Description : get y-axis location of the web element
     */
    public int getYAxisLocation(By locator) {
        return createWebElement(locator).getLocation().y;
    }

    /**
     * @param locator : locator is used to find web element
     * @return x-axis and y-axis location as a point
     * MethodName : getLocation
     * Description : get x-axis and y-axis location in a point of the web element
     */
    public Point getLocation(By locator) {
        return createWebElement(locator).getLocation();
    }

    /**
     * @param locator : locator is used to identify the web element
     * @param x       : x-axis location on the web page
     * @param y       : y-axis location on the web page
     * MethodName : moveAndClickOnLocation
     * Description : it's used to move to the element using x and y point and then its click it
     */
    public void moveAndClickOnLocation(By locator, int x, int y) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element, x, y).click().build().perform();
            Log.info("move to element " + element + "to " + x + " and " + y + " location and click on it");

        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : ClickElementUsingActionClass
     * Description : it's used action class to click on element
     */
    public void moveAndClickEle(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.click(element).build().perform();
            Log.info("click on the element : " + element);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : clickAndHold
     * Description : it's used to click and hold a particular element
     */
    public void clickAndHold(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.clickAndHold(element).perform();
            Log.info("click and the element : " + element);
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * MethodName : releaseElement
     * Description : it's used to release the hold element
     */
    public void releaseElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.release(element);
            Log.info("releases the element : " + element);
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify web element
     * MethodName : releaseElement
     * Description : it's used to release the hold element
     */
    public void releaseElement(By locator) {
        try {
            WebElement element = createWebElement(locator);
            Actions actions = new Actions(driver);
            actions.release(element);
            Log.info("releases the element : " + locator);
        } catch (Exception e) {
            Log.info(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify web element
     * MethodName : moveToEleAndDoubleClick
     * Description : it's used to move to a particular element and double on that web element
     */
    public void moveToEleAndDoubleClick(By locator) {
        try {
            WebElement element = createWebElement(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).doubleClick().perform();
            Log.info("move to " + element + " and double click on it");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the web element
     * MethodName : doubleClickElement
     * Description : it's uses to double-click on the web element
     */
    public void doubleClickElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
            Log.info("double click on web element : " + element);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param source      : it's a locator is used to identify the web element
     * @param destination : it's a locator is used to identify the web element
     * MethodName : dragAndDrop
     * Description : it's used to drag from source to drop at destination location
     */
    public void dragAndDrop(By source, By destination) {
        try {
            WebElement sou = createWebElement(source);
            WebElement dest = createWebElement(destination);
            Actions actions = new Actions(driver);
            actions.dragAndDrop(sou, dest).perform();
            Log.info("drag from " + sou + "to drop at destination : " + dest);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : locator is used to identify the web element
     * @param xOffset : off set to x-axis point
     * @param yOffset : off set to y-axis point
     * MethodName : dragAndDropByOffsetLocation
     * Description : it's used to drag from source to drop at destination offset of x-axis and y-axis location
     */
    public void dragAndDropByOffsetLocation(By locator, int xOffset, int yOffset) {
        try {
            WebElement source = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.dragAndDropBy(source, xOffset, yOffset);
            Log.info("drag from " + source+ " to drop at " +xOffset+ " and "+yOffset);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }


    /**
     * @param locator : locator is used to identify web element
     * @param timeout : time out in seconds
     * @return WebElement
     * MethodName :waitUntilClickable Method
     * Description :To wait until the element is clickable and return WebElement Input Parameters :By locator,
     */
    public WebElement waitUntilClickable(By locator, int timeout) {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            Log.info("wait until element is clickable : " +element.toString());
            return element;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param locator : By locator
     * @param CSS     : String CSS
     * @return : string
     * MethodName :getCSS Method
     * Description :To retrieve text from web element CSSValue
     */
    public String getCSS(By locator, String CSS) {
        try {
            return createWebElement(locator).getCssValue(CSS).trim();
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;

        }
    }

    /**
     * @param element : Web element,
     * @param timeout :int timeout
     * @return WebElement
     * MethodName :waitUntilVisible Method
     * Description :To wait until the element is visible and return WebElement Input Parameters :By
     */
    public WebElement waitUntilVisibleElement(WebElement element, int timeout) {
        WebElement ele;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
             ele = wait.until(ExpectedConditions.visibilityOf(element));
            Log.info("wait until element is visible : " +element.toString());
            return ele;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param locator : By method to identify the web element
     * @param timeout : int timeout in seconds
     * @return WebElement
     * MethodName :waitUntilVisible Method
     * Description :To wait until the element is visible and return WebElement
     */
    public WebElement waitUntilVisible(By locator, int timeout) {
        WebElement element;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Log.info("wait until visibility of element : "+element);
            return element;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param element : Web element
     * @param timeout : timeout in seconds int
     * @return WebElement
     * MethodName : waitUntilVisible
     * Description : it's used to wait for certain period time until element visible
     */
    public WebElement waitUntilVisible(WebElement element, int timeout) {
        WebElement ele;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            ele = wait.until(ExpectedConditions.visibilityOf(element));
            Log.info("wait for " + timeout + " this seconds until element to visible : " + ele);
            return ele;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }


    /**
     * @param locator : locator is used to identify web element,
     * @param timeout : int timeout in seconds
     * @return boolean
     * MethodName :waitUntilInvisible Method
     * Description :To wait until the element is Invisible and return boolean
     */
    public Boolean waitUntilInvisible(By locator, int timeout) {
        try {
            boolean bool;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            bool = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            Log.info("wait until web element is invisible: " + locator);
            return  bool;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param locator : By method locator to find web element
     * @param timeout : int timeout
     * @return WebElement
     * MethodName :waitElementPresence Method
     * Description :To wait until the element is present and return web element
     */
    public WebElement waitElementPresence(By locator, int timeout) {
        WebElement element;
        try {
            //
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            Log.info("wait until the element is present : " +element);
            return  element;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param locator : By locator
     * @return boolean value
     * MethodName :waitElementPresence Method
     * Description :To wait until the element is invisible and return boolean
     */
    public Boolean waitElementInvisibility(By locator) {
        try {
            WebElement ele = driver.findElement(locator);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
             boolean bool = wait.until(ExpectedConditions.invisibilityOf(ele));
            Log.info("wait until element is invisible: "+ ele);
            return  bool;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }


    /**
     * @param locator :By locator
     * @return boolean
     * MethodName :waitElementToSelected
     * Description :To wait until the element is selected and return boolean
     */
    public Boolean waitElementToSelected(By locator) {
        try {
            //
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            boolean bool = wait.until(ExpectedConditions.elementToBeSelected(locator));
            Log.info("wait until web element is selected : "+ bool);
            return bool;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param element :By locator
     * @return boolean
     * MethodName :waitElementToSelected
     * Description :To wait until the element is selected and return boolean
     */
    public Boolean waitElementToSelected(WebElement element) {
        try {
            //
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            boolean bool = wait.until(ExpectedConditions.elementToBeSelected(element));
            Log.info("wait until element is selected : "+ bool);
            return bool;
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param elements : List of WebElements
     * @return List<WebElement>
     * MethodName :waitListOfAllElementsVisible
     * Description :To wait until the list of elements are  visible and return list of elements
     */
    public List<WebElement> waitListOfAllElementsVisible(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<WebElement> elementsList = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        Log.info("wait until list of web elements are visible");
        return elementsList;
    }

    /**
     * @param locator : locator is used to identify List of WebElements
     * @return List<WebElement>
     * MethodName :waitListOfAllElementsVisible
     * Description :To wait until the list of elements are  visible and return list of elements
     */
    public List<WebElement> waitListOfAllElementsVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    /**
     * @param locator : locator
     * @return boolean
     * MethodName : isListOFElementsDisplayedOnScreen
     * Description : it's used to identify the list of web elements isDisplayed and return boolean
     */
    public boolean isListOFElementsDisplayedOnScreen(By locator) {

        boolean blnDisplay = false;
        try {
            sleep(3);
            List<WebElement> weList = driver.findElements(locator);
            if (weList.size() > 0) {
                blnDisplay = true;
                Log.info("List of web elements displayed on the screen");
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return blnDisplay;
    }

    /**
     * @param locator : By locator
     * @param text    :String text
     * MethodName :typeFilteredValue
     * Description :To enter text in the elementLocator
     */
    public void typeFilteredValue(By locator, String text) {
        enterText(locator, text);
        sleep(3);
        pressTab(locator);
    }

    /**
     * @param text     : String text to enter
     * @param locator  : By locator is used to find web element
     * @param locator2 : By locator2 is used to find web element
     * MethodName : typeValueAndClickOnScreen
     * Description :Enter text and click on screen
     */

    public void typeValueAndClickOnScreen(By locator, By locator2, String text) {
        enterText(locator, text);
        clickElement(locator2);
    }


    /**
     * @param locator : By locator
     * @param text    : String text
     * MethodName :selectDropDownVisibleText
     * Description : it's used to select the dropdown by using visible text
     */
    public void selectDropDownVisibleText(By locator, String text) {

        WebElement element = createWebElement(locator);
        selectOption(element).selectByVisibleText(text);
        Log.info("select drop down" + element + " list element by visible text " + text);
    }

    /**
     * @param element : web element
     * @param text    : String text
     * MethodName :selectDropDownVisibleText
     * Description : it's used to select the dropdown by using visible text
     */
    public void selectDropDownVisibleText(WebElement element, String text) {
        selectOption(element).selectByVisibleText(text);
        Log.info("select drop down" + element + " list element by visible text " + text);
    }

    /**
     * @param element : web element
     * @return Select class
     * MethodName : selectOption
     * Description : it's used to create section class object
     */
    private Select selectOption(WebElement element) {
        return new Select(element);
    }

    /**
     * @param locator : By locator
     * @param value   : String text
     * MethodName : selectDropDownByValue Method
     * Description : To select the list by value
     */
    public void selectDropDownByValue(By locator, String value) {

        WebElement element = driver.findElement(locator);
        selectOption(element).selectByValue(value);
        Log.info("select the drop down " + element + " list by value : " + value);
    }

    /**
     * @param element : WebElement
     * @param value   : String text
     * MethodName : selectDropDownByValue Method
     * Description : To select the list by value
     */
    public void selectDropDownByValue(WebElement element, String value) {
        selectOption(element).selectByValue(value);
        Log.info("select the drop down " + element + " list by value : " + value);
    }

    /**
     * @param locator : By locator
     * @param index   : int index
     * MethodName : selectDropDownValueByIndex Method
     * Description :To select the dropdown list by index
     */
    public void selectDropDownValueByIndex(By locator, int index) {
        WebElement element = driver.findElement(locator);
        selectOption(element).selectByIndex(index);
        Log.info("select drop down " + element + " list by index value");

    }

    /**
     * @param element : Web element
     * @param index   : int index
     * MethodName : selectDropDownValueByIndex Method
     * Description :To select the dropdown list by index
     */
    public void selectDropDownValueByIndex(WebElement element, int index) {
        selectOption(element).selectByIndex(index);
        Log.info("select drop down " + element + " list by index value");
    }

    /**
     * @param locator :By locator
     * @return String
     * MethodName :getSelectedValue Method
     * Description :To get the value selected in a dropdown
     */
    public String getSelectedValue(By locator) {
        String selected;
        WebElement element = createWebElement(locator);
        selected = selectOption(element).getFirstSelectedOption().getText();
        Log.info("get the selected options text : " + selected + " from web element");
        return selected;
    }

    /**
     * @param element : web element
     * @return String
     * MethodName :getSelectedValue Method
     * Description :To get the value selected in a dropdown
     */
    public String getSelectedValue(WebElement element) {
        String selected;
        selected = selectOption(element).getFirstSelectedOption().getText();
        Log.info("get the selected options text : " + selected + " from web element");
        return selected;
    }

    /**
     * @param locator :By locator
     * MethodName :pressTab
     * Description :To send a TAB key
     */
    public void pressTab(By locator) {
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        element.sendKeys(Keys.TAB);
        Log.info("in send keys method we passes the press key tab and it's works");
    }

    /**
     * @param element : web element
     * MethodName :pressTab
     * Description :To send a TAB key
     */
    public void pressTab(@NotNull WebElement element) {
        element.sendKeys(Keys.TAB);
        Log.info("in send keys method we passes the press key tab and it's works");
    }

    /**
     * @param locator :By locator
     * @return string
     * MethodName :getLocatorName
     * Description :To get the locator key
     */
    public String getLocatorName(By locator) {
        return locator.toString();
    }

    /**
     * @param locator : By locator
     * MethodName :clickRadioButton
     * Description :To click on a radio button once visible
     */
    public void clickRadioBtn(By locator) {
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        element.click();
        Log.info("click on radio button element : " + element);
    }

    /**
     * @param element : web Element
     * MethodName :clickRadioButton
     * Description :To click on a radio button once visible
     */
    public void clickRadioBtn(WebElement element) {
        waitUntilVisibleElement(element, OBJ_SYNC_TIME);
        element.click();
        Log.info("click on radio button element : " + element);
    }

    /**
     * @param locator : By locator
     * MethodName :clickElement Method
     * Description :To click on an element immediately
     */
    public void clickElementFast(By locator) {
        try {
            WebElement element = waitUntilVisible(locator, OBJ_SYNC_FAST_TIME);
            element.click();
            Log.info("click on web element: " + element);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element : web element
     * MethodName :clickElement Method
     * Description :To click on an element immediately
     */
    public void clickElementFast(WebElement element) {
        try {
            waitUntilVisible(element, OBJ_SYNC_FAST_TIME);
            element.click();
            Log.info("click on web element: " + element);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param locator : By locator
     * @return boolean
     * MethodName : isElementEnabled Method
     * Description : To check if the element is enabled and return true if enabled
     */
    public boolean isElementEnabled(By locator) {
        try {
            waitUntilVisible(locator, 20);
            WebElement element = driver.findElement(locator);
            boolean bool = element.isEnabled();
            Log.info("element is enabled "  + element);
            return bool;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * @param element : web Element
     * @return boolean
     * MethodName : isElementEnabled Method
     * Description : To check if the element is enabled and return true if enabled
     */
    public boolean isElementEnabled(WebElement element) {
        try {
            waitUntilVisible(element, 20);
            boolean bool = element.isEnabled();
            Log.info("element is enabled "  + element);
            return bool;
        } catch (Exception e) {
            Log.error(element.toString() + " is not enabled");
            return false;
        }
    }

    /**
     * @param locator :By locator
     * @return int
     * MethodName :getListSize Method
     * Description :To get the size of the list and return list size
     */
    public int getListSize(By locator) {
        return driver.findElements(locator).size();
    }

    /**
     * @param locator :By locator
     * MethodName :getElementList Method
     * Description :To get the list of elements and return web element list
     * @return : list of web elements
     */
    public List<WebElement> getElementList(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        List<WebElement> elements = driver.findElements(locator);
        Log.info("identified the list of web elements using the locator : " + locator);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(OBJ_SYNC_TIME));
        return elements;
    }


    /**
     * @param locator :By locator
     * @return boolean
     * MethodName :verifyElementPresent
     * Description :To verify if an element is present and return boolean value
     */
    public boolean verifyElementPresent(By locator) {
        try {
            boolean result = false;
            waitUntilVisible(locator, 10);
            WebElement element = createWebElement(locator);
            if (element != null) {
                Log.info("element is not null " + element);
                result = true;
            }
            return result;
        } catch (Exception e) {
            Log.info(e.getMessage());
            return false;
        }
    }

    /**
     * @param element : web element
     * @return boolean
     * MethodName :verifyElementPresent
     * Description :To verify if an element is present and return boolean value
     */
    public boolean verifyElementPresent(WebElement element) {
        try {
            boolean result = false;
            waitUntilVisible(element, 10);
            if (element != null) {
                Log.info("element is not null " + element);
                result = true;
            }
            return result;
        } catch (Exception e) {
            Log.info(e.getMessage());
            return false;
        }
    }

    /**
     * @param locator : By locator
     * @return string
     * MethodName : getText
     * Description : To retrieve text from web element
     */
    public String getText(By locator) {
        try {
            waitUntilVisible(locator, 20);
            return createWebElement(locator).getText().trim();
        } catch (Exception e) {
            Log.error(e.getMessage());
            return "";
        }
    }

    /**
     * @param element : web element
     * @return string
     * MethodName :getText
     * Description :To retrieve text from web element
     */
    public String getText(WebElement element) {

        try {
            waitUntilVisible(element, 20);
            return element.getText().trim();
        } catch (Exception e) {
            Log.error(e.getMessage());
            return "";
        }
    }

    /**
     * @param locator :By locator
     * @return string
     * MethodName :getText Method
     * Description :To retrieve text from web element
     */
    public String getContentDescription(By locator) {
        try {
            return createWebElement(locator).getAttribute("name").trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param locator   : By locator
     * @param Attribute : String Attribute
     * @return string
     * MethodName : getAttribute
     * Description : To retrieve attribute from web element
     */
    public String getAttribute(By locator, String Attribute) {
        try {
            return createWebElement(locator).getAttribute(Attribute).trim();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @param id        : By locator
     * @param attribute : String Attribute
     * MethodName : getAttributeJSByID Method
     * Description : To retrieve text from web element
     * @return string
     */
    public String getAttributeJSByID(String id, String attribute) {
        try {
            String strText = "";
            String script = "return document.getElementById('" + id + "').getAttribute('" + attribute + "');";
            strText = ((JavascriptExecutor) driver).executeScript(script).toString();
            Log.info("get the element attribute by using js");
            return strText;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param locator :By locator
     * MethodName : switchFrame
     * Description : Switch to the frame by locator
     */
    public void switchFrame(By locator) {
        try {
            driver.switchTo().frame(createWebElement(locator));
            Log.info("switch to the element");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * @param element :By locator
     * MethodName : switchFrame
     * Description : Switch to the frame by locator
     */
    public void switchFrame(WebElement element) {
        try {
            driver.switchTo().frame(element);
            Log.info("switch to the element");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * MethodName :switchDefaultFrame
     * Description :Switch to default frame
     */
    public void switchDefaultFrame() {
        try {
            driver.switchTo().defaultContent();
            Log.info("switch to default content");
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * MethodName :switchToActiveElement
     * Description :Switch to active element
     */
    public void switchToActiveElement() {
        driver.switchTo().activeElement();
        Log.info("switch to active element");
    }

    /**
     * @return : string
     * MethodName :getPageSource
     * Description : it's uses to get the page source
     */
    public String getPageSource() {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            Log.info(e.getMessage());
            return null;
        }
    }

    /**
     * @param locator : By locator
     * @param data    : String data
     * @return boolean
     * MethodName :compareTextWithIgnoreCase
     * Description :To Compare two strings
     */
    public boolean compareTextWithIgnoreCase(By locator, String data) {
        try {
            String actual = createWebElement(locator).getText().trim();
            Log.info(actual);
            String expected = data.trim();
            Log.info(expected);
            return actual.equalsIgnoreCase(expected);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param element : web element
     * @param data    : String data
     * @return boolean
     * MethodName :compareTextWithIgnoreCase
     * Description :To Compare two strings
     */
    public boolean compareTextWithIgnoreCase(WebElement element, String data) {
        try {
            String actual = element.getText().trim();
            String expected = data.trim();
            return actual.equalsIgnoreCase(expected);
        } catch (Exception e) {
            Log.info(e.getMessage());
            return false;
        }
    }
    /**
     * @param locator : By locator
     * @param data    : String
     * @return boolean
     * MethodName : verifyTextAfterRemoveSpecialCharacter
     * Description : it's compare text with removing of the special character
     */
    public boolean verifyTextAfterRemoveSpecialCharacter(By locator, String data) {
        try {
            String actual = driver.findElement(locator).getText().trim();
            String expected = data.trim();
            return removeSpecialCharters(actual).equalsIgnoreCase(removeSpecialCharters(expected));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MethodName : compareText
     * Description :To Compare two strings Input
     * @param actual: string
     * @param expected: string
     */
    public boolean compareText(String actual, String expected) throws IOException {
        try {
            actual = actual.trim();
            System.out.println(actual);
            expected = expected.trim();
            return actual.equals(expected);
        } catch (Exception e) {
            Log.error(e.getMessage());
            return false;
        }
    }

    /**
     * @param locator :By locator
     * @param data    String data
     * @return boolean
     * MethodName :verifyTitleAttributeText Method
     * Description :To get the title attribute and compare it with a string
     */
    public boolean verifyTitleAttributeText(By locator, String data) {

        try {
            String actual = createWebElement(locator).getAttribute("title").trim();
            String expected = data.trim();
            return actual.equals(expected);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * MethodName :sleep Method
     * Description :To wait for few seconds
     * @param timeOutInSeconds  :int timeOutInSeconds
     */
    public void sleep(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    /**
     * MethodName :responseTime Method
     * Description :To calculate the response time and return resTime
     * @param startTime  :long startTime
     * @param endTime long endTime
     * @return long
     */
    public long responseTime(long startTime, long endTime) throws Exception {
        long remTime = endTime - startTime;
        return remTime / 1000;
    }

    /**
     * MethodName : removeLastChar Method
     * Description :To remove the last character from a string and return the string
     * @param str : String
     * @return string
     */
    public String removeLastChar(String str) {
        if (str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * Method Name :txtClearValue Method
     * Description :To clear the text in the locator
     * @param locator : locator
     */
    public void txtClearValue(By locator) {
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        element.click();
        element.clear();

    }
    /**
     * Method Name :txtClearValue Method
     * Description :To clear the text in the locator
     * @param element : web element
     */
    public void txtClearValue(WebElement element) {
        waitUntilVisible(element, OBJ_SYNC_TIME);
        element.click();
        element.clear();

    }

    /**
     * MethodName : scrollViewElement
     * Description : it's used to scroll to the element to view
     * @param element : web element
     */
    public void scrollViewElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try {
            jse.executeScript("arguments[0].scrollIntoView(true);", element);
            jse.executeScript("arguments[0].style.border='3px solid green';setTimeout('blinker()', 500)", element);
            jse.executeScript("arguments[0].scrollIntoView(true);", element);
            jse.executeScript("arguments[0].focus();", element);
        } catch (Exception e) {
            // Reporter.log("Button is not identified or not found");
        }
    }


    /**
     * Method Name :totalNoRow Method Description :Get total no. Of rows
     * Input Parameters :driver,Element reference
     */
    public int totalNoRow(By oBy) {
        if (driver != null) {
            return driver.findElements(oBy).size();
        } else {
            return -1;
        }
    }

    /**
     * Method Name :validateDropDownOptions //Method Description:Compare the
     * options in a dropdown // // //Input Parameters :driver,Element
     * Locators,String option in the list box
     */
    public boolean validateDropDownOptions(By locator, String sOptions) {
        boolean flag = false;
        if (driver != null) {
            WebElement element = driver.findElement(locator);
            Select sLstEle = new Select(element);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            String[] sLstArray = sOptions.split(",", 0);

            if (sLstArray.length > 1) {
                List<WebElement> lstOptions = sLstEle.getOptions();
                for (int i = 1; i < lstOptions.size(); i++) {
                    if ((sLstArray[i - 1]).matches(lstOptions.get(i).getText())) {
                        Log.info(
                                i + "\t\t" + sLstArray[i - 1] + "\t\t" + lstOptions.get(i).getText() + "\t\t PASS");
                        flag = true;
                    } else {
                        Log.info(
                                i + "\t\t" + sLstArray[i - 1] + "\t\t" + lstOptions.get(i).getText() + "\t\t FAIL");
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Method Name :validateDropDownOptions //Method Description:Compare the
     * options in a dropdown // // //Input Parameters :driver,Element
     * Locators,String option in the list box
     */
    public boolean validateDropDownOptions(WebElement element, String sOptions) {
        boolean flag = false;
        if (driver != null) {
            Select sLstEle = new Select(element);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            String[] sLstArray = sOptions.split(",", 0);

            if (sLstArray.length > 1) {
                List<WebElement> lstOptions = sLstEle.getOptions();
                for (int i = 1; i < lstOptions.size(); i++) {
                    if ((sLstArray[i - 1]).matches(lstOptions.get(i).getText())) {
                        Log.info(
                                i + "\t\t" + sLstArray[i - 1] + "\t\t" + lstOptions.get(i).getText() + "\t\t PASS");
                        flag = true;
                    } else {
                        Log.info(
                                i + "\t\t" + sLstArray[i - 1] + "\t\t" + lstOptions.get(i).getText() + "\t\t FAIL");
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    /**********************************************************************************************
     * Method Name :validateListSingle Method Description :validate the whether
     * list box is displayed for multiple options
     * Input Parameters :driver,list name, int option - 1(single) and 2
     * (multiple)
     ************************************************************************************************/
    public boolean validateListSingle(By locator, int iOption) {
        boolean flag = false;
        if (driver != null && iOption == 1 || iOption == 2) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            assert driver != null;
            WebElement element = driver.findElement(locator);
            Select sLstEle = new Select(element);

            if (iOption == 1) {

                flag = !sLstEle.isMultiple();

            } else {

                flag = sLstEle.isMultiple();

            }

        }
        return flag;

    }

    /**
     * MethodName : browserClose
     * Description : it's closes active window
     */
    public void browserClose() {
        driver.close();
    }

    /**
     * MethodName : browserKill
     * Description : it's used to kill browsers and closes all tabs/windows
     */
    public static void browserKill() {
        driver.quit();
    }

    /**
     * MethodName : validateRadioSingle Method
     * Description :validate the whether the radio button displayed has multiple option or single option Input
     * @param locator  : Locator
     * @return boolean
     */
    public boolean validateRadioSingle(By locator) {
        boolean flag = false;
        if (driver != null) {
            waitForLoading();
            List<WebElement> RadioGroup1 = driver.findElements(locator);
            if (RadioGroup1.size() > 2) {

                if (!RadioGroup1.get(1).isSelected()) {

                    RadioGroup1.get(1).click();

                }

                if (!RadioGroup1.get(2).isSelected()) {

                    RadioGroup1.get(2).click();
                }

                flag = RadioGroup1.get(2).isSelected() && !RadioGroup1.get(1).isSelected();

            }

        }
        return flag;
    }


    /**
     * MethodName :compareTwoValues
     * Description : To compare two string values
     */
    public boolean compareTwoValues(String elementText, String errorMessage) {
        return elementText.equalsIgnoreCase(errorMessage);
    }


    /**
     * MethodName :generateRandomIntValue
     * Description : To generate a random integer value based upon the two given input values
     * @param min : minimum value
     * @param max : maximum value
     * @return integer
     */

    public int generateRandomIntValue(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * MethodName :generateRandomValue
     * Description : To generate a random integer value based upon the given input parameter
     * @param num : int number
     * @return integer
     */

    public int generateRandomValue(int num) {
        Random rand = new Random();
        return rand.nextInt(num);
    }

    /**
     * MethodName :pressTabByAction
     * Description : To press on TAB key using action class
     */

    public void pressTabByAction() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).click().build().perform();
    }

    /**
     * Method Name :pressEscapeKeyByAction Description : To press on Escape key
     * using action class
     */

    public void pressEscapeByAction() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    /**
     * MethodName : getFontSize
     * Description : Return Font Size for locator
     * @param locator : locator
     * @return string
     */
    public String getFontSize(By locator) {
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        return element.getCssValue("font-size");
    }

    /**
     * MethodName : getFontSize
     * Description : Return Font Size for web element
     * @param element : web element
     * @return string
     */
    public String getFontSize(WebElement element) {
        return element.getCssValue("font-size");
    }

    /**
     * MethodName : getFontColor
     * Description : Return Font Color for the given locator
     * @param locator : locator
     * @return string font color
     */
    public String getFontColor(By locator) {
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        return element.getCssValue("color");
    }

    /**
     * MethodName : getFontColor
     * Description : Return Font Color for the given locator
     * @param element : web element
     * @return string font color
     */
    public String getFontColor(WebElement element) {
        return element.getCssValue("color");
    }

    /**
     * MethodName : getFontWeight
     * Description : Return Font weight for the given locator
     * @param locator : locator
     * @return integer
     */
    public int getFontWeight(By locator) {
        String sFont_Weight;
        WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
        sFont_Weight = element.getCssValue("font-weight");
        return Integer.parseInt(sFont_Weight);
    }

    /**
     * MethodName : getAttributeValue
     * Description : Returns the attribute value of a specific element
     * @param locator  : locator
     * @param attribute : attribute name
     * @return string
     */
    public String getAttributeValue(By locator, String attribute) {
        String value = "";
        try {
            WebElement element = waitUntilVisible(locator, OBJ_SYNC_TIME);
            value = element.getAttribute(attribute);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return value;
    }

    /**
     * MethodName : forwardBrowser
     * Description : Forward Browser
     */
    public void forwardBrowser() {
        driver.navigate().forward();
    }

    /**
     * MethodName : NavigateToPreviousPage
     * Description : Back to previous page
     */
    public void NavigateToPreviousPage() {
        driver.navigate().back();
    }

    /**
     * MethodName :isElementDisplayed Method
     * Description :To check if the element is displayed and return true if displayed
     * @param locator : locator
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = createWebElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * MethodName : isTextPresentInPageSource
     * Description : it's used to check whether the text is present in page source
     * @param pageText : pageText
     * @return boolean
     */
    public boolean isTextPresentInPageSource(String pageText) {
        boolean flag = false;

        try {
            String pgSource = driver.getPageSource().toLowerCase();
            if (pgSource.contains(pageText.toLowerCase())) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * MethodName :isElementDisplayed_MinWait Method
     * Description :To check if the element is displayed and return true if displayed, with minimal wait
     * time of 6 seconds
     * @param locator :By locator
     */
    public boolean isElementDisplayed_MinWait(By locator) {

        boolean blnDisplay = false;
        try {
            waitUntilVisible(locator, 4);
            WebElement element = createWebElement(locator);
            if (isElementDisplayed(element)) {
                Log.info(element+ " is displayed");
                blnDisplay = true;
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(OBJ_SYNC_TIME));

        return blnDisplay;
    }

    /**
     * MethodName : getListElementText
     * Description : it's used to get the list of elements text
     * @param locator : locator
     * @return string
     */
    public String getListElementText(By locator) {
        List<WebElement> ele = driver.findElements(locator);
        StringBuilder textList = new StringBuilder();

        try {
            for (WebElement element : ele) {
                textList.append(" ").append((element.getText().replaceAll("\n", " ")).replaceAll("\\s{2,}", " "));
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return textList.toString().trim();
    }

    /**
     * MethodName : getListElementText
     * Description : it's used to get the list of elements text
     * @param ele : List of web elements
     * @return string
     */
    public String getListElementText(List<WebElement> ele) {
        StringBuilder textList = new StringBuilder();

        try {
            for (WebElement element : ele) {
                textList.append(" ").append((element.getText().replaceAll("\n", " ")).replaceAll("\\s{2,}", " "));
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return textList.toString().trim();
    }

    /**
     * MethodName : trimSpace
     * Description : it's used to trim spaces
     * @param text : text
     * @return : string
     */
    public String trimSpace(String text) {
        return (((text).replaceAll("\n", " ").trim().replaceAll("’", "'")).replaceAll("\\s{2,}", " "));
    }

    /**
     * MethodName : removeSpecialCharters
     * Description : it's used to remove special characters in text
     * @param inputParameter : string
     * @return string
     */
    public String removeSpecialCharters(String inputParameter) {

        return inputParameter.replaceAll("[^a-zA-Z\\d]", "").replace("\n", "");
    }

    /**
     * MethodName : removeAlphabets
     * Description : it's used to remove alphabets from string
     * @param inputParameter : parameter
     * @return string
     */
    public String removeAlphabets(String inputParameter) {
        return inputParameter.replaceAll("\\d", "");
    }

    /**
     * MethodName : currencyStrToDouble
     * Description : it's used to currency value to double value
     * @param str : string currency value
     * @param currencySym : currency symbol
     * @return double
     */
    public double currencyStrToDouble(@NotNull String str, String currencySym) {
        boolean flag = true;
        double amount = 0;
        try {
            if (str.contains(".")) {
                String[] abc = str.split("\\.");
                if (abc.length > 1) {
                    if (abc[1].equalsIgnoreCase("00")) {
                        str = abc[0];
                        flag = false;
                    }
                }
            }
            String tmp = str.replace(currencySym, "");
            String temp = tmp.replaceAll("\\s+", "");
            amount = Double.parseDouble(temp);
            if (flag)
                amount = round(amount, 2);
        } catch (NumberFormatException e) {
            Log.error(e.getMessage());
        }
        return amount;
    }

    /**
     * MethodName : chompDecimal
     * Description : it's used to remove decimal value from the string
     * @param str string
     * @return string
     */
    public String chompDecimal(String str) {
        String tmp = str.replace(".00", "");
        return tmp.replaceAll("\\s+", "");
    }

    /**
     * MethodName : round
     * Description : it's used to round the double value based on the number of places given
     * @param value : double value
     * @param places : int number of places to round
     * @return double
     */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public String fmt(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    /**
     * MethodName : isElementDisplayed
     * Description : it's used to check whether element is displayed
     * @param ele : Web element
     * @return boolean
     */
    public boolean isElementDisplayed(@NotNull WebElement ele) {
        return ele.isDisplayed();
    }

    /**
     * MethodName : isElementSelected
     * Description : it's used to check whether the element is selected
     * @param element : web element
     * @return boolean
     */
    public boolean isElementSelected(@NotNull WebElement element) {
        boolean bool = element.isSelected();
        Log.info("element " + element + " is selected : "+bool);
        return bool;
    }

    /**
     * MethodName : isElementSelected
     * Description : it's used to check whether the element is selected
     * @param locator : locator is used to find web element
     * @return boolean
     */
    public boolean isElementSelected(By locator) {
        WebElement element = createWebElement(locator);
        boolean bool = element.isSelected();
        Log.info("element " + element + " is selected : "+bool);
        return bool;
    }

    public static int counter = 0;


    /**
     * MethodName : switchToPreviewWindow
     * Description : it's used to switch to preview window
     */
    public void switchToPreviewWindow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForLoading();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        parentWindowHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        System.out.println("Total Windows(switch_to_preview_window) : " + handles.size());

        for (String windowHandle : handles) {
            if (!windowHandle.equals(parentWindowHandle)) {
                childWindowHandle = windowHandle;
                driver.switchTo().window(windowHandle);
                Log.info("switch to child window : " + childWindowHandle);
                break;
            }
        }
    }

    /**
     * MethodName : switchToParentPage
     * Description : it's used to switch from child window to parent window
     */
    public void switchToParentPage() {
        driver.close(); //closing child window
        driver.switchTo().window(parentWindowHandle);//Control to parent window
        Log.info("switch to parent window");
    }


    public void scrollElementToMidOfTheScreenUsingJs(WebDriver driver, WebElement element) throws Throwable {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        Thread.sleep(3);
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'})", element);
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = driver -> {
            assert driver != null;
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
        };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_SYNC_TIME));
            wait.until(expectation);
        } catch (Throwable error) {
            Log.error(error.getMessage());
        }
    }


    public void waitForURLToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_OUT_TIME_SEC)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }


    /**
     * MethodName : addCookiesToFile
     * Description : it's used to add cookies to a file
     * @param path : path of the file
     * @param fileName : file name
     */
    public void addCookiesToFile(String path, String fileName) {
        File file = new File(path + "/" + fileName);
        try {
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter bWrite = new BufferedWriter(fileWrite);
            Set<Cookie> cookies = driver.manage().getCookies();
            Log.info("cookies : " +cookies.toString());
            for (Cookie ck : cookies) {
                bWrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                bWrite.newLine();
            }
            Log.info("cookies added to file : " + fileName);
            bWrite.close();
            fileWrite.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * MethodName : readCookieByFile
     * Description : it's used to read the cookie file and into the cookies
     * @param filePath : string file path
     */
    public void readCookieByFile(String filePath) {
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strLine, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;

                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = DateFormat.getDateInstance().parse(val);
                    }
                    boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    System.out.println(ck);
                    driver.manage().addCookie(ck);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * MethodName : deselectDropDownByIndex
     * Description : it's used to deselect the drop-down by index
     * @param element : web element
     * @param index : index value
     */
    public void deselectDropDownByIndex(WebElement element, int index) {
        selectOption(element).deselectByIndex(index);
        Log.info("deselect the drop-down : "+ element + " by index : " + index);
    }

    /**
     * MethodName : deselectDropDownByValue
     * Description : it's used to deselect the drop-down by value
     * @param element : web element
     * @param value : string value
     */
    public void deselectDropDownByValue(WebElement element, String value) {
        selectOption(element).deselectByValue(value);
        Log.info("deselect the drop-down : "+ element + " by value : " + value);
    }

    /**
     * MethodName : deselectByVisibleText
     * Description : it's used to deselect the drop-down by visible text
     * @param element : web element
     * @param text : string text
     */
    public void deselectByVisibleText(WebElement element, String text) {
        selectOption(element).deselectByVisibleText(text);
        Log.info("deselect the drop-down : "+ element + " by visible text : " + text);
    }

    /**
     * MethodName : deselectDropDownByIndex
     * Description : it's used to deselect the drop-down by index
     * @param locator : locator is used to find web element
     * @param index : index value
     */
    public void deselectDropDownByIndex(By locator, int index) {
        WebElement element = driver.findElement(locator);
        selectOption(element).deselectByIndex(index);
        Log.info("deselect the drop-down : "+ element + " by index : " + index);
    }

    /**
     * MethodName : deselectDropDownByValue
     * Description : it's used to deselect the drop-down by value
     * @param locator : locator is used to identify web element
     * @param value : string value
     */
    public void deselectDropDownByValue(By locator, String value) {
        WebElement element = driver.findElement(locator);
        selectOption(element).deselectByValue(value);
        Log.info("deselect the drop-down : "+ element + " by value : " + value);
    }

    /**
     * MethodName : deselectByVisibleText
     * Description : it's used to deselect the drop-down by visible text
     * @param locator : locator is used to identify web element
     * @param text : string text
     */
    public void deselectByVisibleText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        selectOption(element).deselectByVisibleText(text);
        Log.info("deselect the drop-down : "+ element + " by text : " + text);
    }

    /**
     * MethodName : deselectAllOptions
     * Description : it's used to deselect the all options from dropdown
     * @param element : web element
     */
    public void deselectAllOptions(WebElement element) {
        selectOption(element).deselectAll();
        Log.info("Deselect the all options : " + element);
    }

    /**
     * MethodName : deselectAllOptions
     * Description : it's used to deselect the all options from dropdown
     * @param locator : locator is used to identify web element
     */
    public void deselectAllOptions(By locator) {
        WebElement element = createWebElement(locator);
        selectOption(element).deselectAll();
        Log.info("Deselect the all options : " + element);
    }

    /**
     * MethodName : captureScreenshot
     * Description : it's used to capture screenshot
     * @param screenshotName : screenshot name
     * @param result : test case result
     * @return : file path as a string
     */
    public String captureScreenshot(String screenshotName, String result) {
        String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);


        String destPath = "./screenshots/" + result + "/" + screenshotName + "_" + date + ".png";
        File destFile = new File(destPath);

        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }

    /**
     * MethodName : getCurrentUrl
     * Description : it's showing
     * @return string
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }


    /**
     * MethodName : getTitle
     * Description : it's used to get the title of the page
     * @return title as string
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * MethodName : clickNavigateToNewPage
     * Description : it's used to click on element and navigate to new page
     * @param object Object of new page to navigate
     * @param element web element of the page
     * @return Object of new page
     */
    public Object clickNavigateToNewPage(Object object, WebElement element) {
        clickElement(element);
        Log.info("Clicked on element : " + element.toString());
        return object;
    }

    /**
     * MethodName : clickNavigateToNewPage
     * Description : it's used to click on element and navigate to new page
     * @param object Object of new page to navigate
     * @param locator web element of the page
     * @return Object of new page
     */
    public Object clickNavigateToNewPage(Object object, By locator) {
        clickElement(locator);
        Log.info("Clicked on element : " + locator.toString());
        return object;
    }

    /**
     * MethodName : uploadFileUsingRobotClass
     * Description : upload file from windows using robot class
     * @param filePath : string
     */
    public void uploadFileUsingRobotClass(String filePath) {
        try {
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

            Robot robot = new Robot();//robot object is created
            robot.setAutoDelay(1000);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.setAutoDelay(1000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Log.info("Upload the file from : " +filePath);
        } catch (AWTException e) {
            Log.error(e.getMessage());
        }
    }

    /**
     * MethodName : clickFromListOfElements
     * Description : it's used to click on the list of web elements
     * @param elements : list of web elements
     * @param text : text to identify to click list of elements;
     */
    public void clickFromListOfElements (List<WebElement> elements, String text) {
        for (WebElement ele : elements) {
            if (ele.getText().trim().equals(text.trim())) {
                Log.info("element text " + ele.getText() + " is equal to " + text);
                ele.click();
                Log.info("element clicked on "+ ele.getText());
                break;
            }
        }
    }

    /**
     * MethodName :  clickFromListOfElements
     * Description : it's used to click on the list of web elements
     * @param locator : string locator of list of elements
     * @param text : text to identify to click list of elements;
     */
    public void clickFromListOfElements (By locator, String text) {
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement ele : elements) {
            if (ele.getText().trim().equals(text.trim())) {
                Log.info("element text " + ele.getText() + " is equal to " + text);
                ele.click();
                break;
            }
        }
    }

    /**
     * MethodName : init_properties
     * Description : it's used to read properties from a file
     * @param path : String file path
     * @return : properties
     */
    public static Properties init_properties(String path) {

        Properties prop = new Properties();
        try {
            File file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException("File doesn't exits");
            }
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}