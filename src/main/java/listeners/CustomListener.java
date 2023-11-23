package listeners;

import io.qameta.allure.Attachment;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener implements ITestListener {

    private static Logger log = Logger.getLogger(CustomListener.class.getSimpleName());

    @Override
    public void onTestStart(ITestResult result) {
        log.debug("start method: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        driverStop(result);
        log.debug("success method: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        log.error("failure method: " + methodName);
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
        try {
            if (driver != null) {
                failedWebTest(driver, methodName);
            } else {
                failedNotWebTest(methodName);
            }
        } catch (Exception e) {
            log.error("TEST FINISH EXCEPTION: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.debug("skipped method: " + result.getMethod().getMethodName());
        driverStop(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.debug("failure with SUCCESS percentage method: " + result.getMethod().getMethodName());
        driverStop(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        long executionTime = context.getEndDate().getTime() - context.getStartDate().getTime();
        log.debug(String.format("Total time of tests run: %s seconds", executionTime / 1000));

        log.debug(String.format("Passed tests: %s", context.getPassedTests().getAllResults().size()));
        log.debug(String.format("Failed tests: %s", context.getFailedTests().getAllResults().size()));
        log.debug(String.format("Skipped tests: %s", context.getSkippedTests().getAllResults().size()));
    }

    private void failedWebTest(WebDriver driver, String methodName) {
        saveScreenShotPNG(driver);
        saveTextLog(methodName);
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
            log.debug("STOP DRIVER EXCEPTION: " + e.getMessage());
        }
    }

    private void failedNotWebTest(String methodName) {
        log.debug("method : '" + methodName + "' test failed generated");
    }

    @Attachment(value = "Page screenshot:", type = "image/png")
    public byte[] saveScreenShotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Failure:", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    private void driverStop(ITestResult result) {
        driverStop(result.getTestContext());
    }

    private void driverStop(ITestContext context) {
        try {
            WebDriver driver = (WebDriver) context.getAttribute("driver");
            if (driver != null) {
                driver.close();
                driver.quit();
            }
        } catch (Exception e) {
            log.debug("STOP DRIVER EXCEPTION: " + e.getMessage());
        }
    }
}
