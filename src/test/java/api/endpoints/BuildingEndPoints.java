package api.endpoints;
import static io.restassured.RestAssured.*;
import api.payload.Building;
import api.routes.Routes_building;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BuildingEndPoints {
	
	 static String bearerToken;
	 
	 
	// Get all Buildings
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
	    
	    
	    
	  // Add Building
	    public static Response addBuilding(Building payload, String userAgent) {  // implementation of (create project) endpoint //Accept userAgent as a parameter
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
	            .post(Routes_building.addBuilding_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	 // Get Buildings
	    public static Response getBuildingsByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .pathParam("projectId", ProjectId)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .get(Routes_building.getBuildingsByProjectId_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	 // Update Building by Building Id
	    public static Response updateBuilding(String BuildingId, Building payload, String userAgent) {  
	        // Set User-Agent for multi-browser testing
	        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
	            .accept(ContentType.JSON)
	            .pathParam("buildingId",BuildingId)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	            .body(payload)
	        .when()
	            .post(Routes_building.updateBuildingByBuildingId_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	 // Toggle Archive Building
	    public static Response ToggleArchiveBuilding(String BuildingId, String userId, String userAgent) {   
	        // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
	            .accept(ContentType.JSON)
	            .pathParam("buildingId", BuildingId)
	            .pathParam("uid", userId)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .post(Routes_building.toggleArchiveBuilding_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	    //Get Archived Buildings by Project Id
	    public static Response getArchivedBuildingsByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .pathParam("projectId", ProjectId)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .get(Routes_building.getArchivedBuildingsByProjectId_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	 // Remove Toggle Archive Building
	    public static Response RemoveToggleArchiveBuilding(String BuildingId, String userAgent) {   
	        // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
	            .accept(ContentType.JSON)
	            .pathParam("buildingId", BuildingId)
	            .header("User-Agent", userAgent)  // Add User-Agent here
	        .when()
	            .post(Routes_building.removeToggleArchiveBuilding_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	    
	 // Get Building by Building Id
	    public static Response getBuildingByBuildingId(String BuildingId, String userAgent) {  
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .pathParam("BuildingId", BuildingId)
	            .header("User-Agent", userAgent)  
	        .when()
	            .get(Routes_building.getBuildingByBuildingId_url);  
	        return response;
	    }
	    
	    
	    
	    
	    //Get all Archived Buildings 
	    public static Response getArchivedBuildings(String userAgent) {  
		       // Get the token for authorization
		        bearerToken = api.test.User_Tests.getToken(userAgent);
		        Response response = 
		        given()
		            .headers("Authorization", "Bearer " + bearerToken)
		            .header("User-Agent", userAgent) 
		        .when()
		            .get(Routes_building.getArchivedBuildings_url);  
		        return response;
		    }
	    
	    
	    
	    
	 // Get Building History by Building Id
	    public static Response getBuildingHistoryByBuildingId(String BuildingId, String userAgent) {  
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .pathParam("BuildingId", BuildingId)
	            .header("User-Agent", userAgent)  
	        .when()
	            .get(Routes_building.getBuildingHistory_url);  
	        return response;
	    }
	    
	    
	    
	    
	 // Get all Buildings History 
	    public static Response getAllBuildingsHistory(String userAgent) {  
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .header("User-Agent", userAgent)  
	        .when()
	            .get(Routes_building.getAllBuildingHistory_url);  
	        return response;
	    }
	    
	    
	    
	    
	 // Check is revit file uploaded by building id
	    public static Response checkIsRevitFileUploadedByBuildingId(String BuildingId, String userAgent) {  
	       // Get the token for authorization
	        bearerToken = api.test.User_Tests.getToken(userAgent);
	        Response response = 
	        given()
	            .headers("Authorization", "Bearer " + bearerToken)
	            .pathParam("BuildingId", BuildingId)
	            .header("User-Agent", userAgent)  
	        .when()
	            .get(Routes_building.checkIsRevitFileUploaded_url);  
	        return response;
	    }
}
