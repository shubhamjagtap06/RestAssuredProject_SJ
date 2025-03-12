package api.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelReportManager {

    private Workbook workbook;
    private Sheet sheet;
    private String excelFileName;
    private int rowNum = 10;

    // Define hardcoded data (project info)
    String application = "Construct Monitor Report";
    String os = "Windows 10";
    String userName = "SEZ5850";
    String environment = "QA";
    String user = "Shubham Jagtap";
    String testDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    public ExcelReportManager() {
        // Initialize the Excel file name with timestamp
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        excelFileName = "TestResults-" + timeStamp + ".xlsx";  // Excel report name with timestamp

        // Initialize workbook and sheet
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Test Results");

        // Setup the project info header and data in Excel (e.g., Application, OS, etc.)
        addProjectInfoToSheet();
    }

    private void addProjectInfoToSheet() {
    	// Yellow and Bold style for header row
        CellStyle yellowBoldStyle = createYellowBoldCellStyle();

        // Add Project Details in Excel (first few rows)
        createRow(0, "Name", "Value", yellowBoldStyle);
        createRow(1, "Application", application, yellowBoldStyle);
        createRow(2, "Operating System", os, yellowBoldStyle);
        createRow(3, "User Name", userName, yellowBoldStyle);
        createRow(4, "Environment", environment, yellowBoldStyle);
        createRow(5, "User", user, yellowBoldStyle);
        createRow(6, "Test Date", testDate, yellowBoldStyle);

        // Leave two empty rows after "Test Date"
        sheet.createRow(7);
        sheet.createRow(8);

        // Create header row for test results with yellow and bold style
        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue("Test Case Name");
        header.createCell(1).setCellValue("Status");
        header.createCell(2).setCellValue("Time");

        // Apply yellow bold style to header row cells
        header.getCell(0).setCellStyle(yellowBoldStyle);
        header.getCell(1).setCellStyle(yellowBoldStyle);
        header.getCell(2).setCellStyle(yellowBoldStyle);
    }

    private void createRow(int rowIndex, String cell1, String cell2, CellStyle style) {
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(cell1);
        row.createCell(1).setCellValue(cell2);
        row.getCell(0).setCellStyle(style);
        row.getCell(1).setCellStyle(style);
    }

    private CellStyle createYellowBoldCellStyle() {
        // Yellow and bold style for headers
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    private CellStyle createGreenFillCellStyle() {
        // Green fill for PASS status
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }

    private CellStyle createRedFillCellStyle() {
        // Red fill for FAIL status
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        return style;
    }
    
    private CellStyle createDefaultStyle() {
        // Default style for Test Case Name and Time columns (no fill or bold)
        CellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.NO_FILL);  // No fill
        style.setFont(workbook.createFont());  // No bold font
        return style;
    }

    
    


    public void logTestResult(String testName, String status) {
    	 // Create a new row for the test result
        Row row = sheet.createRow(rowNum++);
        
        // Set the values for the test case name, status, and time
        row.createCell(0).setCellValue(testName);  // Test case name
        row.createCell(1).setCellValue(status);    // Test status
        row.createCell(2).setCellValue(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));  // Test time
        
        // Apply default style (no fill or bold) to Test Case Name and Time columns
        CellStyle defaultStyle = createDefaultStyle();
        row.getCell(0).setCellStyle(defaultStyle);  // Test Case Name column
        row.getCell(2).setCellStyle(defaultStyle);  // Time column
        
        // Apply green or red fill to the Status cell (column 1) based on the status
        if (status.equalsIgnoreCase("PASS")) {
            // Apply green fill for PASS status
            CellStyle greenStyle = createGreenFillCellStyle();
            row.getCell(1).setCellStyle(greenStyle);  // Status column
        } else if (status.equalsIgnoreCase("FAIL")) {
            // Apply red fill for FAIL status
            CellStyle redStyle = createRedFillCellStyle();
            row.getCell(1).setCellStyle(redStyle);  // Status column
        } else {
            // Default to no fill for SKIP or other statuses
            CellStyle defaultStatusStyle = createDefaultStyle();
            row.getCell(1).setCellStyle(defaultStatusStyle);  // Status column
        }
    }


    public void saveReport() throws IOException {
        // Auto-size columns for the Excel sheet
        int numColumns = 0;
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                numColumns = Math.max(numColumns, row.getPhysicalNumberOfCells());
            }
        }

        for (int i = 0; i < numColumns; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the Excel report to a file
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            workbook.write(fileOut);
            System.out.println("Excel Report written successfully.");
        }
    }

    public void copyReportToFolder(String folderPath) throws IOException {
        // Copy the Excel report to a new folder
        Path sourcePath = new File(excelFileName).toPath();
        Path destinationPath = new File(folderPath + "\\" + excelFileName).toPath();
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Excel Report copied successfully to: " + destinationPath);
    }
}
