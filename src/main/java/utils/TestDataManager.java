package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TestDataManager {

    private static final String fileSeparator = PropertiesReader.getFileSeparator();
    private static final String baseDir = PropertiesReader.getUserDir();
    private static final String testDataDir = baseDir + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "testData" + fileSeparator;

    public static List<String[]> getLoginCredentials() throws IOException, CsvException {
        String csvFilePath = testDataDir + "loginCredentials.csv";
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            return csvReader.readAll();
        }
    }
}
