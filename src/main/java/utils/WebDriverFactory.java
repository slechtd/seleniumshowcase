package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class WebDriverFactory {

    private static final String baseDir = PropertiesReader.getUserDir();
    private static final String fileSeparator = PropertiesReader.getFileSeparator();

    public static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    public static void cleanUpDriver() {
        driver.get().quit();
        driver.remove();
    }

    private static WebDriver createDriver() {
        WebDriver driver;
        String browser = PropertiesReader.getBrowser();

        driver = switch (browser) {
            case "chrome" -> {
                System.setProperty("webdriver.chrome.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "chromedriver");
                yield new ChromeDriver();
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "geckodriver");
                yield new FirefoxDriver();
            }
            case "edge" -> {
                System.setProperty("webdriver.edge.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "msedgedriver");
                yield new EdgeDriver();
            }
            default ->
                    throw new IllegalArgumentException("Invalid driver value found in config.properties: " + browser);
        };

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}
