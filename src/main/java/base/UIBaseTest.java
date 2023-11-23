package base;

import driver.Chrome;
import driver.WebDriverContext;
import listeners.CustomListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import settings.SettingsProvider;

@Listeners({CustomListener.class})
public class UIBaseTest extends BaseTest {

    protected WebDriver driver;

    @BeforeClass
    protected void setup() {
        driver = new Chrome().initDriver();
        WebDriverContext.setDriver(driver);
        driver.get(SettingsProvider.getWebHost());
    }

    @AfterClass
    public void quitDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();

            WebDriverContext.removeDriver();
        }
    }
}
