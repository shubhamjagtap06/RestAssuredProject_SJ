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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class

public class Project_Tests {

    // Logger initialization
    private static final Logger logger = LogManager.getLogger(Project_Tests.class);
 // Inside Project_Tests class
    public static String sharedProjectIdFromResponse;
    
//public String projectIdFromResponse;
    public String UpdatedprojectIdFromResponse;
    Faker faker;
    Project proj_payload;
// Declare User-Agent as a class variable
    private String userAgent;

    
    
    @BeforeClass // This should execute before methods in endpoints
    public void setupData() 
    {
        logger.info("Setting up data for tests...");

        faker = new Faker(); // Prepare object
        proj_payload = new Project(); // Prepare object  

        // Generating projectId and setting project details
        proj_payload.setProjectId("string123");
        //proj_payload.setCompanyId("C0001");
        proj_payload.setCompanyId("string123");
        //proj_payload.setProjectName("INTERSTELLAR");
        proj_payload.setProjectName(faker.name().firstName());
        proj_payload.setDescription(faker.name().lastName());
        proj_payload.setConstructionScheduleFrom("2025-01-01T12:03:55.621Z");
        proj_payload.setConstructionScheduleTo("2025-12-31T12:03:55.621Z");
        proj_payload.setTotalNoOfBuildings(0);
        proj_payload.setTotalBuildUpArea(10000);
        proj_payload.setAreaUnit("SqFt");
        proj_payload.setAddedBy("UID22");
        proj_payload.setUpdatedBy("UID22");
        proj_payload.setProgress(10);
        proj_payload.setActive(true);
        proj_payload.setDeleted(false);
        proj_payload.setArchived(false);
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
            sharedProjectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Project Id (From response): " + sharedProjectIdFromResponse);
            Assert.assertNotNull(sharedProjectIdFromResponse);  // Ensure the projectId exists
         // Save sharedProjectIdFromResponse to config.properties file
            saveProjectIdToPropertiesFile(sharedProjectIdFromResponse);
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    private void saveProjectIdToPropertiesFile(String projectId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties")) {
            properties.setProperty("sharedProjectId", projectId);
            properties.store(output, null);  // Save the property to file
            logger.info("Project ID saved to config.properties: " + projectId);
         // To confirm the saved value, let's read the file back and log the content
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties\"")) {
                properties.load(input);
                logger.info("Loaded Project ID from config.properties: " + properties.getProperty("sharedProjectId"));
            }
        } catch (IOException io) {
            logger.error("Error saving Project ID to config.properties: " + io.getMessage());
        }
    }
    
    
    
    //Get Project with project Id
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here too
    public void test_GetProject() {
        try {
            logger.info("Getting project with Id: " + sharedProjectIdFromResponse);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.getProject(sharedProjectIdFromResponse, userAgent); // Pass userAgent to the endpoint
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
            logger.info("Updating project with Id: " + sharedProjectIdFromResponse);
            
            proj_payload.setProjectId(sharedProjectIdFromResponse);
            //proj_payload.setProjectName(faker.name().firstName());
            proj_payload.setDescription(faker.name().lastName());
            //proj_payload.setTotalNoOfBuildings(0);
            //proj_payload.setAreaUnit("sqft");
            //proj_payload.setAddedBy("Shubham Jagtap Added");
            //proj_payload.setUpdatedBy("Shubham Jagtap Updated");
            proj_payload.setProgress(20);
            
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.updateProject(sharedProjectIdFromResponse, proj_payload, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project updated successfully.");
            
            
         // Retrieve projectId from updated project response
            UpdatedprojectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Project Id (From updated response): " + UpdatedprojectIdFromResponse);
            Assert.assertNotNull(UpdatedprojectIdFromResponse);  // Ensure the projectId exists
            logger.info("Project Id exists and is not null: " + UpdatedprojectIdFromResponse);
         // Verify update
			/*
			 * System.out.println("Updated Project id from updated response: "
			 * +UpdatedprojectIdFromResponse); Response responseAfterUpdate =
			 * ProjectEndPoints.getProject(UpdatedprojectIdFromResponse, userAgent); // Pass
			 * userAgent to the endpoint responseAfterUpdate.then().log().all();
			 * 
			 * System.out.println("Updated Response got");
			 */
            
         // Validate the updated values:
            
			
			  //System.out.println("pp:"+response.jsonPath().getList("totalNoOfBuildings").get(0));
			  //System.out.println("pq:" +proj_payload.getTotalNoOfBuildings());
			  //Assert.assertEquals(response.jsonPath().getList("totalNoOfBuildings").get(0), proj_payload.getTotalNoOfBuildings());
			  System.out.println("pp:" +response.jsonPath().getInt("projects.progress"));
			  System.out.println("pq:" +proj_payload.getProgress());
			  Assert.assertEquals(+response.jsonPath().getInt("projects.progress"), proj_payload.getProgress());
		} catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    

    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
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
    
    
    
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class) // Retry logic applied here
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
    
    
    
    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class) 
    public void test_GetProjectDetails() {
        try {
            logger.info("Getting project with Id:" +sharedProjectIdFromResponse);
            // Pass the User-Agent header to simulate browser behavior
            Response response = ProjectEndPoints.getProjectDetails(sharedProjectIdFromResponse, userAgent); // Pass userAgent to the endpoint
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project details got successfully.");
        } catch (Exception e) {
            logger.error("Test case failed: " + e.getMessage());
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
