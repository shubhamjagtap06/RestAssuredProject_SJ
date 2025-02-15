package api.test;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.ProjectEndPoints;
import api.payload.Project;
import io.restassured.response.Response;


@SuppressWarnings("unused")
public class ProjectTests {
	
	public String projectIdFromResponse;
	
	Faker faker;
	Project proj_payload;
			
	
	@BeforeClass						//this should execute before methods in end points
	public void setupData()
		{
			faker = new Faker();			//prepare object
			proj_payload = new Project();	//prepare object	
			
			//proj_payload.setProjectId("Proj"+faker.number());
			// Store the generated project ID in a variable
		    String generatedProjectId = "Proj" + faker.number().numberBetween(100, 999);
		    proj_payload.setProjectId("Proj" + faker.number().numberBetween(100, 999));  // Use the generated project ID
			proj_payload.setCompanyId(faker.idNumber().toString());
			proj_payload.setProjectName(faker.name().fullName());
			proj_payload.setDescription(faker.name().lastName());
			proj_payload.setConstructionScheduleFrom("2025-01-01T12:03:55.621Z");
			proj_payload.setConstructionScheduleTo("2025-12-31T12:03:55.621Z");
			proj_payload.setTotalNoOfBuildings(5);
			proj_payload.setTotalBuildUpArea(faker.number().hashCode());
			proj_payload.setAreaUnit("sqm");
			proj_payload.setAddedBy("Shubham Jagtap");
			proj_payload.setUpdatedBy("Shubham Jagtap");
			proj_payload.setProgress(10);
			proj_payload.setActive(true);
			proj_payload.setDeleted(false);
			proj_payload.setArchived(true);
			proj_payload.setLastUpdated(0);					//now, proj_payload has a data
			
		}
	
	
	@Test(priority=1)
	public void test_CreateProject()
	{
		try 
		{
			// You can print the original projectId (although it may be overridden)
	        System.out.println("Project Id (Before creation, for reference): " + this.proj_payload.getProjectId());
			Response response = ProjectEndPoints.createProject(proj_payload);
			
			response.then().log().all();
			//String Proj_id = response.jsonPath().get("projectId").toString();
			//context.getSuite().setAttribute("projectId", Proj_id);	
			//System.out.println("Project Id: "+Proj_id);
			Assert.assertEquals(response.getStatusCode(), 200);
			//System.out.println(response.jsonPath().getInt("pid"));
			System.out.println("Project created successfully");
			
			// Retrieve projectId from response and assert
		    projectIdFromResponse = response.jsonPath().getString("projects.projectId");	//imp
		    System.out.println("Project Id (From response): " + projectIdFromResponse);
		    Assert.assertNotNull(projectIdFromResponse);  // Ensure the projectId exists
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			Assert.fail("Test Case failed" + e.getMessage());
			System.out.println("failed");
		}
		
	}
	
	
	//Get project by project id
	@Test(priority=2)
	public void test_GetProject()
	{
		try 
			{
				// Now use projectIdFromResponse from the previous test
		        System.out.println("Project Id from previous test: " +projectIdFromResponse);
		        
				//Response response = ProjectEndPoints.getProject("Proj258");
				//System.out.println("Project Id: "+ this.proj_payload.getProjectId());
				Response response = ProjectEndPoints.getProject(projectIdFromResponse);
				response.then().log().all();
				Assert.assertEquals(response.getStatusCode(), 200);
			}
		catch(Exception e) 
			{
				e.printStackTrace();
				Assert.fail("Test Case failed" + e.getMessage());
				System.out.println("failed");
			}
	}
	
	//Update project by project id
	@Test(priority=3)
	public void test_UpdateProject()
	{
		try 
			{
				proj_payload.setProjectName(faker.name().firstName());
				proj_payload.setDescription(faker.name().lastName());
				proj_payload.setTotalNoOfBuildings(15);
				proj_payload.setAreaUnit("sqft");
				proj_payload.setAddedBy("Shubham Jagtap Updated");
				proj_payload.setUpdatedBy("Shubham Jagtap Updated");
				proj_payload.setProgress(20);
				
				//Response response = ProjectEndPoints.updateProject(this.proj_payload.getProjectId(), proj_payload);
				Response response = ProjectEndPoints.updateProject(projectIdFromResponse, proj_payload);
				
				response.then().log().all();
			
				Assert.assertEquals(response.getStatusCode(), 200);
				//Checking data after update
				Response responseAfterUpdate = ProjectEndPoints.getProject(this.proj_payload.getProjectId());
				responseAfterUpdate.then().log().all();
				//Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
			
			}
		catch(Exception e) 
			{
				e.printStackTrace();
				Assert.fail("Test Case failed" + e.getMessage());
				System.out.println("failed");
			}
	}
	
	
	//Delete project by project id
		//@Test(priority=4)
		public void test_DeleteProject()
		{
			/*
			 * //String id = (String) context.getSuite().getAttribute("projectId");
			 * //Response response = ProjectEndPoints.getProject(id);
			 * response.then().log().all();
			 * 
			 * 
			 * Assert.assertEquals(response.getStatusCode(), 200);
			 */
			Response response = ProjectEndPoints.deleteProject("Proj247");
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200);
			System.out.println("Project deleted successfully");
			
			
		}

}
