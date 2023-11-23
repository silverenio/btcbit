package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {

    private final By HEADER_LOGO = By.xpath("//a[contains(@class,'header__logo-link')]");
    private final By SIGN_IN_BUTTON = By.xpath("//a[@href='/sign_in']");
    private final By EMAIL_INPUT = By.xpath("//input[@id='email']");
    private final By PASSWORD_INPUT = By.xpath("//input[@id='password']");
    private final By SIGN_IN_FORM_BUTTON = By.xpath("//input[@id='sign-in']");
    private final By VALIDATION = By.xpath("//div[@id='js-login-error']/p");


    public MainPage(WebDriver driver) {
        super(driver);
        waitUntilElementPresent(SIGN_IN_BUTTON);
    }

    public MainPage clickSignInButton() {
        waitUntilElementPresentAndClick(SIGN_IN_BUTTON);
        waitUntilElementPresent(EMAIL_INPUT);
        return this;
    }

    public MainPage inputEmail(String email) {
        inputTextIntoElement(EMAIL_INPUT, email);
        return this;
    }

    public MainPage inputPassword(String password) {
        inputTextIntoElement(PASSWORD_INPUT, password);
        return this;
    }

    public void clickSignInFormButton() {
        waitUntilElementPresent(SIGN_IN_FORM_BUTTON).click();
    }

    public String getValidation() {
        return waitUntilElementPresent(VALIDATION).getText();
    }

    public void clickHeaderLogo() {
        waitUntilElementPresentAndClick(HEADER_LOGO);
    }
}
