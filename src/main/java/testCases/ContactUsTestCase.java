package testCases;

import baseClasses.BaseTestCase;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import pageObjects.ContactUsPageObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import utils.TestDataManager;

import java.io.IOException;
import java.util.List;

@Listeners(utils.Listeners.class)
public class ContactUsTestCase extends BaseTestCase {

    // PAGE OBJECTS
    private HomePageObject homePage;
    private ContactUsPageObject contactUsPage;

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

        boolean isFirstNameErrorPresent = contactUsPage.isFirstNameErrorPresent();
        boolean isEmailErrorPresent = contactUsPage.isEmailErrorPresent();
        boolean isEnquiryErrorPresent = contactUsPage.isEnquiryErrorPresent();

        checkAlert(isFirstNameValid, isFirstNameErrorPresent, "firstName", rowNr);
        checkAlert(isEmailValid, isEmailErrorPresent, "email", rowNr);
        checkAlert(isEnquiryValid, isEnquiryErrorPresent, "enquiry", rowNr);

        continueTest();
    }

    @SuppressWarnings("ConstantConditions")
    private void checkAlert(boolean isValid, boolean isErrorPresent, String fieldName, int rowNr) {
        if (!isValid && !isErrorPresent) Assert.fail("Missing alert for invalid " + fieldName + " at row: " + rowNr);
        else if (isValid && isErrorPresent) Assert.fail("Unexpected alert with valid " + fieldName + " at row: " + rowNr);
        else if (!isValid && isErrorPresent) log("Alert shown for invalid " + fieldName + " at row: " + rowNr);
        else if (isValid && !isErrorPresent) log("No alert shown for valid " + fieldName + " at row: " + rowNr);
    }

    private void continueTest(){
        homePage.clickContactUsLinkElement();
        log("contactUsLink clicked successfully.");
    }
}
