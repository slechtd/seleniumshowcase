package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import baseClasses.BasePageObject;

public class MyAccountPageObject extends BasePageObject {

    // LOCATORS - not to be accessed directly, use getters below.
    private final By myAccountHeader = By.cssSelector(".maintext");

    // ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().
    public WebElement getMyAccountHeaderElement() {
        return driver.findElement(myAccountHeader);
    }
}
