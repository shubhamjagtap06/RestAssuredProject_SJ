package api.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.FloorEndPoints;
import api.payload.Floor;
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
        faker = new Faker(); // Prepare object
        floor_payload = new Floor(); // Prepare object  

        // Setting floor data
        floor_payload.setProjectId(sharedProjectIdFromResponse);
        floor_payload.setBuildingId(sharedBuildingIdFromResponse);
        floor_payload.setFloorId(faker.idNumber().toString());
        floor_payload.setLevelId(faker.idNumber().toString());
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
        floor_payload.setArchived(false);
        
        logger.info("Data setup complete.");
    }

    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    private void loadSharedProjectIdFromConfigFile() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties")) {
            properties.load(input);
            sharedProjectIdFromResponse = properties.getProperty("sharedProjectId");
            if (sharedProjectIdFromResponse != null) {
                logger.info("Loaded Project ID: " + sharedProjectIdFromResponse);
            } else {
                logger.error("sharedProjectId not found");
            }
        } catch (IOException e) {
            logger.error("Error loading Project ID: " + e.getMessage());
        }
    }

    private void loadSharedBuildingIdFromConfigFile() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_buildingId.properties")) {
            properties.load(input);
            sharedBuildingIdFromResponse = properties.getProperty("sharedBuildingId");
            if (sharedBuildingIdFromResponse != null) {
                logger.info("Loaded Building ID: " + sharedBuildingIdFromResponse);
            } else {
                logger.error("sharedBuildingId not found");
            }
        } catch (IOException e) {
            logger.error("Error loading Building ID: " + e.getMessage());
        }
    }

    
    
    
    // Get all Floors
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetFloors() {
        try {
            logger.info("Getting all Floors");

            Response response = FloorEndPoints.getFloors(userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floors retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    
    
    
    // Add new floor
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void test_AddFloor() {
        try {
            logger.info("Adding Floor to Project Id: " + this.floor_payload.getProjectId());
            logger.info("Adding Floor to Building Id: " + this.floor_payload.getBuildingId());

            Response response = FloorEndPoints.addFloor(floor_payload, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Floor is added successfully to Building ID:" + this.floor_payload.getBuildingId());

            sharedFloorIdFromResponse = response.jsonPath().getString("floorId");
            logger.info("Floor Id (From response): " + sharedFloorIdFromResponse);
            saveFloorIdToPropertiesFile(sharedFloorIdFromResponse);
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    private void saveFloorIdToPropertiesFile(String floorId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_floorId.properties")) {
            properties.setProperty("sharedFloorId", floorId);
            properties.store(output, null);
            logger.info("Floor ID saved to config.properties: " + floorId);
        } catch (IOException io) {
            logger.error("Error saving Floor ID: " + io.getMessage());
        }
    }

    
    
    
    // Get all floors by building id
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAllFloorsByBuildingId() {
        try {
            logger.info("Getting all Floors by Building Id:" + this.floor_payload.getBuildingId());

            Response response = FloorEndPoints.getFloorsByBuildingId(this.floor_payload.getBuildingId(), userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floors by Building Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    
    
    
    // Update Floor
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void test_Update_Floor() {
        try {
            logger.info("Updating Floor with Floor Id: " + sharedFloorIdFromResponse);

            floor_payload.setBuildingId(sharedBuildingIdFromResponse);
            floor_payload.setProjectId(sharedProjectIdFromResponse);
            floor_payload.setFloorId(sharedFloorIdFromResponse);
            floor_payload.setTotalBuildUpArea(30);
            floor_payload.setProgress(50);

            Response response = FloorEndPoints.updateFloor(sharedFloorIdFromResponse, floor_payload, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Floor updated successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
 // Get floor by floor id
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetFloorByFloorId() {
        try {
            logger.info("Getting Floor by floor Id:" + sharedFloorIdFromResponse);

            Response response = FloorEndPoints.getFloorByFloorId(sharedFloorIdFromResponse, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("Floor by floor Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
 // Get Archived Floors
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetArchivedFloors() {
        try {
            logger.info("Getting all Archived Floors");

            Response response = FloorEndPoints.getArchivedFloors(userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Archived Floors retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
 // Get All Floors History
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAllFloorsHistory() {
        try {
            logger.info("Getting All Floors History");

            Response response = FloorEndPoints.getAllFloorsHistory(userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("All Floors History retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
 // Get floor by floor id
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetBargraphByFloorId() {
        try {
            logger.info("Getting Bargraph by floor Id:" + sharedFloorIdFromResponse);

            Response response = FloorEndPoints.getFloorByFloorId(sharedFloorIdFromResponse, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
            logger.info("Bargraph by floor Id retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
