package api.endpoints;
import static io.restassured.RestAssured.*;

import api.payload.ScanData;
import api.routes.Routes_scandata;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ScanDataEndPoints {
	
	static String bearerToken;
	
	
	//Get all Scan Data's
		public static Response getScanData(String userAgent) {  // implementation of (get/read project) endpoint
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
}
