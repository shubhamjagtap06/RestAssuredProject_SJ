package api.test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import api.endpoints.LicenseEndPoints;
import api.endpoints.ParameterEndPoints;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

@SuppressWarnings("unused")
public class License_Tests {
	
	private static final Logger logger = LogManager.getLogger(Project_Tests.class);
    private String userAgent;
    public String currentDate = DateUtil.getCurrentDateInISOFormat();

    
    
    @BeforeClass 
    // This should execute before methods in endpoints
    public void setupData() {
    	// Set up global timeouts for RestAssured (connection and socket timeouts)
        RestAssured.config = RestAssured.config().httpClient(
            HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)  // 5 seconds connection timeout
                .setParam("http.socket.timeout", 5000)      // 5 seconds read timeout
        );
        logger.info("Setting up data for tests...");
        logger.info("Data setup complete.");
        System.out.println("current date: "+currentDate);
    }
    
    
  //Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    
    
    
  //Get User License By Email Id
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) 
    public void test_GetUserLicenseByEmailId() {
        try {
            logger.info("Getting User License By Email Id: shubham.jagtap@neilsoft.com");
            Response response = LicenseEndPoints.getUserLicenseByEmail("shubham.jagtap@neilsoft.com", userAgent); 
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("Getting User License By Email Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
  //Check Logo for Report By Company Id
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void test_CheckLogoforReportByCompanyId() {
        try {
            logger.info("Getting Check Logo for Report By Company Id: C0001");
            Response response = LicenseEndPoints.checkLogoForReportByCompanyId("C0001", userAgent); 
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "text/plain; charset=utf-8");
            logger.info("Getting Check Logo for Report By Company Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
  //Get Logo for Report By Company Id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetLogoforReportByCompanyId() {
        try {
            logger.info("Getting Logo for Report By Company Id: C0001");
            Response response = LicenseEndPoints.getLogoForReportByCompanyId("C0001", userAgent); 
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "text/plain; charset=utf-8");
            logger.info("Getting Logo for Report By Company Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
  //Get Job Processor
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetJobProcessor() {
        try {
            logger.info("Getting Job Processor ");
            Response response = LicenseEndPoints.getJobProcessor(userAgent); 
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("Getting Job Processor retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
  //Get Offset
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetOffset() {
        try {
            logger.info("Getting Offset ");
            Response response = LicenseEndPoints.getOffset(userAgent); 
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("Getting Offset retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
