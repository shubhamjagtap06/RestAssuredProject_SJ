package api.endpoints;
import static io.restassured.RestAssured.*;
import api.payload.Project;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


//ModuleEndPoints.java file
//here, ProjectEndPoints.java file
//Created to perform CRUD requests to Project API
//All methods/requests included here

@SuppressWarnings("unused")
public class ProjectEndPoints {
	
	static String bearerToken;
	
	//Create project
		public static Response createProject(Project payload)		//implementation of (create project) endpoint
		{
			//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";
			bearerToken = api.test.User_Tests.getToken();
			Response response = 
			given()
				.headers("Authorization","Bearer "+bearerToken)
				.contentType(ContentType.JSON)		//from postman or swagger (part of request)
				.accept(ContentType.JSON)
				.body(payload)
			.when()
				.post(Routes.post_url);				//refer URL from Routes class
				
			return response;
		}
	
	
	
	//Get Project
		public static Response getProject(String ProjectId)		//implementation of (get/read project) endpoint
		{
			//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";

			Response response = 
			given()
				.headers("Authorization","Bearer "+bearerToken)
				.pathParam("projectId", ProjectId)
			.when()
				.get(Routes.get_url);							//refer URL from Routes class
			return response;
		}
	
	
	
	//Update project
		public static Response updateProject(String ProjectId, Project payload)	//implementation of (update project) endpoint
		{
			//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";

			Response response = 
			given()
				.headers("Authorization","Bearer "+bearerToken)
				.contentType(ContentType.JSON)			//from postman or swagger (part of request)
				.accept(ContentType.JSON)
				.pathParam("projectId", ProjectId)
				.body(payload)
			.when()
				.post(Routes.update_url);				//refer URL from Routes class
			return response;
		}
		
		
		
	//Delete project
		public static Response deleteProject(String ProjectId)		//implementation of (delete project) endpoint
		{
			//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";

			Response response = 
			given()
			.headers("Authorization","Bearer "+bearerToken)
			.pathParam("projectId", ProjectId)
			//.pathParam("deletedBy", deletedBy)
			.when()
				.post(Routes.delete_url);							//refer URL from Routes class
			return response;
		}
		
		
		
		
		//Get Project
				public static Response getActiveProject(String CompanyId)		//implementation of (get/read project) endpoint
				{
					//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";

					Response response = 
					given()
						.headers("Authorization","Bearer "+bearerToken)
						.pathParam("CompanyId", CompanyId)
					.when()
						.get(Routes.get_activeProject_url);							//refer URL from Routes class
					return response;
				}
				
				
				
				
				
		//Get Project
				public static Response getArchivedProject(String CompanyId)		//implementation of (get/read project) endpoint
				{
					//String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3VzZXJkYXRhIjoiU2h1YmhhbSBKYWd0YXAiLCJuYW1lIjoiIiwibmJmIjoxNzM5NTA5Mzg5LCJleHAiOjE3Mzk1OTU3ODksImlhdCI6MTczOTUwOTM4OX0.z7ZWFneFR1Z-N4hrN4ruiNcN3ZHvZ8rq3axLI99QGLU";

					Response response = 
					given()
						.headers("Authorization","Bearer "+bearerToken)
						.pathParam("CompanyId", CompanyId)
					.when()
						.get(Routes.get_archivedProject_url);							//refer URL from Routes class
					return response;
				}		
		

}
