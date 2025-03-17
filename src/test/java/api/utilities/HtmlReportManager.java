package api.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@SuppressWarnings("unused")
public class HtmlReportManager implements ITestListener {

    private ExtentReports extent;
    private ExtentSparkReporter sparkReporter;
    private String reportPath;
    private ExtentTest test;
    String repName;
    String excelFileName;
    Workbook workbook;
    Sheet sheet;
    private String application = "Construct Monitor Report";
    private String os = "Windows 11 Pro";
    private String userName = "SEZ5850";
    private String environment = "QA";
    private String user = "Shubham Jagtap";
    private ExcelReportManager excelReportManager;
    
    @Override
    public void onStart(ITestContext testContext) {
        System.out.println("Extent Report: onStart is called");

        // Create timestamp for report file name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html"; // Ensure report name has timestamp
        excelFileName = "TestResults-" + timeStamp + ".xlsx";  // Excel report name with timestamp

     // Initialize ExcelReportManager to handle Excel report creation
        excelReportManager = new ExcelReportManager();
        
        
        // Define report folder and ensure it exists
        String reportFolder = System.getProperty("user.dir") + "\\ExtentReports1_CM";
        File reportDir = new File(reportFolder);
        if (!reportDir.exists()) {
            boolean created = reportDir.mkdirs();
            if (created) {
                System.out.println("Created ExtentReports folder: " + reportFolder);
            } else {
                System.out.println("Failed to create report folder.");
            }
        }

        // Delete previous Excel reports before creating a new one
        File previousExcelFile = new File(reportFolder + "\\" + excelFileName);
        if (previousExcelFile.exists()) {
            boolean deleted = previousExcelFile.delete(); // Delete the old Excel report
            if (deleted) {
                System.out.println("Deleted previous Excel report: " + previousExcelFile.getName());
            } else {
                System.out.println("Failed to delete the previous Excel report.");
            }
        }

        // Clear previous HTML reports to avoid conflicts
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

        // Define the full path for the HTML report
        String reportPath = reportFolder + "\\" + repName;
        System.out.println("HTML Report Path: " + reportPath);

        // Initialize ExtentSparkReporter with the report path
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject1");
        sparkReporter.config().setReportName("Construct Monitor Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", application);
        extent.setSystemInfo("Operating System", os);
        extent.setSystemInfo("User Name", userName);
        extent.setSystemInfo("Environment", environment);
        extent.setSystemInfo("User", user);

        
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
     // Log result to Excel report
        excelReportManager.logTestResult(result.getName(), "PASS");  // Ensure this is being called
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
     // Log result to Excel report
        excelReportManager.logTestResult(result.getName(), "FAIL");  // Ensure this is being called
    
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
     // Log result to Excel report
        excelReportManager.logTestResult(result.getName(), "SKIP");  // Ensure this is being called
    }

    @Override
    public void onFinish(ITestContext testContext) {
        System.out.println("Extent Report: onFinish is called");
        extent.flush();  // Ensure the Extent report is written at the end of the test suite
        System.out.println("Extent Report written successfully.");

        // Save the Excel report
        try {
            excelReportManager.saveReport();  // Ensure the Excel report is saved at the end
            excelReportManager.copyReportToFolder(System.getProperty("user.dir") + "\\ExtentReports1_CM");
        } catch (IOException e) {
            System.err.println("Error generating Excel report: " + e.getMessage());
        }

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

        // Send Reports via Email (pass the timestamped HTML file)
        sendEmail("shubham.jagtap@neilsoft.com", "Neilsoft@1", "shubham.jagtap@neilsoft.com", repName);
}
    
    
    
    
    
    public void sendEmail(String senderEmail, String appPassword, String recipientEmail, String reportName) {
        System.out.println("Attempting to send email via Outlook...");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "webmail.neilsoft.com");
        properties.put("mail.smtp.port", "587");
        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });
        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            // Adding recipients
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("divya.chaudhari@neilsoft.com"));
            message.setSubject("Test Execution Report");
            // Find the report based on the timestamped name (repName)
            File reportsDir = new File(System.getProperty("user.dir") + "\\ExtentReports1_CM");
            File htmlReport = new File(reportsDir, reportName); // Use the passed report name
            // Check if the HTML report exists
            if (!htmlReport.exists()) {
                System.out.println("HTML report not found.");
                return;
            }
            // Check if Excel report exists
            File latestExcelReport = getLatestFile(reportsDir, ".xlsx");
            if (latestExcelReport == null || !latestExcelReport.exists()) {
                System.out.println("Excel report not found.");
                return;
            }
            // Create email parts
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Dear Team,\n\nPlease find attached the latest test execution reports.\n\nRegards,\nShubham Jagtap\nNeilsoft QA Automation");
            // HTML report part
            MimeBodyPart htmlReportPart = new MimeBodyPart();
            htmlReportPart.attachFile(htmlReport);
            htmlReportPart.setFileName(htmlReport.getName());
            // Excel report part
            MimeBodyPart excelPart = new MimeBodyPart();
            excelPart.attachFile(latestExcelReport);
            excelPart.setFileName(latestExcelReport.getName());
            // Combine all parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlReportPart);
            multipart.addBodyPart(excelPart);
            // Set the content of the message
            message.setContent(multipart);
            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully with HTML and Excel report!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
    private File getLatestFile(File directory, String extension) {
	    File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(extension));
	    if (files == null || files.length == 0) {
	        return null; // No files found
	    }
	    File latestFile = files[0]; // Assume first file is the latest
	    for (File file : files) {
	        if (file.lastModified() > latestFile.lastModified()) {
	            latestFile = file;
	        }
	    }
	    return latestFile;
	}
}
