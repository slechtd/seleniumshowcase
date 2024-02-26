package testCases;

import baseClasses.BaseTestCase;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.LogoutPageObject;
import pageObjects.MyAccountPageObject;
import utils.TestDataManager;

import java.io.IOException;
import java.util.List;

@Listeners(utils.Listeners.class)
public class LoginTestCase extends BaseTestCase {

    // PAGE OBJECTS
    private HomePageObject homePage;
    private LoginPageObject loginPage;

    // TEST METHOD
    @Test
    public void test() throws IOException, CsvException {
        log("Starting LoginTestCase");
        navigateToLoginPage();
        enterCredentials();
    }

    // PRIVATE METHODS
    private void navigateToLoginPage() {
        homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        log("HomePage loaded successfully.");

        homePage.clickLoginOrRegisterLink();
        log("loginOrRegisterLink clicked successfully.");

        loginPage = new LoginPageObject();
        waitForPageToBeLoaded(10);
        log("LoginPage loaded successfully.");
    }

    private void enterCredentials() throws IOException, CsvException {
        List<String[]> credentialsList = TestDataManager.getLoginCredentials();

        for (String[] credentialRow : credentialsList) {

            String rowNr = credentialRow[0];
            String name = credentialRow[1];
            String password = credentialRow[2];
            String isValid = credentialRow[3];

            loginPage.enterName(name);
            log("Successfully entered the name: " + name + " from row " + rowNr);

            loginPage.enterPassword(password);
            log("Successfully entered the password: " + password + " from row " + rowNr);

            loginPage.clickLoginButton();
            log("Login button clicked successfully.");

            if (Boolean.parseBoolean(isValid)) {
                handleValidLogin();
            } else {
                handleInvalidLogin();
            }
        }
    }

    private void handleInvalidLogin() {
        Assert.assertTrue(loginPage.getAlertElement().isDisplayed(), "Alert element is visible");
        pass("Failed to log-in as expected.");
    }

    private void handleValidLogin() {
        MyAccountPageObject myAccountPage = new MyAccountPageObject();
        waitForPageToBeLoaded(10);
        Assert.assertTrue(myAccountPage.getMyAccountHeaderElement().isDisplayed(), "testCase");
        log("MyAccountPage loaded successfully.");

        hoverOverElement(myAccountPage.getWelcomeBackElement());
        waitForElementVisible(myAccountPage.getDropdownMenuItems().get(0), 10);
        log("DropDown menu is visible.");

        myAccountPage.logOut();
        log("Logout button clicked successfully.");

        LogoutPageObject logoutPage = new LogoutPageObject();
        waitForPageToBeLoaded(10);
        log("LogoutPage loaded successfully.");

        String actualHeaderText = logoutPage.getLogoutHeaderElement().getText();
        String expectedHeaderText = "ACCOUNT LOGOUT";
        Assert.assertEquals(actualHeaderText, expectedHeaderText, "The logout header text did not match.");
        pass("Logout Successful.");
    }
}
