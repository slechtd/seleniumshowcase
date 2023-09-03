package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {

    private static final WebDriver driver = WebDriverFactory.getDriver();

    private static final String baseDir = System.getProperty("user.dir");

    public static String screenShotDestinationPath;

    public static String takeSnapShot() {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destFile = baseDir + "/out/screenshots/" + timestamp() + ".png";
        screenShotDestinationPath = destFile;

        try {
            FileUtils.copyFile(srcFile, new File(destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenShotDestinationPath;  // Return the path of the screenshot.
    }


    private static String timestamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
    }

    public static String getScreenShotDestinationPath() {
        return screenShotDestinationPath;
    }
}
