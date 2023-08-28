package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class HomePageObject extends BasePageObject {

    private final WebDriver driver = getDriver();

    //LOCATORS - not to be accessed directly, use getters bellow.

    private final By loginOrRegisterLink = By.linkText("Login or register");

    //CONSTRUCTOR

    public HomePageObject() throws IOException {
    }

    //ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getLoginOrRegisterLinkElement() {
        return driver.findElement(loginOrRegisterLink);
    }

    //PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void clickLoginOrRegisterLink() {
        driver.findElement(loginOrRegisterLink).click();
    }
}
