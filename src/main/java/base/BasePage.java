package base;

import helpers.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    public Logger log = Logger.getLogger(this.getClass().getName());

    protected WebDriver driver;
    private WaitHelper waitHelper;
    private JavascriptExecutor jsExecutor;

    public BasePage(WebDriver driver) {
        super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitHelper = new WaitHelper(driver);
    }

    public WebElement waitUntilElementPresent(By byElement) {
        return waitUntilElementPresent(byElement, "Search element by: " + byElement);
    }

    public WebElement waitUntilElementPresent(By byElement, String errorMessage) {
        return waitHelper.waitUntilElementPresent(byElement, errorMessage);
    }

    public WebElement waitUntilElementPresentAndClick(By byElement) {
        return waitUntilElementPresentAndClick(byElement, "Search element by: " + byElement);
    }

    public WebElement waitUntilElementPresentAndClick(By byElement, String errorMessage) {
        waitHelper.waitUntilElementPresent(byElement, errorMessage);
        WebElement element = waitHelper.waitUntilElementClickable(byElement);
        try {
            element.click();
        } catch (ElementClickInterceptedException ecie) {
            jsScrollToElementIntoView(element);
            try {
                element.click();
            } catch (ElementClickInterceptedException ecie1) {
                jsScrollToElementNotIntoView(element);
                element.click();
            }
        }
        return element;
    }

    public boolean elementIsDisplayed(By by) {
        try {
            return findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement findElement(By by) {
        return waitHelper.waitUntilElementPresent(by);
    }

    public WebElement inputTextIntoElement(By by, Object text) {
        WebElement element = findElement(by);
        element.clear();

        // due to clear() is not working on some elements
        if (!element.getAttribute("value").isEmpty()) {
            element.sendKeys(Keys.CONTROL, "a");
            element.sendKeys(Keys.DELETE);
        }

        if (text != null) {
            element.sendKeys(String.valueOf(text));
        }
        return element;
    }

    public void jsScrollToElementIntoView(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsScrollToElementNotIntoView(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", element);
    }
}
