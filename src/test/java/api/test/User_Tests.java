package api.test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.ProjectEndPoints;
import api.endpoints.UserEndPoints;
import api.payload.Project;
import io.restassured.response.Response;



@SuppressWarnings("unused")
public class User_Tests {
	
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
    
    @SuppressWarnings("static-access")
	@BeforeClass
    public void setup() throws IOException {
        // Load properties from the config.properties file
        Properties properties = new Properties();

        // Specify the location of the config.properties file
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");

        // Load the properties from the file
        properties.load(fis);

        // Retrieve the properties
        this.email = properties.getProperty("email");
        this.password = properties.getProperty("password");
    }
    
    
    
    public static String getToken() {
        // Call the loginUser method to get the response
        Response response = UserEndPoints.userLogin();

        // Log the response for debugging
        response.then().log().all();

        // Assert that the login request was successful (HTTP status 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());

        // Optionally, you can add checks for login-specific response data (e.g., token or user info)
        // For example:
        String authToken = response.jsonPath().getString("jwtToken"); // Assuming a token is returned on successful login
        Assert.assertNotNull(authToken, "Auth token should not be null");
        System.out.println("JWT Token :"+authToken);
        return authToken;
    }
    
    
    @Test(priority=1)
    public static void testLogin() {
        // Call the loginUser method to get the response
        Response response = UserEndPoints.userLogin();

        // Log the response for debugging
        response.then().log().all();

        // Assert that the login request was successful (HTTP status 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());

        // Optionally, you can add checks for login-specific response data (e.g., token or user info)
        // For example:
        String authToken = response.jsonPath().getString("jwtToken"); // Assuming a token is returned on successful login
        Assert.assertNotNull(authToken, "Auth token should not be null");
        System.out.println("JWT Token :"+authToken);
        
        //Extract the user information from the response
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

        // Output the details for debugging
        System.out.println("User ID: " + userId);
        System.out.println("Company ID: " + companyId);
        
    }
    
    
    
    
    //test_GetAllUser()
    @Test(priority=2)
	public void test_GetAllUser()
	{
		try 
		{
	        
			Response response = UserEndPoints.getAllUser();
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			Assert.fail("Test Case failed" + e.getMessage());
			System.out.println("test case failed");
		}
	}
    
    
    
    
    //test_GetUserByEmailId()
    @Test(priority=3)
	public void test_GetUserByEmailId()
	{
		try 
		{
	        
			Response response = UserEndPoints.getUserByEmailId("shubham.jagtap@neilsoft.com");
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			Assert.fail("Test Case failed" + e.getMessage());
			System.out.println("test case failed");
		}
	}
    
    
    
    
  //test_GetUserByEmailId()
    @Test(priority=4)
	public void testLogout()
	{
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
                "  \"recStartDate\": \"2025-02-15T07:37:17.339Z\",\n" + // Use appropriate date format
                "  \"recEndDate\": \"2025-02-15T07:37:17.339Z\",\n" +
                "  \"isActive\": " + isActive + "\n" +
                "}";
		try 
		{
	        
			Response response = UserEndPoints.userLogout(logoutRequestBody);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			Assert.fail("Test Case failed" + e.getMessage());
			System.out.println("test case failed");
		}
	}


}
