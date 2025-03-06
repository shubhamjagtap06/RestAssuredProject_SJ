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
import api.utilities.RetryAnalyzer; 			//Import the RetryAnalyzer class
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import api.assertions.ProjectAssertions; 		//Import the custom assertions class

@SuppressWarnings("unused")
public class Project_Tests {

    private static final Logger logger = LogManager.getLogger(Project_Tests.class);
    public static String sharedProjectIdFromResponse;
    public static String sharedBuildingIdFromResponse;
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
        //proj_payload.setCompanyId("C0001");
        proj_payload.setProjectName(faker.name().firstName());
        //proj_payload.setProjectName("Amsterdam");
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
            //Assert.assertEquals(response.getStatusCode(), 200);
            // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Project created successfully.");
            sharedProjectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Project Id (From response): " + sharedProjectIdFromResponse);
         //Assert.assertNotNull(sharedProjectIdFromResponse);
         // Use custom assertion for non-null project ID
            ProjectAssertions.assertNotNull(sharedProjectIdFromResponse, "Project ID");

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

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
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
         //Assert.assertEquals(response.getStatusCode(), 200);
         //Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Project updated successfully.");
            UpdatedprojectIdFromResponse = response.jsonPath().getString("projects.projectId");
            logger.info("Updated Project Id (From updated response): " + UpdatedprojectIdFromResponse);
            //Assert.assertNotNull(UpdatedprojectIdFromResponse);
            // Use custom assertion for non-null updated project ID
            ProjectAssertions.assertNotNull(UpdatedprojectIdFromResponse, "Updated Project ID");


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
    
   
    
   
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetActiveProject() {
        try {
            logger.info("Getting Active projects with Company Id: C0001");

            Response response = ProjectEndPoints.getActiveProject("C0001", "UID22", userAgent);
            response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Active Projects retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the active projects: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetArchivedProject() {
        try {
        	logger.info("Getting Archived projects with Company Id: C0001");

        	 Response response = ProjectEndPoints.getArchivedProject("C0001", "UID22", userAgent);
             response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Archived Projects retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the archived projects: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectDetails() {
        try {
            logger.info("Getting project details with Id: " + sharedProjectIdFromResponse);

            Response response = ProjectEndPoints.getProjectDetails(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Project Details retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the project details: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 7, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetTimeLineByProjectId() {
        try {
            logger.info("Getting project Timeline with Project Id: " + sharedProjectIdFromResponse);

            Response response = ProjectEndPoints.getTimeLineByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Project TimeLine Details retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the project TimeLine details: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 8, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAreaByProjectId() {
        try {
            logger.info("Getting project Area with Project Id: " + sharedProjectIdFromResponse);

            Response response = ProjectEndPoints.getAreaByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Project Area retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while retrieving the project Area details: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 9, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetCompanyDetailsByProjectId() {
        try {
            logger.info("Getting Company Details with Project Id: " + sharedProjectIdFromResponse);

            Response response = ProjectEndPoints.getCompanyDetailsByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();

            //Assert.assertEquals(response.getStatusCode(), 200);
         // Use custom assertion for status code
            ProjectAssertions.assertStatusCode(response, 404);
            //logger.info("Project Company Details retrieved successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Company Details with Project Id: " + e.getMessage());
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectsByCompanyIdUserId() {
        try {
            logger.info("Getting Projects By Company Id User Id: C0001, UID22");

            Response response = ProjectEndPoints.getProjectByCompanyIdUserId("C0001","UID22", userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Projects By Company Id User Id: C0001, UID22 retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Projects By Company Id User Id: C0001, UID22: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 11, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetCompanyWiseProjectsByCompanyId() {
        try {
            logger.info("Getting Company Wise Projects By Company Id: C0001 ");
            Response response = ProjectEndPoints.getCompanyWiseProjectByCompanyId("C0001",userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Company Wise Projects By Company Id: C0001 retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Company Wise Projects By Company Id: C0001 " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 12, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectByProjectId() {
        try {
            logger.info("Getting Project By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getProjectByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Project By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Project By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 13, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAccDetailsByProjectId() {
        try {
            logger.info("Getting Acc Details By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getAccDetailsByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 204);
            logger.info("Getting Acc Details By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Acc Details By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    //0603
    @Test(priority = 14, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetSiteAddressByProjectId() {
        try {
            logger.info("Getting Site Address By Project Id: Project ABCDE ID: Proj767 ");
            Response response = ProjectEndPoints.getSiteAddressByProjectId("Proj767", userAgent);
            response.then().log().all();
            //ProjectAssertions.assertStatusCode(response, 404);
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Site Address By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Site Address By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 15, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetFinanceByProjectId() {
        try {
            logger.info("Getting Finance By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getFinanceByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            //ProjectAssertions.assertStatusCode(response, 200);
            ProjectAssertions.assertStatusCode(response, 404);
            logger.info("Getting Finance By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Finance By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 16, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectArchivedDetailsByProjectId() {
        try {
            logger.info("Getting Project Archived Details By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getProjectArchivedDetailsByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Project Archived Details By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Project Archived Details By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 17, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectCompanyByProjectId() {
        try {
            logger.info("Getting Project Company By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getProjectCompanyByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Project Company By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Project Company By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 18, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetProjectHistoryByProjectId() {
        try {
            logger.info("Getting Project History By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getProjectHistoryByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Project History By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Project History By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 19, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAllProjectHistory() {
        try {
            logger.info("Getting All Project History ");
            Response response = ProjectEndPoints.getAllProjectHistory(userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting All Project History retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting All Project History " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetHeatMapValuesByProjectId() {
        try {
            logger.info("Getting Heat Map Values By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getHeatMapValuesByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Heat Map Values Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Heat Map Values By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
	/*
	 * //@Test(priority = 21, retryAnalyzer = RetryAnalyzer.class) public void
	 * test_GetLineGraphValuesByProjectIdBuildingId() { try {
	 * logger.info("Getting Line Graph Values By Project Id Building Id: "
	 * +sharedProjectIdFromResponse +sharedBuildingIdFromResponse); Response
	 * response = ProjectEndPoints.getLineGraphValuesByProjectIdBuildingId(
	 * sharedProjectIdFromResponse, sharedBuildingIdFromResponse, userAgent);
	 * response.then().log().all(); ProjectAssertions.assertStatusCode(response,
	 * 200); logger.
	 * info("Getting Line Graph Values By Project Id Building Id retrived successfully"
	 * ); } catch (Exception e) { logger.
	 * error("Error occurred while Getting Line Graph Values By Project Id Building Id: "
	 * + e.getMessage()); if (e.getCause() instanceof
	 * java.net.SocketTimeoutException || e.getCause() instanceof
	 * org.apache.http.conn.ConnectTimeoutException) {
	 * logger.error("Timeout error: " + e.getCause().getMessage()); }
	 * Assert.fail("Test Case failed: " + e.getMessage()); } }
	 */
    
    
    
    
    @Test(priority = 22, retryAnalyzer = RetryAnalyzer.class)
    public void test_getLineGraphValuesAllBuildingsByProjectId() {
        try {
            logger.info("Getting Line Graph Values All Buildings By Project id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getLineGraphValuesAllBuildingsByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Line Graph Values All Buildings By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Line Graph Values All Buildings By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 23, retryAnalyzer = RetryAnalyzer.class)
    public void test_getSingleLineGraphValueByProjectId() {
        try {
            logger.info("Getting Single Line Graph Value By ProjectId: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.getSingleLineGraphValueByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting Single Line Graph Value By Project Id retrived successfully");
        } catch (Exception e) {
            logger.error("Error occurred while Getting Single Line Graph Value By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
	/*
	 * // @Test(priority = 24, retryAnalyzer = RetryAnalyzer.class) public void
	 * test_GetProjectLineGraphValuesByProjectIdBuildingId() { try {
	 * logger.info("Getting Project Line Graph Values By Project Id Building Id : "
	 * +sharedProjectIdFromResponse +sharedBuildingIdFromResponse); Response
	 * response = ProjectEndPoints.getProjectLineGraphValuesByProjectIdBuildingId(
	 * sharedProjectIdFromResponse, sharedBuildingIdFromResponse, userAgent);
	 * response.then().log().all(); ProjectAssertions.assertStatusCode(response,
	 * 200); logger.
	 * info("Getting Project Line Graph Values By Project Id Building Id retrived successfully"
	 * ); } catch (Exception e) { logger.
	 * error("Error occurred while Project Line Graph Values By Project Id Building Id: "
	 * + e.getMessage()); if (e.getCause() instanceof
	 * java.net.SocketTimeoutException || e.getCause() instanceof
	 * org.apache.http.conn.ConnectTimeoutException) {
	 * logger.error("Timeout error: " + e.getCause().getMessage()); }
	 * Assert.fail("Test Case failed: " + e.getMessage()); } }
	 */
    
    
    
    
    @Test(priority = 25, retryAnalyzer = RetryAnalyzer.class)
    public void test_addProjectDataToArchiveServerByProjectId() {
        try {
            logger.info("Getting add Project Data To Archive Server By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.addProjectDataToArchiveServerByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting add Project Data To Archive Server By Project Id");
        } catch (Exception e) {
            logger.error("Error occurred while Getting add Project Data To Archive Server By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
    
    
    
    
    @Test(priority = 26, retryAnalyzer = RetryAnalyzer.class)
    public void test_addProjectDataToFileServerByProjectId() {
        try {
            logger.info("Getting add Project Data To File Server By Project Id: "+sharedProjectIdFromResponse);
            Response response = ProjectEndPoints.addProjectDataToFileServerByProjectId(sharedProjectIdFromResponse, userAgent);
            response.then().log().all();
            ProjectAssertions.assertStatusCode(response, 200);
            logger.info("Getting add Project Data To File Server By Project Id");
        } catch (Exception e) {
            logger.error("Error occurred while Getting add Project Data To File Server By Project Id: " + e.getMessage());
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
