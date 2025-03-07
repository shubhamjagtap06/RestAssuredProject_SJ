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

import api.endpoints.RegionEndPoints;
import api.payload.Region;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

public class Region_Tests {
	
	// Logger initialization
    private static final Logger logger = LogManager.getLogger(Floor_Tests.class);
 // Declare User-Agent as a class variable
    private String userAgent;
 // Declare sharedProjectId as a class-level variable
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
    public static String sharedFloorIdFromResponse;
    public static String sharedRegionIdFromResponse;
    Faker faker;
    Region region_payload;
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
        loadSharedProjectIdFromConfigFile();
        // Load the sharedProjectId from config.properties
        loadSharedBuildingIdFromConfigFile();
        loadSharedFloorIdFromConfigFile();
        faker = new Faker(); // Prepare object
        region_payload = new Region(); // Prepare object  

        // Generating and Setting projectId
        region_payload.setProjectId(sharedProjectIdFromResponse);
        //floor_payload.setBuildingId(faker.idNumber().toString());
        region_payload.setBuildingId(sharedBuildingIdFromResponse);
        region_payload.setFloorId(sharedFloorIdFromResponse);
        region_payload.setRegionId(faker.idNumber().toString());
        //building_payload.setBuildingId("Building_ABC");
        region_payload.setRegionName(faker.name().fullName());
        
        region_payload.setComment("Comment for the region");
        region_payload.setAddedBy("UID22");
        region_payload.setUpdatedBy("UID22");
        region_payload.setAddedOn(currentDate);
        region_payload.setProgress(0);
        //region_payload.setRecentScanDate("2025-01-05T12:02:46.537Z");
        //region_payload.setRecentScanDate("2025-01-01T12:02:46.537Z");
        region_payload.setRecentScanDate("0001-01-01T00:00:00");
        region_payload.setRecStartDate("2025-01-01T12:02:46.537Z");
        //region_payload.setRecStartDate(currentDate);
        region_payload.setRecEndDate("2025-01-31T12:02:46.537Z");
        //region_payload.setRecEndDate(currentDate);
        region_payload.setDeleted(false);
        	// Now, building_payload has data
        
        logger.info("Data setup complete.");
        System.out.println("current date: "+currentDate);
        System.out.println("sharedProjectIdFromResponse:" +sharedProjectIdFromResponse);
        System.out.println("sharedBuildingIdFromResponse:" +sharedBuildingIdFromResponse);
        System.out.println("sharedFloorIdFromResponse:" +sharedFloorIdFromResponse);
        

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
            logger.error("Error loading Building  ID from config.properties: " + e.getMessage());
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
            logger.error("Error loading Floor  ID from config.properties: " + e.getMessage());
        }
    }
    
    
    
    
    
  //Get all Regions
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetRegions() {
        try {
            logger.info("Getting all Regions");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = RegionEndPoints.getRegions(userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Regions retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Add new Region
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_AddRegion() {
        try {
        	
        	logger.info("Adding Region to Project Id: " +this.region_payload.getProjectId());
            logger.info("Adding Region to Building Id: " +this.region_payload.getBuildingId());
            logger.info("Adding Region to Floor Id: " +this.region_payload.getFloorId());
  
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = RegionEndPoints.addRegion(region_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Region is added successfully to Floor ID:" +this.region_payload.getRegionId());
            
            // Retrieve buildingId from response
            sharedRegionIdFromResponse = response.jsonPath().getString("regionId");
            logger.info("Region Id (From response): " + sharedRegionIdFromResponse);
         // Save sharedProjectIdFromResponse to config.properties file
            saveRegionIdToPropertiesFile(sharedRegionIdFromResponse);
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    private void saveRegionIdToPropertiesFile(String regionId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_regionId.properties")) {
            properties.setProperty("sharedRegionId", regionId);
            properties.store(output, null);  // Save the property to file
            logger.info("Region ID saved to config.properties: " + regionId);
         // To confirm the saved value, let's read the file back and log the content
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_regionId.properties\"")) {
                properties.load(input);
                logger.info("Loaded Region ID from config.properties: " + properties.getProperty("sharedRegionId"));
            }
        } catch (IOException io) {
            logger.error("Error saving Region ID to config.properties: " + io.getMessage());
        }
    }
    
    
    
  //Get all regions by floor id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetAllRegionsByFloorId() {
        try {
            logger.info("Getting all Regions by Floor Id:" +this.region_payload.getFloorId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = RegionEndPoints.getRegionsByFloorId(this.region_payload.getFloorId(),userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Regions by Floor Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
  //Update Region
   @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_Update_Region() {
        try {
            logger.info("Updating Region with Region Id: " +sharedRegionIdFromResponse);
            
            region_payload.setBuildingId(sharedBuildingIdFromResponse);
            region_payload.setProjectId(sharedProjectIdFromResponse);
            region_payload.setFloorId(sharedFloorIdFromResponse);
            region_payload.setRegionId(sharedRegionIdFromResponse);
            region_payload.setProgress(50);
            region_payload.setComment("Region Comment updated");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = RegionEndPoints.updateRegion(sharedRegionIdFromResponse, region_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Region updated successfully.");
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
   }
   
   
   
   
 //Get region by region id
   @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class) 
   public void test_GetRegionByRegionId() {
       try {
           logger.info("Getting Region by Region Id:" +sharedRegionIdFromResponse);
           Response response = RegionEndPoints.getRegionByRegionId(sharedRegionIdFromResponse,userAgent); 
           response.then().log().all();
           Assert.assertEquals(response.getStatusCode(), 200);
           Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
           logger.info("Region by Region Id retrieved successfully.");
       } catch (Exception e) {
           logger.error("Test case failed: " + e.getMessage());
           if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
               logger.error("Timeout error: " + e.getCause().getMessage());
           }
           Assert.fail("Test Case failed: " + e.getMessage());
       }
   }
   
   
   
   
 //Get Id by region id
   @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class) 
   public void test_GetIdByRegionId() {
       try {
           logger.info("Getting Ids by Region Id:" +sharedRegionIdFromResponse);
           Response response = RegionEndPoints.getIdByRegionId(sharedRegionIdFromResponse,userAgent); 
           response.then().log().all();
           Assert.assertEquals(response.getStatusCode(), 200);
           Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
           logger.info("Gettings Ids by Region Id retrieved successfully.");
       } catch (Exception e) {
           logger.error("Test case failed: " + e.getMessage());
           if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
               logger.error("Timeout error: " + e.getCause().getMessage());
           }
           Assert.fail("Test Case failed: " + e.getMessage());
       }
   }
   
   
   
   
 //Get all region history
   @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class) 
   public void test_GetAllRegionHistory() {
       try {
           logger.info("Getting All Region History:" +sharedRegionIdFromResponse);
           Response response = RegionEndPoints.getAllRegionHistory(userAgent); 
           response.then().log().all();
           Assert.assertEquals(response.getStatusCode(), 200);
           Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
           logger.info("Gettings All Region History retrieved successfully.");
       } catch (Exception e) {
           logger.error("Test case failed: " + e.getMessage());
           if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
               logger.error("Timeout error: " + e.getCause().getMessage());
           }
           Assert.fail("Test Case failed: " + e.getMessage());
       }
   }
    
}
