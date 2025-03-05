package api.endpoints;
import static io.restassured.RestAssured.*;

import api.routes.Routes_license;
import io.restassured.response.Response;
public class LicenseEndPoints {

	static String bearerToken;
	
	
	// Get User License by Email
    public static Response getUserLicenseByEmail(String EmailId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("Email", EmailId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_license.get_UserLicense_url);  
        return response;
    }
    
    
    
    // Check Logo for report by company id
    public static Response checkLogoForReportByCompanyId(String CompanyId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_license.checkLogoForReport_url);  
        return response;
    }
    
    
    
    // Get Logo for report by company id
    public static Response getLogoForReportByCompanyId(String CompanyId, String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .pathParam("CompanyId", CompanyId)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_license.getLogoForReport_url);  
        return response;
    }
    
    
    
    // Load Balancer => Get Job Processor
    public static Response getJobProcessor(String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_license.LoadBalancer_url);  
        return response;
    }
    
    
    
    // Get Offset
    public static Response getOffset(String userAgent) {  
    	bearerToken = api.test.User_Tests.getToken(userAgent);
        Response response = 
        given()
            .headers("Authorization", "Bearer " + bearerToken)
            .header("User-Agent", userAgent)  
        .when()
            .get(Routes_license.get_Offset_url);  
        return response;
    }
}
