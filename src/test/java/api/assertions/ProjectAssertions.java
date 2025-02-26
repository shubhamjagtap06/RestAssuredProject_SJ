package api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProjectAssertions {

    private static final Logger logger = LogManager.getLogger(ProjectAssertions.class);

    // Custom Assertion Method for Status Code
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode, 
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        logger.info("Status Code is: " + actualStatusCode);
    }

    
    
    // Custom Assertion Method for Non-Null Check
    public static void assertNotNull(Object obj, String message) {
        Assert.assertNotNull(obj, message + " should not be null.");
        logger.info(message + " is not null.");
    }
    
    
    
 // Custom Assertion for Project Id saved to a file
    public static void assertProjectIdSaved(String expectedProjectId) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("path/to/config_projectId.properties")) {
            properties.load(input);
            String savedProjectId = properties.getProperty("sharedProjectId");
            Assert.assertEquals(savedProjectId, expectedProjectId, "Saved Project ID does not match the expected Project ID");
            logger.info("Project ID successfully saved: " + savedProjectId);
        } catch (IOException e) {
            Assert.fail("Failed to load the properties file: " + e.getMessage());
        }
    }
}