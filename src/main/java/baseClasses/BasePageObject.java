package baseClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WebDriverFactory;

import java.util.List;

public class BasePageObject {

    protected static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }

    // LOCATORS - not to be accessed directly, use getters bellow.

    private final By loginOrRegisterLink = By.linkText("Login or register");
    private final By welcomeBackElement = By.cssSelector("#customer_menu_top .menu_text");
    private final By dropdownMenuItems = By.cssSelector("#customer_menu_top .sub_menu li");

    // ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getLoginOrRegisterLinkElement() {
        return getDriver().findElement(loginOrRegisterLink);
    }

    public WebElement getWelcomeBackElement() {
        return getDriver().findElement(welcomeBackElement);
    }

    public List<WebElement> getDropdownMenuItems() {
        return getDriver().findElements(dropdownMenuItems);
    }

    // PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void clickLoginOrRegisterLink() {
        getDriver().findElement(loginOrRegisterLink).click();
    }

    public void logOut() {
        clickMenuItem(MenuItem.LOGOFF);
    }


    // PRIVATE METHODS - methods used by page methods

    private void clickMenuItem(MenuItem menuItem) {
        List<WebElement> menuItems = getDropdownMenuItems();
        for (WebElement item : menuItems) {
            if (item.getText().contains(menuItem.getText())) {
                item.click();
                break;
            }
        }
    }

    // OTHER

    public enum MenuItem {
        LOGOFF("Logoff"),
        ACCOUNT_DASHBOARD("Account Dashboard"),
        WISH_LIST("My wish list"),
        EDIT_ACCOUNT("Edit account details"),
        CHANGE_PASSWORD("Change password"),
        ADDRESS_BOOK("Manage Address Book"),
        ORDER_HISTORY("Order history"),
        TRANSACTION_HISTORY("Transaction history"),
        DOWNLOADS("Downloads"),
        NOTIFICATIONS("Notifications");

        private final String text;

        MenuItem(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
