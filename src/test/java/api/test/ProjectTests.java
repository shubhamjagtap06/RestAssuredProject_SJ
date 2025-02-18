package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.ProjectEndPoints;
import api.payload.Project;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class

public class ProjectTests {

    // Logger initialization
    private static final Logger logger = LogManager.getLogger(ProjectTests.class);

    public String projectIdFromResponse;

    Faker faker;
    Project proj_payload;

    // Declare User-Agent as a class variable
    private String userAgent;

    @BeforeClass // This should execute before methods in endpoints
    public void setupData() {
        logger.info("Setting up data for tests...");

        faker = new Faker(); // Prepare object
        proj_payload = new Project(); // Prepare object  

        // Generating projectId and setting project details
        String generatedProjectId = "Proj" + faker.number().numberBetween(100, 999);
        proj_payload.setProjectId(generatedProjectId);
        proj_payload.setCompanyId(faker.idNumber().toString());
        proj_payload.setProjectName(faker.name().fullName());
        proj_payload.setDescription(faker.name().lastName());
        proj_payload.setConstructionScheduleFrom("2025-01-01T12:03:55.621Z");
        proj_payload.setConstructionScheduleTo("2025-12-31T12:03:55.621Z");
        proj_payload.setTotalNoOfBuildings(5);
        proj_payload.setTotalBuildUpArea(faker.number().hashCode());
        proj_payload.setAreaUnit("sqm");
        proj_payload.setAddedBy("Shubham Jagtap");
        proj_payload.setUpdatedBy("Shubham Jagtap");
        proj_payload.setProgress(10);
        proj_payload.setActive(true);
        proj_payload.setDeleted(false);
        proj_payload.setArchived(true);
        proj_payload.setLastUpdated(0); // Now, proj_payload has data
        
        logger.info("Data setup complete.");
    }

    // Inject User-Agent value from the XML configuration
    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_CreateProject() {
        try {
            logger.info("Creating project with Project Id: " + this.proj_payload.getProjectId());
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.createProject(proj_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            // Assert response status code
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project created successfully.");
            
            // Retrieve projectId from response
            projectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Project Id (From response): " + projectIdFromResponse);
            Assert.assertNotNull(projectIdFromResponse);  // Ensure the projectId exists
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetProject() {
        try {
            logger.info("Getting project with Id: " + projectIdFromResponse);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.getProject(projectIdFromResponse, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_UpdateProject() {
        try {
            logger.info("Updating project with Id: " + projectIdFromResponse);
            
            proj_payload.setProjectName(faker.name().firstName());
            proj_payload.setDescription(faker.name().lastName());
            proj_payload.setTotalNoOfBuildings(15);
            proj_payload.setAreaUnit("sqft");
            proj_payload.setAddedBy("Shubham Jagtap Updated");
            proj_payload.setUpdatedBy("Shubham Jagtap Updated");
            proj_payload.setProgress(20);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.updateProject(projectIdFromResponse, proj_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project updated successfully.");

            // Verify update
            Response responseAfterUpdate = ProjectEndPoints.getProject(projectIdFromResponse, userAgent); // Pass userAgent to the endpoint
            responseAfterUpdate.then().log().all();
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_GetActiveProject() {
        try {
            logger.info("Getting active project with Company Id: C0001 and User ID: UID22");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.getActiveProject("C0001", "UID22", userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Active project retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
    public void test_GetArchivedProject() {
        try {
            logger.info("Getting archived project with Company Id: C0001 and User ID: UID22");
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.getArchivedProject("C0001", "UID22", userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Archived project retrieved successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
