package testCases;

import baseClasses.BaseTestCase;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.MyAccountPageObject;
import utils.ExtentManager;
import utils.TestDataManager;

import java.io.IOException;

@Listeners(utils.Listeners.class)
public class ValidLoginTestCase extends BaseTestCase {

    @Test
    public void login() throws IOException {

        String[][] credentials = TestDataManager.getValidCredentials();

        ExtentManager.log("Starting InvalidLoginTestCase.");

        HomePageObject homePage = new HomePageObject();
        waitForPageToBeLoaded(10);
        ExtentManager.log("HomePage loaded successfully.");

        homePage.clickLoginOrRegisterLink();
        ExtentManager.log("loginOrRegisterLink clicked successfully.");

        LoginPageObject loginPage = new LoginPageObject();
        waitForPageToBeLoaded(10);
        ExtentManager.log("LoginPage loaded successfully.");

        loginPage.enterLogin(credentials[0][0]);
        ExtentManager.log("Email entered successfully." + credentials[0][0]);

        loginPage.enterPassword(credentials[0][1]);
        ExtentManager.log("Password entered successfully." + credentials[0][1]);

        loginPage.clickLoginButton();
        ExtentManager.log("Login button clicked successfully.");

        MyAccountPageObject myAccountPage = new MyAccountPageObject();
        waitForPageToBeLoaded(10);
        ExtentManager.log("MyAccountPage loaded successfully.");

        String failureMessage = "Header element not found on MyAccountPage";

        try {
            if (myAccountPage.getMyAccountHeaderElement().isDisplayed()) {
                ExtentManager.pass("Successfully logged-in");
            } else {
                Assert.fail(failureMessage);
                ExtentManager.fail(failureMessage);
            }
        } catch (NoSuchElementException e) {
            Assert.fail(failureMessage);
            ExtentManager.fail(failureMessage);
        }
    }
}
