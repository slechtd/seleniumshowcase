package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogoutPageObject extends BasePageObject {
    private final WebDriver driver = getDriver();

    //LOCATORS - not to be accessed directly, use getters bellow.

    private final By LogoutHeader = By.cssSelector(".maintext");

    //ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getLogoutHeaderElement() {
        return driver.findElement(LogoutHeader);
    }
}
