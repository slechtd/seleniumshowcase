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

    private HomePageObject homePage;
    private LoginPageObject loginPage;

    @Test
    public void login() throws IOException, CsvException {
        navigateToLoginPage();
        enterCredentials();
    }

    private void navigateToLoginPage() {
        log("Starting LoginTestCase");

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

            String name = credentialRow[0];
            String password = credentialRow[1];
            String isValid = credentialRow[2];

            // Check for page loading before interacting with the page
            waitForPageToBeLoaded(10);

            loginPage.enterName(name);
            log("Name entered successfully: " + name);

            loginPage.enterPassword(password);
            log("Password entered successfully: " + password);

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
        Assert.assertTrue(loginPage.getAlertElement().isDisplayed(), "Alert element is not visible");
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
