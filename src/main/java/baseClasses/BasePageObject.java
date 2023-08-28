package baseClasses;

import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;

public class BasePageObject {

    protected static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }
}
