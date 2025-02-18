package api.endpoints;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {

    static String bearerToken;

    // User login
    public static Response userLogin(String userAgent) {  // Accepting userAgent as parameter
        String requestBody = "{\n" +
                "  \"email\": \"" + "shubham.jagtap@neilsoft.com" + "\",\n" +  	// Make sure the value is within double quotes
                "  \"password\": \"" + "Neilsoft@11" + "\"\n" +  				// Same here, wrap the password value in quotes
                "}";
        
        Response response =
                given()
                    .contentType(ContentType.JSON)
                    .header("User-Agent", userAgent)  // Adding User-Agent header
                    .body(requestBody)
                .when()
                    .post(Routes_user.login_url);
        return response;
    }
    
    
 // User login
    public static Response userLogin1() {  // Accepting userAgent as parameter
        String requestBody = "{\n" +
                "  \"email\": \"" + "shubham.jagtap@neilsoft.com" + "\",\n" +  	// Make sure the value is within double quotes
                "  \"password\": \"" + "Neilsoft@11" + "\"\n" +  				// Same here, wrap the password value in quotes
                "}";
        
        Response response =
                given()
                    .contentType(ContentType.JSON)
                    //.header("User-Agent", userAgent)  // Adding User-Agent header
                    .body(requestBody)
                .when()
                    .post(Routes_user.login_url);
        return response;
    }

    // Get All Users
    public static Response getAllUser(String userAgent) {  // Accepting userAgent as parameter
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response =
                given()
                    .headers("Authorization", "Bearer " + bearerToken)
                    .header("User-Agent", userAgent)  // Adding User-Agent header
                .when()
                    .get(Routes_user.getAllUser_url);
        return response;
    }

    // Get User by emailId
    public static Response getUserByEmailId(String emailId, String userAgent) {  // Accepting userAgent as parameter
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response =
                given()
                    .headers("Authorization", "Bearer " + bearerToken)
                    .header("User-Agent", userAgent)  // Adding User-Agent header
                    .pathParam("EmailId", emailId)
                .when()
                    .get(Routes_user.getUserByEmail_url);
        return response;
    }

    // User logout
    public static Response userLogout(String userinfo, String userAgent) {  // Accepting userAgent as parameter
        bearerToken = api.test.User_Tests.getToken(userAgent);
        
        Response response =
                given()
                    .headers("Authorization", "Bearer " + bearerToken)
                    .header("User-Agent", userAgent)  // Adding User-Agent header
                    .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
                    .accept(ContentType.JSON)
                    .body(userinfo)
                .when()
                    .post(Routes_user.logout_url);
        return response;
    }
}
