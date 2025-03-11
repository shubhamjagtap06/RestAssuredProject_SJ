package api.utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        System.out.println("Extent Report: onStart is called");

        // Create timestamp for report file name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html"; // Ensure report name has timestamp
        //repName = "Test-Report.html"; // Ensure report name has timestamp
        System.out.println("Report Name: "+repName);

        
        // Define report folder and ensure it exists
        String reportFolder = System.getProperty("user.dir") + "\\ExtentReports1_CM";
        //String reportFolder = "C:\\RestAssuredProject\\RestAssuredProject_SJ-master\\New1_ExtentReports";
        //String reportFolder = "D:\\CM_Reports";
        File reportDir = new File(reportFolder);
        if (!reportDir.exists()) {
            boolean created = reportDir.mkdirs(); // Ensure folder creation
            if (created) {
                System.out.println("Created ExtentReports folder: " + reportFolder);
            } else {
                System.out.println("Failed to create report folder.");
            }
        }

        
		/*
		 * File index1File = new File(reportFolder + "\\index.html"); if
		 * (index1File.exists()) { boolean deleted = index1File.delete(); if (deleted) {
		 * System.out.println("Deleted index.html file."); } }
		 */
        
        // Clear previous reports to avoid conflicts
        File[] files = reportDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deleted = file.delete(); // Delete old files
                    if (deleted) {
                        System.out.println("Deleted old report file: " + file.getName());
                    }
                }
            }
        }

        // Define the full path for the report file
        String reportPath = reportFolder + "\\" + repName;
        System.out.println("Report Path: " + reportPath);

        // Initialize ExtentSparkReporter with the report path
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject1");
        sparkReporter.config().setReportName("Construct Monitor Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Construct Monitor Report");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Shubham Jagtap");
    }

    
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    
    public void onFinish(ITestContext testContext) {
        System.out.println("Extent Report: onFinish is called");
        extent.flush();  // Ensure the report is written at the end of the test suite
        System.out.println("Extent Report written successfully.");
        
        
        
     // Copy the generated report to the same folder with "_copy" suffix as timestamp and repname not working
        try {
        	String repName1 = "index.html";
            // Get the original report file path
            File originalReportFile = new File(System.getProperty("user.dir") + "\\ExtentReports1_CM\\" + repName1);
            if (originalReportFile.exists()) {
                // Create a new file name for the copy (e.g., append "_copy" to the original report name)
            	
                String copiedReportName = repName1.replace("index.html", repName);
                File copiedReportFile = new File(System.getProperty("user.dir") + "\\ExtentReports1_CM\\" + copiedReportName);

                // Copy the original report to the new file
                Path originalPath = originalReportFile.toPath();
                Path copiedPath = copiedReportFile.toPath();
                Files.copy(originalPath, copiedPath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Report copied successfully to: " + copiedReportFile.getAbsolutePath());
            } else {
                System.out.println("Original report file not found to copy.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error copying the report: " + e.getMessage());
        }
        
        // After writing the report, convert the generated HTML to PDF
        String htmlReportPath = System.getProperty("user.dir") + "\\ExtentReports1_CM\\" + repName;
        String pdfReportPath = System.getProperty("user.dir") + "\\ExtentReports1_CM\\" + repName.replace(".html", ".pdf");
        
        HtmlToPdfConverter.convertHtmlToPdf(htmlReportPath, pdfReportPath);
    }

    
	/*
	 * String reportFolder = System.getProperty("user.dir") + "\\ExtentReports1_CM";
	 * File index2File = new File(reportFolder + "\\index.html"); { if
	 * (index2File.exists()) { boolean deleted = index2File.delete(); if (deleted) {
	 * System.out.println("Deleted index.html file."); } } }
	 */
}