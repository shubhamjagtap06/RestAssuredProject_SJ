package api.endpoints;
import static io.restassured.RestAssured.*;
import api.payload.Building;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BuildingEndPoints {
	
	 static String bearerToken;
	 
	 
	// Get Buildings
	    public static Response getBuildings(String userAgent) {  // implementation of (get/read project) endpoint
	       
	    	// Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .get(Routes_building.getBuildings_url);  // Refer URL from Routes class
	        return response;
	    }

}
