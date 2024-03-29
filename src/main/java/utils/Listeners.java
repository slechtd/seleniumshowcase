package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

    public Listeners() {
        super();
    }

    public synchronized void onStart(ITestContext context) {

    }

    public synchronized void onTestFailure(ITestResult result) {
        String testCase = result.getTestClass().getRealClass().getSimpleName();
        DBLogger.fail(result.getThrowable(), testCase);
        String screenshotPath = ScreenshotManager.takeSnapShot();
        DBLogger.addScreenshot(screenshotPath, testCase);
    }

    public synchronized void onFinish(ITestContext context) {

    }
}
