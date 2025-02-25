package api.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import api.endpoints.UserEndPoints;
import api.utilities.RetryAnalyzer; // Import the RetryAnalyzer class
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.response.Response;

public class User_Tests {

    private static final Logger logger = LogManager.getLogger(User_Tests.class);
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
    private String userAgent;

    @BeforeClass
    public void setup() throws IOException {
        logger.info("Starting setup...");

        // Set timeouts for Rest Assured globally
        RestAssured.config = RestAssured.config().httpClient(
            HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)  // 5 seconds connection timeout
                .setParam("http.socket.timeout", 5000)      // 5 seconds read timeout
        );

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

    @BeforeMethod
    @Parameters("User-Agent")  // Inject User-Agent value from the XML configuration
    public void setupUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public static String getToken(String userAgent) {
        logger.info("Getting JWT token...");
        Response response = UserEndPoints.userLogin(userAgent);  // Pass the User-Agent
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());
        String authToken = response.jsonPath().getString("jwtToken");
        Assert.assertNotNull(authToken, "Auth token should not be null");
        logger.info("JWT Token obtained: {}", authToken);
        return authToken;
    }
    
    
    public static String getToken1() {
        logger.info("Getting JWT token...");

        // Call the loginUser method to get the response
        Response response = UserEndPoints.userLogin1();
        // Assert that the login request was successful (HTTP status 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());
        // Check if the token exists
        String authToken = response.jsonPath().getString("jwtToken");
        Assert.assertNotNull(authToken, "Auth token should not be null");
        logger.info("JWT Token obtained: {}", authToken);
        return authToken;
    }
    

    // Test Login with retry and timeout handling
    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void testLogin() {
        logger.info("Starting testLogin...");
        try {
            Response response = UserEndPoints.userLogin(userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());
            // Extract and log user information
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
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    // Get all Users with retry and timeout handling
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetAllUser() {
        logger.info("Starting test_GetAllUser...");
        try {
            Response response = UserEndPoints.getAllUser(userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Retrieved all users successfully.");
        } catch (Exception e) {
            logger.error("Failed to retrieve users", e);
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    // Get User by Email Id with retry and timeout handling
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    public void test_GetUserByEmailId() {
        logger.info("Starting test_GetUserByEmailId...");
        try {
            Response response = UserEndPoints.getUserByEmailId("shubham.jagtap@neilsoft.com", userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Retrieved user by email successfully.");
        } catch (Exception e) {
            logger.error("Failed to retrieve user by email", e);
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    // Logout with retry and timeout handling
    @Test(priority = 6, retryAnalyzer = RetryAnalyzer.class)
    public void testLogout() {
        logger.info("Starting testLogout...");
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
            Response response = UserEndPoints.userLogout(logoutRequestBody, userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("User logged out successfully.");
        } catch (Exception e) {
            logger.error("Logout failed", e);
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    // Forgot Password with retry and timeout handling
    @Test(priority = 4, retryAnalyzer = RetryAnalyzer.class)
    public void test_forgotPassword() {
        logger.info("Starting test_forgotPassword...");
        try {
            Response response = UserEndPoints.forgotPassword("shubham.jagtap@neilsoft.com", userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Forgot password link sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send forgot password link", e);
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }

    // Send OTP to Email with retry and timeout handling
    @Test(priority = 5, retryAnalyzer = RetryAnalyzer.class)
    public void test_sendOtpToEmail() {
        logger.info("Starting test_sendOtpToEmail...");
        try {
            Response response = UserEndPoints.sendOtpToEmail("shubham.jagtap@neilsoft.com", userAgent);  // Pass the User-Agent
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
            logger.info("Send OTP to email sent successfully");
        } catch (Exception e) {
            logger.error("Failed to send OTP to email", e);
            // Check if it's a timeout-related exception and handle separately
            if (e.getCause() instanceof java.net.SocketTimeoutException || e.getCause() instanceof org.apache.http.conn.ConnectTimeoutException) {
                logger.error("Timeout error: " + e.getCause().getMessage());
            }
            Assert.fail("Test Case failed: " + e.getMessage());
        }
    }
}
