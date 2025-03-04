package api.endpoints;
import static io.restassured.RestAssured.*;


import api.payload.Floor;
import api.routes.Routes_floor;
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
    
    
    
 // Get Floors by Building Id
    public static Response getFloorsByBuildingId(String BuildingId, String userAgent) {  // implementation of (get/read project) endpoint
       // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("buildingId", BuildingId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_floor.getFloorsByBuildingId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Update Floor by Floor Id
    public static Response updateFloor(String FloorId, Floor payload, String userAgent) {  
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
            .pathParam("floorId",FloorId)
            .header("User-Agent", userAgent)  // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_floor.updateFloorByFloorId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Floor by Floor Id
    public static Response getFloorByFloorId(String FloorId, String userAgent) {  
       // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("FloorId", FloorId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_floor.getFloorByFloorId_url);  
        return response;
    }
    
    
    
  //Get Archived Floors
  	public static Response getArchivedFloors(String userAgent) {  
  	       // Get the token for authorization
  	        bearerToken = api.test.User_Tests.getToken(userAgent);
  	        Response response = 
  	        given()
  	            .headers("Authorization", "Bearer " + bearerToken)
  	            .header("User-Agent", userAgent)  
  	        .when()
  	            .get(Routes_floor.getArchivedFloors_url);  
  	        return response;
  	    }
  	
  	
  	
  //Get All Floor History
  	public static Response getAllFloorsHistory(String userAgent) {  
  	       // Get the token for authorization
  	        bearerToken = api.test.User_Tests.getToken(userAgent);
  	        Response response = 
  	        given()
  	            .headers("Authorization", "Bearer " + bearerToken)
  	            .header("User-Agent", userAgent)  
  	        .when()
  	            .get(Routes_floor.getAllFloorHistory_url);  
  	        return response;
  	    }
  	
  	
  	
 // Get Bargraph by Floor Id
    public static Response getBargraphByFloorId(String FloorId, String userAgent) {  
       // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("FloorId", FloorId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_floor.getBargraphByFloorId_url);  
        return response;
    }
    
}
