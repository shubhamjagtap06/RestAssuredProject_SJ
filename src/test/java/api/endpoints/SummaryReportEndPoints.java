package api.endpoints;
import static io.restassured.RestAssured.*;
import api.routes.Routes_summaryReport;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SuppressWarnings("unused")
public class SummaryReportEndPoints {
	static String bearerToken;
	
	
	
		//Get Project Parameter Progress by project id
				public static Response getProjectParameterProgress(String ProjectId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("ProjectId", ProjectId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getProjectParameterProgress_url);  
				        return response;
				    }
		
		
		
		//Get Region Parameter Progress
				public static Response getRegionParameterProgress(String ProjectId, String FloorId, String RegionId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("ProjectId", ProjectId)
				            .pathParam("FloorId", FloorId)
				            .pathParam("RegionId", RegionId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getRegionParameterProgress_url);  
				        return response;
				    }
				
				
				
		//Get Building Parameter Progress 
				public static Response getBuildingParameterProgress(String ProjectId, String BuildingId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("ProjectId", ProjectId)
				            .pathParam("BuildingId", BuildingId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getBuildingParameterProgress_url);  
				        return response;
				    }
				
				
				
		//Get Floor Parameter Progress 
				public static Response getFloorParameterProgress(String ProjectId, String BuildingId, String FloorId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("ProjectId", ProjectId)
				            .pathParam("BuildingId", BuildingId)
				            .pathParam("FloorId", FloorId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getFloorParameterProgress_url);  
				        return response;
				    }
				
				
				
		//Get Add Progress To Floor Parameter
				public static Response getAddProgressToFloorParameter(String FloorId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("FloorId", FloorId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getAddProgressToFloorParameter_url);  
				        return response;
				    }
				
				
				
		//Get Add Progress To Building Parameter
				public static Response getAddProgressToBuildingParameter(String BuildingId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("BuildingId", BuildingId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getAddProgressToBuildingParameter_url);  
				        return response;
				    }
				
				
				
		//Get Add Progress To Project Parameter
				public static Response getAddProgressToProjectParameter(String ProjectId, String userAgent) {  
				        bearerToken = api.test.User_Tests.getToken(userAgent);
				        Response response = 
				        given()
				            .headers("Authorization", "Bearer " + bearerToken)
				            .pathParam("ProjectId", ProjectId)
				            .header("User-Agent", userAgent) 
				        .when()
				            .get(Routes_summaryReport.getAddProgressToProjectParameter_url);  
				        return response;
				    }

}
