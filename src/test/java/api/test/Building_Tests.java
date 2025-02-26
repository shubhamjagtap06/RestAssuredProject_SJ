package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.BuildingEndPoints;
import api.payload.Building;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.utilities.DateUtil;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class

public class Building_Tests {
    
    // Logger initialization
    private static final Logger logger = LogManager.getLogger(Building_Tests.class);
    // Declare User-Agent as a class variable
    private String userAgent;
    // Declare sharedProjectId as a class-level variable
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
    Faker faker;
    Building building_payload;
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
        faker = new Faker(); // Prepare object
        building_payload = new Building(); // Prepare object  

        // Generating and Setting projectId
        building_payload.setProjectId(sharedProjectIdFromResponse);
        //building_payload.setProjectId("string");
        // Setting project details
        building_payload.setBuildingId(faker.idNumber().toString());
        //building_payload.setBuildingId("Building_ABC");
        building_payload.setBuildingName(faker.name().fullName());
        building_payload.setConstructionScheduleFrom("2025-02-01T12:03:55.621Z");
        building_payload.setConstructionScheduleTo("2025-06-01T12:03:55.621Z");
        building_payload.setTotalNoOfFloors(0);
        building_payload.setTotalBuildUpArea(10);
        building_payload.setAreaUnit("SqFt");
        building_payload.setAddedBy("UID22");
        building_payload.setUpdatedBy("UID22");
        building_payload.setAddedOn(currentDate);
        building_payload.setProgress(10);
        building_payload.setRecStartDate("2025-01-01T12:02:46.537Z");
        building_payload.setRecEndDate("2025-01-31T12:02:46.537Z");
        building_payload.setDeleted(false);
        building_payload.setArchived(false);	// Now, building_payload has data
        
        logger.info("Data setup complete.");
        System.out.println("current date: "+currentDate);
        System.out.println("sharedProjectIdFromResponse:" +sharedProjectIdFromResponse);
    }
    
    // Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    // Load the sharedProjectId from config.properties
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
    
    // Get all buildings
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetBuildings() {
        try {
            logger.info("Getting all Buildings");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = BuildingEndPoints.getBuildings(userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Buildings retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    // Add new building
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_AddBuilding() {
        try {
            logger.info("Adding Building to Project Id: " + this.building_payload.getProjectId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = BuildingEndPoints.addBuilding(building_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Building is added successfully to Project ID:" + this.building_payload.getProjectId());
            
            // Retrieve buildingId from response
            sharedBuildingIdFromResponse = response.jsonPath().getString("buildingId");
            logger.info("Building Id (From response): " + sharedBuildingIdFromResponse);
            // Save sharedProjectIdFromResponse to config.properties file
            saveBuildingIdToPropertiesFile(sharedBuildingIdFromResponse);
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    private void saveBuildingIdToPropertiesFile(String buildingId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_buildingId.properties")) {
            properties.setProperty("sharedBuildingId", buildingId);
            properties.store(output, null);  // Save the property to file
            logger.info("Building ID saved to config.properties: " + buildingId);
            // To confirm the saved value, let's read the file back and log the content
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_buildingId.properties")) {
                properties.load(input);
                logger.info("Loaded Building ID from config.properties: " + properties.getProperty("sharedBuildingId"));
            }
        } catch (IOException io) {
            logger.error("Error saving Building ID to config.properties: " + io.getMessage());
        }
    }
    
    // Get all buildings by project id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetAllBuildingsByProjectId() {
        try {
            logger.info("Getting all Buildings by Project Id:" + this.building_payload.getProjectId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = BuildingEndPoints.getBuildingsByProjectId(this.building_payload.getProjectId(), userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Buildings by Project Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    // Update Building
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_Update_Building() {
        try {
            logger.info("Updating Building with Building Id: " + sharedBuildingIdFromResponse);
            
            building_payload.setBuildingId(sharedBuildingIdFromResponse);
            building_payload.setProjectId(sharedProjectIdFromResponse);
            building_payload.setTotalBuildUpArea(20);
            building_payload.setProgress(50);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = BuildingEndPoints.updateBuilding(sharedBuildingIdFromResponse, building_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Building updated successfully.");
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
