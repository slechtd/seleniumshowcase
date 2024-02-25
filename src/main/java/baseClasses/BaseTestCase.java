package baseClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.EnvironmentManager;
import utils.MiniLogger;
import utils.WebDriverFactory;

import java.time.Duration;

public class BaseTestCase {

    //SET-UP & TEAR-DOWN METHODS

    @BeforeTest
    protected void setup() throws Exception {
        WebDriverFactory.getDriver().get(EnvironmentManager.getEnvironmentUrl());
    }

    @AfterTest
    protected void tearDown() {
        WebDriverFactory.cleanUpDriver();
        MiniLogger.addExecutionEndTimestamp();
    }

    //SHARED TESTCASE METHODS

    public void log(String message) {
        String testCaseName = getClass().getSimpleName();
        MiniLogger.log(message, testCaseName);
    }

    public void pass(String message) {
        String testCaseName = getClass().getSimpleName();
        MiniLogger.pass(message, testCaseName);
    }

    public void fail(String message) {
        String testCaseName = getClass().getSimpleName();
        MiniLogger.fail(message, testCaseName);
    }

    public void fail(Throwable throwable) {
        String testCaseName = getClass().getSimpleName();
        MiniLogger.fail(throwable, testCaseName);
    }

    // SHARED ELEMENT METHODS

    public static void waitForElementVisible(WebElement element, int seconds) {
        wait(seconds).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementInvisible(WebElement element, int seconds) {
        wait(seconds).until(ExpectedConditions.invisibilityOf(element));
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForPageToBeLoaded(int seconds) {
        try {
            WebDriverWait wait = wait(seconds);
            ExpectedCondition<Boolean> pageLoadCondition = createPageLoadCondition();
            wait.until(pageLoadCondition);
        } catch (TimeoutException e) {
            throw new RuntimeException("Page did not load within " + seconds + " seconds.");
        }
    }

    public void hoverOverElement(WebElement elementToHover) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(elementToHover).perform();
    }

    // PRIVATE METHODS

    private static WebDriverWait wait(int seconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
    }

    private static ExpectedCondition<Boolean> createPageLoadCondition() {
        return driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    private static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }
}
