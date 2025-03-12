package api.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import java.io.IOException;

@SuppressWarnings("unused")
public class TestExecution {

    public static void main(String[] args) throws IOException {
        // Initialize the report managers
        HtmlReportManager htmlReportManager = new HtmlReportManager();
        ExcelReportManager excelReportManager = new ExcelReportManager();

        // Initialize dummy values for ITestContext and ITestResult (to simulate a real execution)
        ITestContext testContext = null; // Replace this with actual ITestContext object
        ITestResult testResult = null;   // Replace this with actual ITestResult object

        // Start HTML report
        htmlReportManager.onStart(testContext);  // Simulating the start of the report
        // After each test result:
        htmlReportManager.onTestSuccess(testResult);  // Simulate test success
        htmlReportManager.onTestFailure(testResult);  // Simulate test failure
        htmlReportManager.onTestSkipped(testResult);  // Simulate test skipped
        htmlReportManager.onFinish(testContext);  // End HTML report

        // Create Excel report
        excelReportManager.logTestResult("Test Case Name", "PASS");  // Simulate logging a test result
        excelReportManager.saveReport();  // Save the Excel report
        excelReportManager.copyReportToFolder("desired folder path");  // Copy the report to the specified folder

        // Optionally, you can execute the TestNG tests programmatically if needed:
        TestNG testNG = new TestNG();
        testNG.setTestClasses(new Class[] { api.test.User_Tests.class }); // Add your test classes here
        testNG.run();  // Run the tests programmatically
    }
}
