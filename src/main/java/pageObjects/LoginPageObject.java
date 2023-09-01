package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageObject extends BasePageObject {

    private final WebDriver driver = getDriver();

    //LOCATORS - not to be accessed directly, use getters bellow.

    private final By loginField = By.cssSelector("#loginFrm_loginname");

    private final By passwordField = By.cssSelector("#loginFrm_password");

    private final By loginButton = By.cssSelector("button[title='Login']");

    private final By alert = By.cssSelector(".alert.alert-danger.alert-error");

    //ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getLoginFieldElement() {
        return driver.findElement(loginField);
    }

    public WebElement getPasswordFieldElement() {
        return driver.findElement(passwordField);
    }

    public WebElement getLoginButtonElement() {
        return driver.findElement(loginButton);
    }

    public WebElement getAlertElement() {
        return driver.findElement(alert);
    }

    //PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void enterLogin(String value) {
        driver.findElement(loginField).sendKeys(value);
    }

    public void enterPassword(String value) {
        driver.findElement(passwordField).sendKeys(value);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
