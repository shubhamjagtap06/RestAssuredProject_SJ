package api.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.FloorEndPoints;
import api.payload.Floor;
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

public class Floor_Tests {
	
	// Logger initialization
    private static final Logger logger = LogManager.getLogger(Floor_Tests.class);
 // Declare User-Agent as a class variable
    private String userAgent;
 // Declare sharedProjectId as a class-level variable
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
    public static String sharedFloorIdFromResponse;
    Faker faker;
    Floor floor_payload;
 // Use DateUtil to get current date in the required format
    public String currentDate = DateUtil.getCurrentDateInISOFormat();
    
    
    @BeforeClass 
    // This should execute before methods in endpoints
    public void setupData() {
        logger.info("Setting up data for tests...");
        
     // Load the sharedProjectId from config.properties
        loadSharedProjectIdFromConfigFile();
        // Load the sharedProjectId from config.properties
        loadSharedBuildingIdFromConfigFile();
        faker = new Faker(); // Prepare object
        floor_payload = new Floor(); // Prepare object  

        // Generating and Setting projectId
        floor_payload.setProjectId(sharedProjectIdFromResponse);
        //floor_payload.setBuildingId(faker.idNumber().toString());
        floor_payload.setBuildingId(sharedBuildingIdFromResponse);
        floor_payload.setFloorId(faker.idNumber().toString());
        floor_payload.setLevelId(faker.idNumber().toString());
        //building_payload.setBuildingId("Building_ABC");
        floor_payload.setFloorName(faker.name().fullName());
        floor_payload.setTotalBuildUpArea(10);
        floor_payload.setAreaUnit("SqFt");
        floor_payload.setAddedBy("UID22");
        floor_payload.setUpdatedBy("UID22");
        floor_payload.setAddedOn(currentDate);
        floor_payload.setProgress(10);
        floor_payload.setRecStartDate("2025-01-01T12:02:46.537Z");
        floor_payload.setRecEndDate("2025-01-31T12:02:46.537Z");
        floor_payload.setDeleted(false);
        floor_payload.setArchived(false);	// Now, building_payload has data
        
        logger.info("Data setup complete.");
        System.out.println("current date: "+currentDate);
        System.out.println("sharedProjectIdFromResponse:" +sharedProjectIdFromResponse);
        System.out.println("sharedBuildingIdFromResponse:" +sharedBuildingIdFromResponse);
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
    
    
    
    
    
  //Get all Floors
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetFloors() {
        try {
            logger.info("Getting all Floors");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = FloorEndPoints.getFloors(userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floors retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
  //Add new floor
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_AddFloor() {
        try {
        	
        	logger.info("Adding Floor to Project Id: " +this.floor_payload.getProjectId());
            logger.info("Adding Floor to Building Id: " +this.floor_payload.getBuildingId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = FloorEndPoints.addFloor(floor_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Floor is added successfully to Building ID:" +this.floor_payload.getBuildingId());
            
            // Retrieve buildingId from response
            sharedFloorIdFromResponse = response.jsonPath().getString("floorId");
            logger.info("Floor Id (From response): " + sharedFloorIdFromResponse);
         // Save sharedProjectIdFromResponse to config.properties file
            saveFloorIdToPropertiesFile(sharedFloorIdFromResponse);
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    private void saveFloorIdToPropertiesFile(String floorId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_floorId.properties")) {
            properties.setProperty("sharedFloorId", floorId);
            properties.store(output, null);  // Save the property to file
            logger.info("Floor ID saved to config.properties: " + floorId);
         // To confirm the saved value, let's read the file back and log the content
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_floorId.properties\"")) {
                properties.load(input);
                logger.info("Loaded Floor ID from config.properties: " + properties.getProperty("sharedFloorId"));
            }
        } catch (IOException io) {
            logger.error("Error saving Floor ID to config.properties: " + io.getMessage());
        }
    }
    
    
    
    
  //Get all buildings by project id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetAllFloorsByBuildingId() {
        try {
            logger.info("Getting all Floors by Building Id:" +this.floor_payload.getBuildingId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = FloorEndPoints.getFloorsByBuildingId(this.floor_payload.getBuildingId(),userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floors by Building Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    //Update Floor
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_Update_Floor() {
        try {
            logger.info("Updating Floor with Floor Id: " +sharedFloorIdFromResponse);
            
            floor_payload.setBuildingId(sharedBuildingIdFromResponse);
            floor_payload.setProjectId(sharedProjectIdFromResponse);
            floor_payload.setFloorId(sharedFloorIdFromResponse);
            floor_payload.setTotalBuildUpArea(30);
            floor_payload.setProgress(80);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = FloorEndPoints.updateFloor(sharedFloorIdFromResponse, floor_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Floor updated successfully.");
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
     }
    
}
