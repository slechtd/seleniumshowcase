package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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
                System.setProperty("webdriver.chrome.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "chromedriver" + getOsFilename());
                yield new ChromeDriver(getChromeOptions());
            }
            case "firefox" -> {
                System.setProperty("webdriver.gecko.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "geckodriver" + getOsFilename());
                yield new FirefoxDriver(getFirefoxOptions());
            }
            case "edge" -> {
                System.setProperty("webdriver.edge.driver", baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "drivers" + fileSeparator + "msedgedriver" + getOsFilename());
                yield new EdgeDriver(getEdgeOptions());
            }
            default ->
                    throw new IllegalArgumentException("Invalid driver value found in config.properties: " + browser);
        };

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    private static String getOsFilename() {
        return PropertiesReader.getOsName().contains("win") ? ".exe" : "";
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(PropertiesReader.isHeadless() ? "--headless" : "--headed");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments(PropertiesReader.isHeadless() ? "--headless" : "--headed");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments(PropertiesReader.isHeadless() ? "--headless" : "--headed");
        return options;
    }
}
