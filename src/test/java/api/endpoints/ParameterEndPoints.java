package api.endpoints;
import static io.restassured.RestAssured.*;

import api.routes.Routes_parameter;
import io.restassured.response.Response;


public class ParameterEndPoints {
	
	static String bearerToken;
	
	
 // Get Global Parameter Status By Company Id
    public static Response getGlobalParameterStatusByCompanyId(String CompanyId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("companyId", CompanyId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_parameter.get_GlobalParameterStatus_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Project Parameter Status By Project Id
    public static Response getProjectParameterStatusByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_parameter.get_ProjectParameterStatus_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Building Parameter Status By Building Id
    public static Response getBuildingParameterStatusByBuildingId(String BuildingId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("BuildingId", BuildingId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_parameter.get_BuildingParameterStatus_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Floor Parameter Status By Floor Id
    public static Response getFloorParameterStatusByFloorId(String FloorId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("FloorId", FloorId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_parameter.get_FloorParameterStatus_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Region Parameter Status By Region Id
    public static Response getRegionParameterStatusByRegionId(String RegionId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("RegionId", RegionId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_parameter.get_RegionParameterStatus_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get Parameter Master
    public static Response ParameterMaster(String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_parameter.parameterMaster_url); 
        return response;
    }
	
	

}
