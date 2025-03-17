package api.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import api.endpoints.ScanDataEndPoints;
import api.endpoints.SummaryReportEndPoints;
import api.payload.ScanData;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

@SuppressWarnings("unused")
public class SummaryReport_Tests {
	
 // Logger initialization
    private static final Logger logger = LogManager.getLogger(Floor_Tests.class);
 // Declare User-Agent as a class variable
    private String userAgent;
 // Declare sharedProjectId as a class-level variable
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
    public static String sharedFloorIdFromResponse;
    public static String sharedRegionIdFromResponse;
    public static String sharedScanDateIdFromResponse;
    Faker faker;
 // Use DateUtil to get current date in the required format
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
        loadSharedProjectIdFromConfigFile();
        loadSharedBuildingIdFromConfigFile();
        loadSharedFloorIdFromConfigFile();
        loadSharedRegionIdFromConfigFile();
        faker = new Faker(); // Prepare object
        logger.info("Data setup complete.");
    }
    
    
    
    
  //Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }  
  //Load the sharedProjectId from config.properties
	    private void loadSharedProjectIdFromConfigFile() {
	        Properties properties = new Properties();
	        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties")) {
	            properties.load(input);
	            sharedProjectIdFromResponse = properties.getProperty("sharedProjectId");
	            if (sharedProjectIdFromResponse != null) {
	                logger.info("Loaded Project ID from config.properties: " + sharedProjectIdFromResponse);
	            } else {
	                logger.error("sharedProjectId not found in config.properties");
	            }
	        } catch (IOException e) {
	            logger.error("Error loading Project ID from config.properties: " + e.getMessage());
	        }
	    }
  //Load the sharedBuildingId from config.properties
	    private void loadSharedBuildingIdFromConfigFile() {
	        Properties properties = new Properties();
	        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_buildingId.properties")) {
	            properties.load(input);
	            sharedBuildingIdFromResponse = properties.getProperty("sharedBuildingId");
	            if (sharedBuildingIdFromResponse != null) {
	                logger.info("Loaded Building ID from config.properties: " + sharedBuildingIdFromResponse);
	            } else {
	                logger.error("sharedBuildingId not found in config.properties");
	            }
	        } catch (IOException e) {
	            logger.error("Error loading Building ID from config.properties: " + e.getMessage());
	        }
	    }
  //Load the sharedFloorId from config.properties
	    private void loadSharedFloorIdFromConfigFile() {
	        Properties properties = new Properties();
	        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_floorId.properties")) {
	            properties.load(input);
	            sharedFloorIdFromResponse = properties.getProperty("sharedFloorId");
	            if (sharedFloorIdFromResponse != null) {
	                logger.info("Loaded Floor ID from config.properties: " + sharedFloorIdFromResponse);
	            } else {
	                logger.error("sharedFloorId not found in config.properties");
	            }
	        } catch (IOException e) {
	            logger.error("Error loading Floor ID from config.properties: " + e.getMessage());
	        }
	    }
  //Load the sharedRegionId from config.properties
	    private void loadSharedRegionIdFromConfigFile() {
	        Properties properties = new Properties();
	        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_regionId.properties")) {
	            properties.load(input);
	            sharedRegionIdFromResponse = properties.getProperty("sharedRegionId");
	            if (sharedRegionIdFromResponse != null) {
	                logger.info("Loaded Region ID from config.properties: " + sharedRegionIdFromResponse);
	            } else {
	                logger.error("sharedRegionId not found in config.properties");
	            }
	        } catch (IOException e) {
	            logger.error("Error loading Region ID from config.properties: " + e.getMessage());
	        }
	    }
	    
	    
	    
	    
	  //Tests 
	  //Get Project Parameter Progress
	     @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetProjectParameterProgress() {
	        try {
	            logger.info("Getting Project Parameter Progress");
	            Response response = SummaryReportEndPoints.getProjectParameterProgress(sharedProjectIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Project Parameter Progress retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //Get Region Parameter Progress
	     @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetRegionParameterProgress() {
	        try {
	            logger.info("Getting Region Parameter Progress");
	            Response response = SummaryReportEndPoints.getRegionParameterProgress(sharedProjectIdFromResponse, sharedFloorIdFromResponse, sharedRegionIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Region Parameter Progress retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //Get Building Parameter Progress
	     @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetBuildingParameterProgress() {
	        try {
	            logger.info("Getting Building Parameter Progress");
	            Response response = SummaryReportEndPoints.getBuildingParameterProgress(sharedProjectIdFromResponse, sharedBuildingIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Building Parameter Progress retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //Get Floor Parameter Progress
	     @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetFloorParameterProgress() {
	        try {
	            logger.info("Getting Floor Parameter Progress");
	            Response response = SummaryReportEndPoints.getRegionParameterProgress(sharedProjectIdFromResponse, sharedBuildingIdFromResponse, sharedFloorIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Floor Parameter Progress retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //GetAddProgressToFloorParameter
	     @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetAddProgressToFloorParameter() {
	        try {
	            logger.info("Getting Add Progress To Floor Parameter");
	            Response response = SummaryReportEndPoints.getAddProgressToFloorParameter(sharedFloorIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            //Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Add Progress To Floor Parameter retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //GetAddProgressToBuildingParameter
	     @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetAddProgressToBuildingParameter() {
	        try {
	            logger.info("Getting Add Progress To Building Parameter");
	            Response response = SummaryReportEndPoints.getAddProgressToBuildingParameter(sharedBuildingIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            //Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Add Progress To Building Parameter retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }
	     
	     
	     
	   //GetAddProgressToProjectParameter
	     @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class) 
	    public void test_GetAddProgressToProjectParameter() {
	        try {
	            logger.info("Getting Add Progress To Project Parameter");
	            Response response = SummaryReportEndPoints.getAddProgressToProjectParameter(sharedProjectIdFromResponse, userAgent); 
	            response.then().log().all();
	            Assert.assertEquals(response.getStatusCode(), 200);
	            //Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
	            logger.info("Getting Add Progress To Project Parameter retrieved successfully.");
	        } catch (Exception e) {
	            logger.error("Test case failed: " + e.getMessage());
	            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
	                logger.error("Timeout error: " + e.getCause().getMessage());
	            }
	            Assert.fail("Test Case failed: " + e.getMessage());
	        }
	    }

}
