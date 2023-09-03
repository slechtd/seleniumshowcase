package testCases;

import baseClasses.BaseTestCase;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.LogoutPageObject;
import pageObjects.MyAccountPageObject;
import utils.TestDataManager;

import java.io.IOException;

@Listeners(utils.Listeners.class)
public class ValidLoginAndLogoutTestCase extends BaseTestCase {

    @Test
    public void login() throws IOException {

        String[][] credentials = TestDataManager.getValidCredentials();

        log("Starting ValidLoginAndLogoutTestCase");

        HomePageObject homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        log("HomePage loaded successfully.");

        homePage.clickLoginOrRegisterLink();
        log("loginOrRegisterLink clicked successfully.");

        LoginPageObject loginPage = new LoginPageObject();
        waitForPageToBeLoaded(10);
        log("LoginPage loaded successfully.");

        loginPage.enterLogin(credentials[0][0]);
        log("Login entered successfully: " + credentials[0][0]);

        loginPage.enterPassword(credentials[0][1]);
        log("Password entered successfully: " + credentials[0][1]);

        loginPage.clickLoginButton();
        log("Login button clicked successfully.");

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
