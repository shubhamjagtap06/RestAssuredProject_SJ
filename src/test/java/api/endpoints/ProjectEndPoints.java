package api.endpoints;

import static io.restassured.RestAssured.*;
import api.payload.Project;
import api.routes.Routes_project;
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
    
    
// Create project for DDT
    public static Response createProject1(Project payload) {  // implementation of (create project) endpoint //Accept userAgent as a parameter
        // Get the token for authorization
        bearerToken = api.test.User_Tests.getToken1();
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
    	bearerToken = api.test.User_Tests.getToken(userAgent);
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
    	bearerToken = api.test.User_Tests.getToken(userAgent);
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
    	bearerToken = api.test.User_Tests.getToken(userAgent);
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
    	bearerToken = api.test.User_Tests.getToken(userAgent);
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
    	bearerToken = api.test.User_Tests.getToken(userAgent);
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
    
    
 // Get Project details by project Id
    public static Response getProjectDetails(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_ProjectDetailsByProjId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
    
 // Get Timeline by project Id
    public static Response getTimeLineByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_ProjectTimelineByProjId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
    
  // Get Area by project Id
    public static Response getAreaByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_AreaByProjId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
    
  // Get company details by project Id
    public static Response getCompanyDetailsByProjectId(String ProjectId, String userAgent) {  // implementation of (get/read project) endpoint
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("projectId", ProjectId)
            .header("User-Agent", userAgent)  // Add User-Agent here
        .when()
            .get(Routes_project.get_CompanyDetailsByProjId_url);  // Refer URL from Routes class
        return response;
    }
    
    
    
 // Get projects by company id and user id
    public static Response getProjectByCompanyIdUserId(String CompanyId, String UserId, String userAgent) {  // implementation of (get/read project) endpoint
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .pathParam("UserId", UserId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_Projects_url); 
        return response;
    }
    
    
    
 // Get company wise project by company id
    public static Response getCompanyWiseProjectByCompanyId(String CompanyId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_CompanyWiseProject_url);  
        return response;
    }
    
    
    
 // Get project by project Id
    public static Response getProjectByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_ProjectByProjectId_url);  
        return response;
    }
    
    
    
 // Get Acc Details by project Id
    public static Response getAccDetailsByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_AccDetailsByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Site Address by project Id
    public static Response getSiteAddressByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_SiteAddressByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Finance by project Id
    public static Response getFinanceByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_FinanceByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Project Archived Details by project Id
    public static Response getProjectArchivedDetailsByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_ProjectArchivedDetailsByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Project Company by project Id
    public static Response getProjectCompanyByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_ProjectCompanyByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Project History by project Id
    public static Response getProjectHistoryByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_ProjectHistoryByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get All Project History 
    public static Response getAllProjectHistory(String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_AllProjectHistory_url);  
        return response;
    }
    
    
    
    
 // Get HeattMap values by project Id
    public static Response getHeatMapValuesByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_HeatMapValuesByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get LineGraph values by project Id and building id
    public static Response getLineGraphValuesByProjectIdBuildingId(String ProjectId, String BuildingId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .pathParam("BuildingId", BuildingId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_LineGraphValuesByProjectIdBuildingId_url);  
        return response;
    }
    
    
    
    
 // Get LineGraph values all buildings by project Id 
    public static Response getLineGraphValuesAllBuildingsByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_LineGraphValuesAllBuildingsByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Single LineGraph values by project Id 
    public static Response getSingleLineGraphValueByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_SingleLineGraphValuesByProjectId_url);  
        return response;
    }
    
    
    
    
 // Get Project LineGraph values by project Id and building id
    public static Response getProjectLineGraphValuesByProjectIdBuildingId(String ProjectId, String BuildingId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .pathParam("BuildingId", BuildingId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.get_ProjectLineGraphValuesByProjectIdBuildingId_url);  
        return response;
    }
    
    
    
    
 // Add project data to archive server by project Id 
    public static Response addProjectDataToArchiveServerByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.addProjectDataToArchiveServerByProjectId_url);  
        return response;
    }
    
    
    
    
 // Add project data to file server by project Id 
    public static Response addProjectDataToFileServerByProjectId(String ProjectId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("ProjectId", ProjectId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_project.addProjectDataToFileServerByProjectId_url);  
        return response;
    }
}
