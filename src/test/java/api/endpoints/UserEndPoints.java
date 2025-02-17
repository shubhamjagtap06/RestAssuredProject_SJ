package api.endpoints;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


@SuppressWarnings("unused")
public class UserEndPoints {
	
	static String bearerToken;
	
	
	//user login
	public static Response userLogin()
		{
			String requestBody = "{\n" +
	                "  \"email\": \"" + "shubham.jagtap@neilsoft.com" + "\",\n" +  	// Make sure the value is within double quotes
	                "  \"password\": \"" + "Neilsoft@11" + "\"\n" +  				// Same here, wrap the password value in quotes
	                "}";
			Response response =
					given()
						.contentType(ContentType.JSON)
						.body(requestBody)
					.when()
						.post(Routes_user.login_url);
					return response;
		}
	
	
	//Get All User
	public static Response getAllUser()
	{
		bearerToken = api.test.User_Tests.getToken();
		Response response =
				given()
					.headers("Authorization","Bearer "+bearerToken)
				.when()
					.get(Routes_user.getAllUser_url);
				return response;
		
	}
	
	
	//Get User by emailid
	public static Response getUserByEmailId(String emailId)
	{
		bearerToken = api.test.User_Tests.getToken();
		Response response =
				given()
					.headers("Authorization","Bearer "+bearerToken)
					.pathParam("EmailId", emailId)
				.when()
					.get(Routes_user.getUserByEmail_url);
				return response;
		
	}
	
	
	
	//user logout
	public static Response userLogout(String userinfo)
	{
		bearerToken = api.test.User_Tests.getToken();
		
		Response response =
				given()
					.headers("Authorization","Bearer "+bearerToken)
					.contentType(ContentType.JSON)		//from postman or swagger (part of request)
					.accept(ContentType.JSON)
					.body(userinfo)
				.when()
					.post(Routes_user.logout_url);
				return response;
		
	}
	
	
}
