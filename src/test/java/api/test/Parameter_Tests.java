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
import api.endpoints.ParameterEndPoints;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;




public class Parameter_Tests {
	
    private static final Logger logger = LogManager.getLogger(Project_Tests.class);
    private String userAgent;
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
    public static String sharedFloorIdFromResponse;
    public static String sharedRegionIdFromResponse;
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
        
        logger.info("Data setup complete.");
        System.out.println("current date: "+currentDate);
        System.out.println("sharedProjectIdFromResponse:" +sharedProjectIdFromResponse);
        System.out.println("sharedBuildingIdFromResponse:" +sharedBuildingIdFromResponse);
        System.out.println("sharedFloorIdFromResponse:" +sharedFloorIdFromResponse);
        System.out.println("sharedRegionIdFromResponse:" +sharedRegionIdFromResponse);
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
  //Load the sharedBuildingId from config.properties
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
  //Load the sharedBuildingId from config.properties
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
    
    
    
    
  //Get Global Parameter status by company id
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetGlobalParameterStatusByCompanyId() {
        try {
            logger.info("Getting Global Parameter Status By Company Id: C0001");
            Response response = ParameterEndPoints.getGlobalParameterStatusByCompanyId("C0001",userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Global Parameter Status By Company Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Get Project Parameter status by Project id
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetProjectParameterStatusByProjectId() {
        try {
            logger.info("Getting Project Parameter Status By Project Id:" +sharedProjectIdFromResponse);
            Response response = ParameterEndPoints.getProjectParameterStatusByProjectId(sharedProjectIdFromResponse,userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Project Parameter Status By Project Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Get Building Parameter status by Building id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetBuildingParameterStatusByBuildingId() {
        try {
            logger.info("Getting Building Parameter Status By Building Id:" +sharedBuildingIdFromResponse);
            Response response = ParameterEndPoints.getBuildingParameterStatusByBuildingId(sharedBuildingIdFromResponse,userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Building Parameter Status By Building Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Get Floor Parameter status by Floor id
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetFloorParameterStatusByFloorId() {
        try {
            logger.info("Getting Floor Parameter Status By Floor Id: "+sharedFloorIdFromResponse );
            Response response = ParameterEndPoints.getFloorParameterStatusByFloorId(sharedFloorIdFromResponse,userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floor Parameter Status By Floor Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Get Region Parameter status by Region id
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetRegionParameterStatusByRegionId() {
        try {
            logger.info("Getting Region Parameter Status By Region Id: "+sharedRegionIdFromResponse );
            Response response = ParameterEndPoints.getRegionParameterStatusByRegionId(sharedRegionIdFromResponse,userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Region Parameter Status By Region Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
