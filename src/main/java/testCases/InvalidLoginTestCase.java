package testCases;

import baseClasses.BaseTestCase;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import utils.TestDataManager;

import java.io.IOException;

@Listeners(utils.Listeners.class)
public class InvalidLoginTestCase extends BaseTestCase {

    @Test
    public void login() throws IOException {

        String[][] credentials = TestDataManager.getInvalidCredentials();

        log("Starting InvalidLoginTestCase.");

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

        Assert.assertTrue(loginPage.getAlertElement().isDisplayed(), "Alert element not visible");
        pass("Failed to log-in as expected.");
    }
}
