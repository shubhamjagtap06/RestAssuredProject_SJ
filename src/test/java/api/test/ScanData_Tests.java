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
import api.payload.ScanData;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;


public class ScanData_Tests {
	
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
    ScanData scandata_payload;
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
     // Load the sharedProjectId from config.properties
       // loadSharedProjectIdFromConfigFile();
        // Load the sharedProjectId from config.properties
        //loadSharedBuildingIdFromConfigFile();
        //loadSharedFloorIdFromConfigFile();
        loadSharedRegionIdFromConfigFile();
        faker = new Faker(); // Prepare object
        scandata_payload = new ScanData(); // Prepare object  
        
        scandata_payload.setScanDate("2025-01-02T12:02:46.537Z");
        scandata_payload.setRegionId(sharedRegionIdFromResponse);
        scandata_payload.setUploadedBy("UID22");
        logger.info("Data setup complete.");
    }
    
    
    
    
  //Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
    
    
    
    
  //Get all Scan Data's
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetScanData() {
        try {
            logger.info("Getting all Scan Data's");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ScanDataEndPoints.getScanData(userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Scan Data's retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Add new Scan Date
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_AddScanDate() {
        try {
        	
        	//logger.info("Adding Region to Project Id: " +this.region_payload.getProjectId());
            //logger.info("Adding Region to Building Id: " +this.region_payload.getBuildingId());
            logger.info("Adding Scan Date to Region Id: " +this.scandata_payload.getRegionId());
  
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ScanDataEndPoints.addScanDate(scandata_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Scan Date is added successfully to Region ID:" +this.scandata_payload.getRegionId());
            
            // Retrieve buildingId from response
            sharedScanDateIdFromResponse = response.jsonPath().getString("scandata.scanId");
            logger.info("Scan Date Id (From response): " + sharedScanDateIdFromResponse);
         // Save sharedProjectIdFromResponse to config.properties file
            saveScanDateIdToPropertiesFile(sharedScanDateIdFromResponse);
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    private void saveScanDateIdToPropertiesFile(String scandateId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_scanDateId.properties")) {
            properties.setProperty("sharedScanDateId", scandateId);
            properties.store(output, null);  // Save the property to file
            logger.info("scandate ID saved to config.properties: " + scandateId);
         // To confirm the saved value, let's read the file back and log the content
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_regionId.properties\"")) {
                properties.load(input);
                logger.info("Loaded scandate Id from config.properties: " + properties.getProperty("sharedScanDateId"));
            }
        } catch (IOException io) {
            logger.error("Error saving scandate ID to config.properties: " + io.getMessage());
        }
    }
}





