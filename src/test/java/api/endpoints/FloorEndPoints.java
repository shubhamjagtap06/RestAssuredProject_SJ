package api.endpoints;
import static io.restassured.RestAssured.*;
import api.payload.Floor;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FloorEndPoints {
	
	static String bearerToken;
	
	
	//Get all Floors
	public static Response getFloors(String userAgent) {  // implementation of (get/read project) endpoint
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .get(Routes_floor.getFloors_url);  // Refer URL from Routes class
	        return response;
	    }
	
	
	
	// Add Floor
    public static Response addFloor(Floor payload, String userAgent) {  // implementation of (create project) endpoint //Accept userAgent as a parameter
        // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
            .header("User-Agent", userAgent)  // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_floor.addFloor_url);  // Refer URL from Routes class
        return response;
    }
}
