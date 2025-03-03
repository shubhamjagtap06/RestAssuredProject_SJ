package api.endpoints;
import static io.restassured.RestAssured.*;

import api.payload.Region;
import api.routes.Routes_region;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RegionEndPoints {
	
	static String bearerToken;
	
	
	//Get all Regions
	public static Response getRegions(String userAgent) {  // implementation of (get/read project) endpoint
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .get(Routes_region.getRegions_url);  // Refer URL from Routes class
	        return response;
	    }
	
	
	// Add Floor
    public static Response addRegion(Region payload, String userAgent) { 
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
            .post(Routes_region.addRegion_url);  // Refer URL from Routes class
        return response;
    }

    
    
 // Get Regions by Floor Id
    public static Response getRegionsByFloorId(String FloorId, String userAgent) {  
       // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("floorId", FloorId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_region.getRegionsByFloorId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Update Region by Region Id
    public static Response updateRegion(String RegionId, Region payload, String userAgent) {  
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
            .pathParam("regionId",RegionId)
            .header("User-Agent", userAgent)  // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_region.updateRegionByRegionId_url);  // Refer URL from Routes class
        return response;
    }
}
