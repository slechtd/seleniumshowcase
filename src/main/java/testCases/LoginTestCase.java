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

    @Test
    public void login() throws IOException, CsvException, InterruptedException {

        List<String[]> credentialsList = TestDataManager.getLoginCredentials();

        log("Starting LoginTestCase");

        HomePageObject homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        log("HomePage loaded successfully.");

        homePage.clickLoginOrRegisterLink();
        log("loginOrRegisterLink clicked successfully.");

        LoginPageObject loginPage = new LoginPageObject();
        waitForPageToBeLoaded(10);
        log("LoginPage loaded successfully.");

        for (String[] credentialRow : credentialsList) {

            String name = credentialRow[0];
            String password = credentialRow[1];
            String isValid = credentialRow[2];

            loginPage.enterName(name);
            log("Name entered successfully: " + name);

            loginPage.enterPassword(password);
            log("Password entered successfully: " + password);

            loginPage.clickLoginButton();
            log("Login button clicked successfully.");

            if (Boolean.parseBoolean(isValid)) { // ZDE CHYBA ?

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

            } else {

                Assert.assertTrue(loginPage.getAlertElement().isDisplayed(), "Alert element is not visible");
                pass("Failed to log-in as expected.");

            }
        }
    }
}
