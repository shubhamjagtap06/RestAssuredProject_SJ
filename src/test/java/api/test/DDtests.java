package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.ProjectEndPoints;
import api.payload.Project;
import api.utilities.DataProviders;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer
import io.restassured.response.Response;



public class DDtests {

    private static final Logger logger = LogManager.getLogger(DDtests.class);  // Logger for this class
    Faker faker;

    // Adding retryAnalyzer to the @Test annotation
    @Test(priority = 1, dataProvider = "getAllData", dataProviderClass = DataProviders.class, retryAnalyzer = RetryAnalyzer.class)
    public void test_CreateProject_DDT(String companyId, String projectName, String description, 
                                  String constructionScheduleFrom, String constructionScheduleTo, 
                                  int totalNoOfBuildings, int totalBuildUpArea, String areaUnit, 
                                  String addedBy, String updatedBy, int progress, boolean isActive, 
                                  boolean isDeleted, boolean isArchived) {
        try {
            logger.info("Starting testCreateProject with companyId: {}", companyId);
            faker = new Faker();
            Project projPayload = new Project();
            // Setting up the project data
            projPayload.setProjectId("Proj" + faker.number().numberBetween(100, 999));  // Use the generated project ID
            projPayload.setCompanyId(companyId);
            projPayload.setProjectName(faker.name().fullName());
            projPayload.setDescription(description);
            projPayload.setConstructionScheduleFrom(constructionScheduleFrom);
            projPayload.setConstructionScheduleTo(constructionScheduleTo);
            projPayload.setTotalNoOfBuildings(totalNoOfBuildings);
            projPayload.setTotalBuildUpArea(totalBuildUpArea);
            projPayload.setAreaUnit(areaUnit);
            projPayload.setAddedBy(addedBy);
            projPayload.setUpdatedBy(updatedBy);
            projPayload.setProgress(progress);
            projPayload.setActive(true);
            projPayload.setDeleted(false);
            projPayload.setArchived(false);
            projPayload.setLastUpdated(0);  // now, projPayload has data

            // Log the payload being sent
            logger.info("Project payload: {}", projPayload);
            // Make the API call to create the project
            logger.info("Making API call to create project...");
            Response response = ProjectEndPoints.createProject1(projPayload);
            // Log the response from the API
            response.then().log().all();
            // Assert that the response status code is 200 (success)
            Assert.assertEquals(response.getStatusCode(), 200, "Project creation failed with status code: " + response.getStatusCode());
            // Log success message
            logger.info("Project created successfully with status code: {}", response.getStatusCode());
        } catch (Exception e) {
            // Log any exceptions
            logger.error("Test case failed: ", e);
            Assert.fail("Test Case failed: " + e.getMessage());
            logger.error("Project creation failed.");
        }
    }
}
