package factory;

import base.BasePage;
import driver.WebDriverContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;

public class PageinstancesFactory {

    public static <T extends BasePage> T getInstance(Class<T> type) {
        try {
            return type.getConstructor(WebDriver.class).newInstance(WebDriverContext.getDriver());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
}
