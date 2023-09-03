package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MiniLogger {
    private static String reportFileName;
    private static String reportFilePath;
    private static final ArrayList<Map<String, String>> info = new ArrayList<>();
    private static final Map<String, ArrayList<Map<String, String>>> logData = new HashMap<>();
    private static final Map<String, ArrayList<String>> screenshotPaths = new HashMap<>();
    private static final Map<String, String> testStatuses = new HashMap<>();

    public static void setReportFileName(String fileName) {
        reportFileName = fileName;
    }

    public static void setReportFilePath(String filePath) {
        reportFilePath = filePath;
    }

    public static void setInfo(String key, String value) {
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put(key, value);
        info.add(infoMap);
    }

    public static void log(String message, String testCaseName) {
        addLogEntry(message, testCaseName);
    }

    public static void pass(String message, String testCaseName) {
        addLogEntry("TEST PASSED: " + message, testCaseName);
        testStatuses.put(testCaseName, "PASSED");
    }

    public static void fail(String message, String testCaseName) {
        addLogEntry("TEST FAILED: " + message, testCaseName);
        testStatuses.put(testCaseName, "FAILED");
    }

    public static void fail(Throwable throwable, String testCaseName) {
        String exceptionMessage = throwable.toString();
        addLogEntry("TEST FAILED: " + exceptionMessage, testCaseName);
        testStatuses.put(testCaseName, "FAILED");
    }

    public static void addScreenshot(String filePath, String testCaseName) {
        screenshotPaths.computeIfAbsent(testCaseName, k -> new ArrayList<>()).add(filePath);
    }

    private static void addLogEntry(String message, String testCaseName) {
        Map<String, String> logEntry = new HashMap<>();
        logEntry.put("timestamp", createTimestamp());
        logEntry.put("message", message);
        logData.computeIfAbsent(testCaseName, k -> new ArrayList<>()).add(logEntry);
    }

    public static void flushReport() {
        Map<String, Object> finalReport = prepareFinalReport();
        writeReportToFile(finalReport);
    }

    private static Map<String, Object> prepareFinalReport() {
        Map<String, Object> finalReport = new LinkedHashMap<>();
        finalReport.put("info", info);

        List<Map<String, Object>> testCasesList = new ArrayList<>();
        for (String key : testStatuses.keySet()) {
            Map<String, Object> testCase = new LinkedHashMap<>();
            testCase.put("name", key);
            testCase.put("status", testStatuses.get(key));
            testCase.put("screenshotPaths", screenshotPaths.getOrDefault(key, new ArrayList<>()));
            testCase.put("logs", logData.getOrDefault(key, new ArrayList<>()));
            testCasesList.add(testCase);
        }

        finalReport.put("testCases", testCasesList);
        return finalReport;
    }

    private static void writeReportToFile(Map<String, Object> finalReport) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(reportFilePath + "/" + reportFileName)) {
            gson.toJson(finalReport, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createTimestamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss-SSS").format(new Date());
    }
}
