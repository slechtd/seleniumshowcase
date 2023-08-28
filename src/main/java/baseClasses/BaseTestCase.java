package baseClasses;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.EnvironmentManager;
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
    }

    //SHARED TESTCASE METHODS

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

    //PRIVATE METHODS

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
