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
import io.restassured.config.HttpClientConfig;
import io.restassured.RestAssured;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import org.apache.http.conn.ConnectTimeoutException;

@SuppressWarnings("unused")
public class Project_Tests {

    private static final Logger logger = LogManager.getLogger(Project_Tests.class);
    public static String sharedProjectIdFromResponse;
    public String UpdatedprojectIdFromResponse;
    Faker faker;
    Project proj_payload;
    private String userAgent;

    @BeforeClass
    public void setupData() {
        // Set up global timeouts for RestAssured (connection and socket timeouts)
        RestAssured.config = RestAssured.config().httpClient(
            HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)  // 5 seconds connection timeout
                .setParam("http.socket.timeout", 5000)      // 5 seconds read timeout
        );

        logger.info("Setting up data for tests...");
        faker = new Faker();
        proj_payload = new Project();

        proj_payload.setProjectId("string1234");
        proj_payload.setCompanyId("string1234");
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
        proj_payload.setLastUpdated(0);
        logger.info("Data setup complete.");
    }

    @BeforeMethod
    @Parameters("User-Agent")
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void test_CreateProject() {
        try {
            logger.info("Creating project with Project Id: " + this.proj_payload.getProjectId());

            Response response = ProjectEndPoints.createProject(proj_payload, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project created successfully.");

            sharedProjectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Project Id (From response): " + sharedProjectIdFromResponse);
            Assert.assertNotNull(sharedProjectIdFromResponse);

            saveProjectIdToPropertiesFile(sharedProjectIdFromResponse);

        } catch (Exception e) {
            logger.error("Error occurred while creating the project: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    private void saveProjectIdToPropertiesFile(String projectId) {
        Properties properties = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties")) {
            properties.setProperty("sharedProjectId", projectId);
            properties.store(output, null);
            logger.info("Project ID saved to config.properties: " + projectId);
            try (FileInputStream input = new FileInputStream("C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\src\\test\\resources\\config_projectId.properties")) {
                properties.load(input);
                logger.info("Loaded Project ID from config.properties: " + properties.getProperty("sharedProjectId"));
            }
        } catch (IOException io) {
            logger.error("Error saving Project ID to config.properties: " + io.getMessage());
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProject() {
        try {
            logger.info("Getting project with Id: " + sharedProjectIdFromResponse);

            Response response = ProjectEndPoints.getProject(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the project: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void test_UpdateProject() {
        try {
            logger.info("Updating project with Id: " + sharedProjectIdFromResponse);

            proj_payload.setProjectId(sharedProjectIdFromResponse);
            proj_payload.setDescription(faker.name().lastName());
            proj_payload.setProgress(20);

            Response response = ProjectEndPoints.updateProject(sharedProjectIdFromResponse, proj_payload, userAgent);
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Project updated successfully.");

            UpdatedprojectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Updated Project Id (From updated response): " + UpdatedprojectIdFromResponse);
            Assert.assertNotNull(UpdatedprojectIdFromResponse);

            Assert.assertEquals(response.jsonPath().getInt("projects.progress"), proj_payload.getProgress());

        } catch (Exception e) {
            logger.error("Error occurred while updating the project: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
