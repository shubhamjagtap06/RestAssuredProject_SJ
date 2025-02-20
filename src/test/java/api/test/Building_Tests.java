package api.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import api.endpoints.BuildingEndPoints;
import api.payload.Building;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
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
    public String buildingIdFromResponse;
    public String UpdatedprojectIdFromResponse;
    //public static String projectIdFromResponse = Project_Tests.sharedProjectIdFromResponse;
    
    
    Faker faker;
    Building building_payload;
 // Use DateUtil to get current date in the required format
    public String currentDate = DateUtil.getCurrentDateInISOFormat();
    
    
    @BeforeClass // This should execute before methods in endpoints
    public void setupData() {
        logger.info("Setting up data for tests...");

     // Load the sharedProjectId from config.properties
        loadSharedProjectIdFromConfigFile();
        faker = new Faker(); // Prepare object
        building_payload = new Building(); // Prepare object  

        // Generating projectId and setting project details
        //String generatedProjectId = "Proj" + faker.number().numberBetween(100, 999);
        //proj_payload.setProjectId(generatedProjectId);
        building_payload.setProjectId(sharedProjectIdFromResponse);
        building_payload.setBuildingId(faker.idNumber().toString());
        building_payload.setBuildingName(faker.name().fullName());
        building_payload.setConstructionScheduleFrom("2025-02-01T12:03:55.621Z");
        building_payload.setConstructionScheduleTo("2025-02-28T12:03:55.621Z");
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
    
    //Inject User-Agent value from the XML configuration
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
    
    
    //@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
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
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "test_CreateProject") // Retry logic applied here
    public void test_AddBuilding() {
        try {
        	System.out.println("Shared Project ID from Project_Tests: " + Project_Tests.sharedProjectIdFromResponse);
            logger.info("Adding Building to Project Id: " + this.building_payload.getProjectId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = BuildingEndPoints.addBuilding(building_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Building is added successfully to Project ID:" +sharedProjectIdFromResponse);
            
            // Retrieve projectId from response
            buildingIdFromResponse = response.jsonPath().getString("buildingId");
            logger.info("Building Id (From response): " + buildingIdFromResponse);
            
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

}
