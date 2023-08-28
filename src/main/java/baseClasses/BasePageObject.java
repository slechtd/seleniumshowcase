package baseClasses;

import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;

import java.io.IOException;

public class BasePageObject {

    protected static WebDriver getDriver() throws IOException {
        return WebDriverFactory.getDriver();
    }
}
