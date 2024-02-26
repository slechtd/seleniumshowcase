package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ContactUsPageObject extends BasePageObject {

    // LOCATORS - not to be accessed directly, use getters bellow.

    private final By firstNameField = By.cssSelector("input#ContactUsFrm_first_name");
    private final By emailField = By.cssSelector("input#ContactUsFrm_email");
    private final By enquiryField = By.cssSelector("textarea#ContactUsFrm_enquiry");
    private final By submitButton = By.cssSelector("button[title='Submit']");
    private final By firstNameErrorAlert = By.cssSelector(".form_field:nth-of-type(1) .element_error");
    private final By emailErrorAlert = By.cssSelector(".form_field:nth-of-type(2) .element_error");
    private final By enquiryErrorAlert = By.cssSelector(".form_field:nth-of-type(3) .element_error");

    // ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getFirstNameFieldElement() {
        return driver.findElement(firstNameField);
    }

    public WebElement getEmailFieldElement() {
        return driver.findElement(emailField);
    }

    public WebElement getEnquiryFieldElement() {
        return driver.findElement(enquiryField);
    }

    public WebElement getSubmitButtonElement() {
        return driver.findElement(submitButton);
    }

    public WebElement getFirstNameErrorElement() {
        return driver.findElement(firstNameErrorAlert);
    }

    public WebElement getEmailErrorElement() {
        return driver.findElement(emailErrorAlert);
    }

    public WebElement getEnquiryErrorElement() {
        return driver.findElement(enquiryErrorAlert);
    }

    // PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void enterFirstName(String value) {
        getFirstNameFieldElement().clear();
        getFirstNameFieldElement().sendKeys(value);
    }

    public void enterEmail(String value) {
        getEmailFieldElement().clear();
        getEmailFieldElement().sendKeys(value);
    }

    public void enterEnquiry(String value) {
        getEnquiryFieldElement().clear();
        getEnquiryFieldElement().sendKeys(value);
    }

    public void clickSubmitButton(){
        getSubmitButtonElement().click();
    }

    // OTHER

    public boolean isFirstNameErrorPresent() {
        return isElementPresent(firstNameErrorAlert);
    }

    public boolean isEmailErrorPresent() {
        return isElementPresent(emailErrorAlert);
    }

    public boolean isEnquiryErrorPresent() {
        return isElementPresent(enquiryErrorAlert);
    }
}
