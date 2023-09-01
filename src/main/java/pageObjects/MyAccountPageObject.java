package pageObjects;

import baseClasses.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyAccountPageObject extends BasePageObject {

    private final WebDriver driver = getDriver();

    //LOCATORS - not to be accessed directly, use getters bellow.

    private final By myAccountHeader = By.cssSelector(".maintext");
    private final By welcomeBackElement = By.cssSelector("#customer_menu_top .menu_text");
    private final By dropdownMenuItems = By.cssSelector("#customer_menu_top .sub_menu li");

    //ELEMENT GETTERS - used when an element needs to be accessed within a TestCase, such as when calling waitForElementVisible().

    public WebElement getMyAccountHeaderElement() {
        return driver.findElement(myAccountHeader);
    }

    public WebElement getWelcomeBackElement() {
        return driver.findElement(welcomeBackElement);
    }

    public List<WebElement> getDropdownMenuItems() {
        return driver.findElements(dropdownMenuItems);
    }

    //PAGE METHODS - correspond to user actions on a given page. Called in a TestCase.

    public void logOut() {
        clickMenuItem(MenuItem.LOGOFF);
    }

    public void goToAccountDashboard() {
        clickMenuItem(MenuItem.ACCOUNT_DASHBOARD);
    }

    public void goToWishList() {
        clickMenuItem(MenuItem.WISH_LIST);
    }

    public void editAccountDetails() {
        clickMenuItem(MenuItem.EDIT_ACCOUNT);
    }

    public void changePassword() {
        clickMenuItem(MenuItem.CHANGE_PASSWORD);
    }

    public void manageAddressBook() {
        clickMenuItem(MenuItem.ADDRESS_BOOK);
    }

    public void viewOrderHistory() {
        clickMenuItem(MenuItem.ORDER_HISTORY);
    }

    public void viewTransactionHistory() {
        clickMenuItem(MenuItem.TRANSACTION_HISTORY);
    }

    public void viewDownloads() {
        clickMenuItem(MenuItem.DOWNLOADS);
    }

    public void viewNotifications() {
        clickMenuItem(MenuItem.NOTIFICATIONS);
    }

    //PRIVATE METHODS - methods that page methods rely on.

    public void clickMenuItem(MenuItem menuItem) {
        List<WebElement> menuItems = getDropdownMenuItems();
        for (WebElement item : menuItems) {
            if (item.getText().contains(menuItem.getText())) {
                item.click();
                break;
            }
        }
    }
}

enum MenuItem {
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
