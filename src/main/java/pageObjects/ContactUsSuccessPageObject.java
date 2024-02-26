package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContactUsSuccessPageObject extends BasePageObject {

    // LOCATORS - not to be accessed directly, use getters bellow.

    private final By continueButton = By.linkText("Continue");

    // ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getContinueButtonElement() {
        return driver.findElement(continueButton);
    }

    // PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void clickContinueButton() {
        getContinueButtonElement().click();
    }
}
