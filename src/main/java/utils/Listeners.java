package utils;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

    public Listeners() throws IOException {
        super();
    }

    public synchronized void onStart(ITestContext context) {
        ExtentManager.getReport();
        ExtentManager.createTest(context.getName(), context.getName());
    }

    public synchronized void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());
        try {
            System.out.println("Test failed: " + result.getName());
            ScreenshotManager.takeSnapShot(result.getMethod().getMethodName());
            ExtentManager.attachImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void onFinish(ITestContext context) {
        ExtentManager.flushReport();
    }
}
