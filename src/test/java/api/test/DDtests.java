package api.test;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.ProjectEndPoints;
import api.payload.Project;
import api.utilities.DataProviders;
import io.restassured.response.Response;
public class DDtests {
	
	Faker faker;
	@Test(priority=1, dataProvider= "getAllData", dataProviderClass = DataProviders.class)
	public void testCreateProject(String companyId, String projectName, String description, String constructionScheduleFrom, String constructionScheduleTo, int totalNoOfBuildings, int totalBuildUpArea, String areaUnit, String addedBy, String updatedBy, int progress, boolean isActive, boolean isDeleted, boolean isArchived)
	{
		faker = new Faker();
		Project projPayload = new Project();
		
		//String generatedProjectId = "Proj" + faker.number().numberBetween(100, 999);
		projPayload.setProjectId("Proj" + faker.number().numberBetween(100, 999));  // Use the generated project ID
		projPayload.setCompanyId(companyId);
		projPayload.setProjectName(faker.name().fullName());
		projPayload.setDescription(description);
		projPayload.setConstructionScheduleFrom(constructionScheduleFrom);
		projPayload.setConstructionScheduleTo(constructionScheduleTo);
		projPayload.setTotalNoOfBuildings(totalNoOfBuildings);
		projPayload.setTotalBuildUpArea(totalBuildUpArea);
		projPayload.setAreaUnit(areaUnit);
		projPayload.setAddedBy(addedBy);
		projPayload.setUpdatedBy(updatedBy);
		projPayload.setProgress(progress);
		projPayload.setActive(true);
		projPayload.setDeleted(false);
		projPayload.setArchived(false);
		projPayload.setLastUpdated(0);					//now, projpayload has a data
		
		
		
		Response response = ProjectEndPoints.createProject(projPayload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("Project created successfully");
		
		
	}

}
