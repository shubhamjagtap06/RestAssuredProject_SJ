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
    private int totalTestCases = 0;  // To keep track of total test cases executed
    private int passCount = 0;       // To keep track of the number of passed test cases
    private int failCount = 0;       // To keep track of the number of failed test cases
    private int skipCount = 0; 		// To track skipped test cases

    // Define hardcoded data (project info)
    String application = "Construct Monitor Report";
    String os = "Windows 11";
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

    
    


    public void logTestResult(String testName, String status) {// Increment the total test case count
        totalTestCases++;

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
            passCount++;  // Increment pass count
            CellStyle greenStyle = createGreenFillCellStyle();
            row.getCell(1).setCellStyle(greenStyle);  // Status column
        } else if (status.equalsIgnoreCase("FAIL")) {
            failCount++;  // Increment fail count
            CellStyle redStyle = createRedFillCellStyle();
            row.getCell(1).setCellStyle(redStyle);  // Status column
        } else if (status.equalsIgnoreCase("SKIP")) {
            skipCount++;  // Increment skip count
            CellStyle skipStyle = createDefaultStyle();  // Apply default style (or you can create a new style for skipped tests)
            row.getCell(1).setCellStyle(skipStyle);  // Status column
        } else {
            CellStyle defaultStatusStyle = createDefaultStyle();
            row.getCell(1).setCellStyle(defaultStatusStyle);  // Status column
        }}


    public void saveReport() throws IOException {
    	// Write all test results and the summary
        addSummaryRow();
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
    
    
    public void addSummaryRow() {
    	// Add an empty row before the summary (to keep one empty row before the totals)
    
        sheet.createRow(rowNum++);

        // Create a new row for summary at the bottom of the test results
        Row summaryRow = sheet.createRow(rowNum++);
        
        // Apply yellow bold style to the label cells
        CellStyle yellowBoldStyle = createYellowBoldCellStyle();
        
        // Create a new style to center align the cells
        CellStyle centerAlignedStyle = workbook.createCellStyle();
        centerAlignedStyle.setAlignment(HorizontalAlignment.CENTER);
        centerAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Set the labels and apply the yellow bold style to them
        Cell cell1 = summaryRow.createCell(0);
        cell1.setCellValue("Total Test Cases Executed");
        cell1.setCellStyle(yellowBoldStyle);

        Cell cell2 = summaryRow.createCell(1);
        cell2.setCellValue("Total Pass");
        cell2.setCellStyle(yellowBoldStyle);

        Cell cell3 = summaryRow.createCell(2);
        cell3.setCellValue("Total Fail");
        cell3.setCellStyle(yellowBoldStyle);
        
        Cell cell4 = summaryRow.createCell(3);  // Fixed column index
        cell4.setCellValue("Total Skip");
        cell4.setCellStyle(yellowBoldStyle);

        // Add the actual counts in the next row, and apply center alignment
        Row countRow = sheet.createRow(rowNum++);
        Cell countCell1 = countRow.createCell(0);
        countCell1.setCellValue(totalTestCases);  // Total Test Cases executed
        countCell1.setCellStyle(centerAlignedStyle); // Apply center alignment style

        Cell countCell2 = countRow.createCell(1);
        countCell2.setCellValue(passCount);       // Total Pass test cases
        countCell2.setCellStyle(centerAlignedStyle); // Apply center alignment style

        Cell countCell3 = countRow.createCell(2);
        countCell3.setCellValue(failCount);       // Total Fail test cases
        countCell3.setCellStyle(centerAlignedStyle); // Apply center alignment style

        Cell countCell4 = countRow.createCell(3);
        countCell4.setCellValue(skipCount);       // Total Skip test cases (new column)
        countCell4.setCellStyle(centerAlignedStyle); // Apply center alignment style}
    }
    
}
