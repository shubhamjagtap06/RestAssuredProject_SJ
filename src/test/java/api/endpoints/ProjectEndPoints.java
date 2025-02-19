package api.endpoints;

import static io.restassured.RestAssured.*;
import api.payload.Project;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//ModuleEndPoints.java file
//here, ProjectEndPoints.java file
//Created to perform CRUD requests to Project API
//All methods/requests included here

public class ProjectEndPoints {
    
    static String bearerToken;
    
    // Create project
    public static Response createProject(Project payload, String userAgent) {  // implementation of (create project) endpoint //Accept userAgent as a parameter
        // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken(userAgent);
        
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
            .header("User-Agent", userAgent)  // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_project.post_url);  // Refer URL from Routes class
        
        return response;
    }
    
 // Create project
    public static Response createProject1(Project payload) {  // implementation of (create project) endpoint //Accept userAgent as a parameter
        // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken1();
        
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
              // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_project.post_url);  // Refer URL from Routes class
        
        return response;
    }

    // Get Project
    public static Response getProject(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_url);  // Refer URL from Routes class
        return response;
    }

    // Update project
    public static Response updateProject(String ProjectId, Project payload, String userAgent) {  // implementation of (update project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .contentType(ContentType.JSON)  // From Postman or Swagger (part of request)
            .accept(ContentType.JSON)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
            .body(payload)
        .when()
            .post(Routes_project.update_url);  // Refer URL from Routes class
        return response;
    }

    // Delete project
    public static Response deleteProject(String ProjectId, String userAgent) {  // implementation of (delete project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .post(Routes_project.delete_url);  // Refer URL from Routes class
        return response;
    }

    // Get Active Project
    public static Response getActiveProject(String CompanyId, String UserId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .pathParam("UserId", UserId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_activeProject_url);  // Refer URL from Routes class
        return response;
    }

    // Get Archived Project
    public static Response getArchivedProject(String CompanyId, String UserId, String userAgent) {  // implementation of (get/read project) endpoint
        // Set User-Agent for multi-browser testing
        //String userAgent = System.getProperty("userAgent", "Mozilla/5.0"); // Default to "Mozilla/5.0" if not provided
        
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .pathParam("UserId", UserId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_archivedProject_url);  // Refer URL from Routes class
        return response;
    }
}
