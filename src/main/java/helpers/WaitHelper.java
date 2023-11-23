package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class WaitHelper {

    private static final int DEFAULT_EXPECTED_CONDITION_WAIT_SECONDS = 15;

    private WebDriverWait wait;

    public WaitHelper(WebDriver driver) {
        wait = new WebDriverWait(driver, DEFAULT_EXPECTED_CONDITION_WAIT_SECONDS);
        setExceptionIgnoring();
    }

    private void setExceptionIgnoring() {
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(NoSuchWindowException.class);
    }

    public WebElement waitUntilElementPresent(By byElement, String errorMessage) {
        wait.withMessage(errorMessage);
        return wait.until(presenceOfElementLocated(byElement));
    }

    public WebElement waitUntilElementClickable(By byElement) {
        wait.withMessage("");
        return wait.until(elementToBeClickable(byElement));
    }

    public WebElement waitUntilElementPresent(By byElement) {
        return waitUntilElementPresent(byElement, "Search element by: " + byElement);
    }
}
