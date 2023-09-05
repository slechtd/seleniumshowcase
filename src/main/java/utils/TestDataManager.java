package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class TestDataManager {

    private static final String baseDir = PropertiesReader.getUserDir();
    private static final String fileSeparator = PropertiesReader.getFileSeparator();

    public static String[][] getValidCredentials() throws IOException {
        return getCredentials(fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "testData" + fileSeparator + "validLoginCredentials.xlsx");
    }

    public static String[][] getInvalidCredentials() throws IOException {
        return getCredentials(fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "testData" + fileSeparator + "invalidLoginCredentials.xlsx");
    }

    // PRIVATE METHODS

    private static String[][] getCredentials(String filePath) throws IOException {
        FileInputStream workbookLocation = new FileInputStream(baseDir + filePath);

        try (XSSFWorkbook workbook = new XSSFWorkbook(workbookLocation)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            String[][] credentials = new String[rowCount - 1][colCount];
            populateCredentialsArray(sheet, credentials, rowCount, colCount);

            return credentials;
        }
    }

    private static void populateCredentialsArray(XSSFSheet sheet, String[][] credentials, int rowCount, int colCount) {
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            populateRowData(row, credentials, i - 1, colCount);
        }
    }

    private static void populateRowData(Row row, String[][] credentials, int arrayRowIndex, int colCount) {
        if (row != null) {
            for (int j = 0; j < colCount; j++) {
                credentials[arrayRowIndex][j] = getCellData(row.getCell(j));
            }
        }
    }

    private static String getCellData(Cell cell) {
        return (cell != null) ? cell.toString() : "";
    }
}
