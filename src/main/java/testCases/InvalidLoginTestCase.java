package testCases;

import baseClasses.BaseTestCase;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import utils.ExtentManager;
import utils.TestDataManager;

import java.io.IOException;

@Listeners(utils.Listeners.class)
public class InvalidLoginTestCase extends BaseTestCase {

    @Test
    public void login() throws IOException {

        String[][] credentials = TestDataManager.getInvalidCredentials();

        ExtentManager.log("Starting InvalidLoginTestCase.");

        HomePageObject homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        ExtentManager.log("HomePage loaded successfully.");

        homePage.clickLoginOrRegisterLink();
        ExtentManager.log("loginOrRegisterLink clicked successfully.");

        LoginPageObject loginPage = new LoginPageObject();
        waitForPageToBeLoaded(10);
        ExtentManager.log("LoginPage loaded successfully.");

        loginPage.enterLogin(credentials[1][0]);
        ExtentManager.log("Email entered successfully.");

        loginPage.enterPassword(credentials[1][1]);
        ExtentManager.log("Password entered successfully.");

        loginPage.clickLoginButton();
        ExtentManager.log("Login button clicked successfully.");

        try {
            if (loginPage.getAlertElement().isDisplayed()) {
                ExtentManager.pass("The alert is visible as expected.");
            } else {
                Assert.fail("Alert is not visible.");
                ExtentManager.fail("Alert is not visible.");
            }
        } catch (NoSuchElementException e) {
            Assert.fail("Alert element not found.");
            ExtentManager.fail("Alert element not found.");
        }
    }
}
