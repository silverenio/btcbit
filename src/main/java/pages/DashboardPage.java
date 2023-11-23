package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private final By DASHBOARD_DROPDOWN = By.xpath("//span[@id='js-dropdown-link-selected']");
    private final By NAV_BAR = By.xpath("//nav[contains(@class, 'menu--is-auth')]");

    public DashboardPage(WebDriver driver) {
        super(driver);
        waitUntilElementPresent(NAV_BAR);
    }

    public boolean isUserLoggedIn() {
        return elementIsDisplayed(NAV_BAR);
    }
}
