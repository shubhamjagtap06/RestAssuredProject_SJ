package api.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import api.endpoints.UserEndPoints;
import io.restassured.response.Response;

public class User_Tests {

    private static final Logger logger = LogManager.getLogger(User_Tests.class);  // Logger for this class

    private static String email;
    private static String password;
    public String jwtToken;

    private static String userId;
    private static String companyId;
    private static String firstName;
    private static String lastName;
    private static String userName;
    private static String role;
    private static String updatedBy;
    private static Boolean isActive;

    @BeforeClass
    public void setup() throws IOException {
        logger.info("Starting setup...");

        // Load properties from the config.properties file
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(fis);
            User_Tests.email = properties.getProperty("email");
            User_Tests.password = properties.getProperty("password");

            logger.info("Config file loaded successfully.");
        } catch (IOException e) {
            logger.error("Error loading config file", e);
            throw e;  // Re-throw exception to stop further execution if the setup fails
        }
    }

    public static String getToken() {
        logger.info("Getting JWT token...");

        // Call the loginUser method to get the response
        Response response = UserEndPoints.userLogin();

        // Log the response for debugging
        response.then().log().all();

        // Assert that the login request was successful (HTTP status 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());

        // Check if the token exists
        String authToken = response.jsonPath().getString("jwtToken");
        Assert.assertNotNull(authToken, "Auth token should not be null");
        logger.info("JWT Token obtained: {}", authToken);

        return authToken;
    }

    @Test(priority = 1)
    public static void testLogin() {
        logger.info("Starting testLogin...");

        try {
            Response response = UserEndPoints.userLogin();

            // Log the response for debugging
            response.then().log().all();

            Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());

            // Extract and log user information from the response
            userId = response.jsonPath().getString("user.userId");
            companyId = response.jsonPath().getString("user.companyId");
            firstName = response.jsonPath().getString("user.firstName");
            lastName = response.jsonPath().getString("user.lastName");
            userName = response.jsonPath().getString("user.userName");
            email = response.jsonPath().getString("user.email");
            password = response.jsonPath().getString("user.password");
            role = response.jsonPath().getString("user.role");
            updatedBy = response.jsonPath().getString("user.updatedBy");
            isActive = response.jsonPath().getBoolean("user.isActive");

            logger.info("User logged in successfully. UserID: {}, CompanyID: {}", userId, companyId);
        } catch (Exception e) {
            logger.error("Login failed", e);
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void test_GetAllUser() {
        logger.info("Starting test_GetAllUser...");

        try {
            Response response = UserEndPoints.getAllUser();
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Retrieved all users successfully.");
        } catch (Exception e) {
            logger.error("Failed to retrieve users", e);
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void test_GetUserByEmailId() {
        logger.info("Starting test_GetUserByEmailId...");

        try {
            Response response = UserEndPoints.getUserByEmailId("shubham.jagtap@neilsoft.com");
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Retrieved user by email successfully.");
        } catch (Exception e) {
            logger.error("Failed to retrieve user by email", e);
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testLogout() {
        logger.info("Starting testLogout...");

        // Create the logout request body using user information from the login response
        String logoutRequestBody = "{\n" +
                "  \"userId\": \"" + userId + "\",\n" +
                "  \"companyId\": \"" + companyId + "\",\n" +
                "  \"firstName\": \"" + firstName + "\",\n" +
                "  \"lastName\": \"" + lastName + "\",\n" +
                "  \"userName\": \"" + userName + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"updatedBy\": \"" + updatedBy + "\",\n" +
                "  \"role\": \"" + role + "\",\n" +
                "  \"recStartDate\": \"2025-02-15T07:37:17.339Z\",\n" +
                "  \"recEndDate\": \"2025-02-15T07:37:17.339Z\",\n" +
                "  \"isActive\": " + isActive + "\n" +
                "}";

        try {
            Response response = UserEndPoints.userLogout(logoutRequestBody);
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("User logged out successfully.");
        } catch (Exception e) {
            logger.error("Logout failed", e);
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
