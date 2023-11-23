package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class Chrome {

    private Logger log = Logger.getLogger(this.getClass().getName());

    private WebDriver driver;

    public WebDriver initDriver() {
        localDriver();
        return driver;
    }

    private void localDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort().build();
        driver = new ChromeDriver(service, initOptions(false));
    }

    private ChromeOptions initOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        if (isHeadless) options.addArguments("--headless");

        return options;
    }
}
