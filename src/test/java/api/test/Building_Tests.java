package api.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import api.endpoints.BuildingEndPoints;
import api.endpoints.ProjectEndPoints;
import api.payload.Building;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class

public class Building_Tests {
	
	// Logger initialization
    private static final Logger logger = LogManager.getLogger(Project_Tests.class);
    // Declare User-Agent as a class variable
    private String userAgent;
    Faker faker;
    Building building_payload;
    
    
    
    //Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    
    
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
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

}
