package testCases;

import baseClasses.BaseTestCase;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import pageObjects.ContactUsPageObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.ContactUsSuccessPageObject;
import pageObjects.HomePageObject;
import utils.TestDataManager;

import java.io.IOException;
import java.util.List;

@Listeners(utils.Listeners.class)
public class ContactUsTestCase extends BaseTestCase {

    // PAGE OBJECTS
    private HomePageObject homePage;
    private ContactUsPageObject contactUsPage;
    private ContactUsSuccessPageObject contactUsSuccessPage;

    @Test
    public void test() throws IOException, CsvException {
        log("Starting LoginTestCase");
        navigateToContactUsPage();
        enterInputs();
    }

    private void navigateToContactUsPage(){
        homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        log("HomePage loaded successfully.");

        homePage.clickContactUsLinkElement();
        log("contactUsLink clicked successfully.");

        contactUsPage = new ContactUsPageObject();
        waitForPageToBeLoaded(10);
        log("ContactUsPage loaded successfully.");
    }

    private void enterInputs() throws IOException, CsvException {
        List<String[]> inputsList = TestDataManager.contactUsInputs();

        for (String[] inputsRow : inputsList) {

            int rowNr = Integer.parseInt(inputsRow[0]);
            String firstName = inputsRow[1];
            boolean isFirstNameValid = Boolean.parseBoolean(inputsRow[2]);
            String email = inputsRow[3];
            boolean isEmailValid = Boolean.parseBoolean(inputsRow[4]);
            String enquiry = inputsRow[5];
            boolean isEnquiryValid = Boolean.parseBoolean(inputsRow[6]);

            contactUsPage.enterFirstName(firstName);
            log("Successfully entered the firstName: " + firstName + " from row " + rowNr);

            contactUsPage.enterEmail(email);
            log("Successfully entered the email: " + email + " from row " + rowNr);

            contactUsPage.enterEnquiry(enquiry);
            log("Successfully entered the enquiry from row " + rowNr);

            contactUsPage.clickSubmitButton();
            log("Submit button clicked successfully.");

            checkExpectedVsActualResults(rowNr, isFirstNameValid, isEmailValid, isEnquiryValid);
        }
    }

    private void checkExpectedVsActualResults(int rowNr, boolean isFirstNameValid, boolean isEmailValid, boolean isEnquiryValid) {

        if (!isFirstNameValid && !contactUsPage.isFirstNameErrorPresent()) {
            Assert.fail("Missing alert for invalid firstName at row: " + rowNr);
        } else if (isFirstNameValid && contactUsPage.isFirstNameErrorPresent()) {
            Assert.fail("Unexpected alert with valid firstName at row: " + rowNr);
        } else if (!isFirstNameValid && contactUsPage.isFirstNameErrorPresent()) {
            log("Alert shown for invalid firstName at row: " + rowNr);
        }  else if (isFirstNameValid && !contactUsPage.isFirstNameErrorPresent()) {
            log("No alert shown for valid firstName at row: " + rowNr);
        }

        if (!isEmailValid && !contactUsPage.isEmailErrorPresent()) {
            Assert.fail("Missing alert for invalid email at row: " + rowNr);
        } else if (isEmailValid && contactUsPage.isEmailErrorPresent()) {
            Assert.fail("Unexpected alert with valid email at row: " + rowNr);
        } else if (!isEmailValid && contactUsPage.isEmailErrorPresent()) {
            log("Alert shown for invalid email at row: " + rowNr);
        } else if (isEmailValid && !contactUsPage.isEmailErrorPresent()) {
            log("No alert shown for valid email at row: " + rowNr);
        }

        if (!isEnquiryValid && !contactUsPage.isEnquiryErrorPresent()) {
            Assert.fail("Missing alert for invalid enquiry at row: " + rowNr);
        } else if (isEnquiryValid && contactUsPage.isEnquiryErrorPresent()) {
            Assert.fail("Unexpected alert with valid enquiry at row: " + rowNr);
        } else if (!isEnquiryValid && contactUsPage.isEnquiryErrorPresent()) {
            log("Alert shown for invalid enquiry at row: " + rowNr);
        } else if (isEnquiryValid && !contactUsPage.isEnquiryErrorPresent()) {
            log("No alert shown for valid enquiry at row: " + rowNr);
        }

        continueTest();
    }

    private void continueTest(){
        contactUsSuccessPage = new ContactUsSuccessPageObject();
        waitForPageToBeLoaded(10);
        log("contactUsSuccessPage loaded successfully.");

        waitForElementVisible(contactUsSuccessPage.getContinueButtonElement(), 10);
        contactUsSuccessPage.clickContinueButton();
        log("clickContinueButton clicked successfully.");

        homePage.clickContactUsLinkElement();
        log("contactUsLink clicked successfully.");
    }
}
