package api.endpoints;
import static io.restassured.RestAssured.*;

import api.payload.ScanData;
import api.routes.Routes_scandata;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ScanDataEndPoints {
	
	static String bearerToken;
	
	
	//Get all Scan Data's
		public static Response getScanData(String userAgent) {  
		       // Get the token for authorization
		        bearerToken = api.test.User_Tests.getToken(userAgent);
		        Response response = 
		        given()
		            .headers("Authorization", "Bearer " + bearerToken)
		            .header("User-Agent", userAgent)  // Add User-Agent here
		        .when()
		            .get(Routes_scandata.getScanData_url);  // Refer URL from Routes class
		        return response;
		    }

		
		
		// Add Scan Date
	    public static Response addScanDate(ScanData payload, String userAgent) { 
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
	            .post(Routes_scandata.uploadScanDate_url);  // Refer URL from Routes class
	        return response;
	    }
	    
	    
	    
	  //Get Scan Data by Region Id
	  		public static Response getScanDataByRegionId(String RegionId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("RegionId", RegionId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getScanDataByRegionId_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get Project progress by project id
	  		public static Response getProjectProgressByProjectId(String ProjectId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("ProjectId", ProjectId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getProjectsProgress_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get Building progress by Building id
	  		public static Response getBuildingProgressByBuildingId(String BuildingId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("BuildingId", BuildingId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getBuildingProgress_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get Floor progress by Floor id
	  		public static Response getFloorProgressByFloorId(String FloorId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("FloorId", FloorId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getFloorProgress_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get Region progress by Region id
	  		public static Response getRegionProgressByRegionId(String RegionId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("RegionId", RegionId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getRegionsProgress_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get DiskSpace by Company id
	  		public static Response getDiskSpaceByCompanyId(String CompanyId, String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .pathParam("CompanyId", CompanyId)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getDiskSpaceByCompanyId_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get All Customers DiskSpace 
	  		public static Response getAllCustomersDiskSpace(String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getAllCustomersDiskSpace_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get Waiting Processes List 
	  		public static Response getWaitingProcessesList(String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getWaitingProcessesList_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Get stack item
	  		public static Response getStackItem(String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.getStackItem_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
	  		
	  		
	  		
	  	//Check Json Files
	  		public static Response checkJsonFiles(String userAgent) {  
	  		       // Get the token for authorization
	  		        bearerToken = api.test.User_Tests.getToken(userAgent);
	  		        Response response = 
	  		        given()
	  		            .headers("Authorization", "Bearer " + bearerToken)
	  		            .header("User-Agent", userAgent)  // Add User-Agent here
	  		        .when()
	  		            .get(Routes_scandata.checkJsonFiles_url);  // Refer URL from Routes class
	  		        return response;
	  		    }
}
