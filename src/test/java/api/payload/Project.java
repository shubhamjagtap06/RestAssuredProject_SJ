package api.payload;

//POJO class for Project
public class Project {
	
	/*
	 * { 
		 * "projectId": "string", 
		 * "companyId": "string", 
		 * "projectName": "string",
		 * "description": "string", 
		 * "constructionScheduleFrom":"2025-02-13T08:55:37.498Z", 
		 * "constructionScheduleTo":"2025-02-13T08:55:37.498Z", 
		 * "totalNoOfBuildings": 0, 
		 * "totalBuildUpArea": 0,
		 * "areaUnit": "string", 
		 * "addedBy": "string", 
		 * "updatedBy": "string", 
		 * "progress":0, 
		 * "isActive": true, 
		 * "isDeleted": true, 
		 * "isArchived": true, 
		 * "lastUpdated": 0
	 * }
	 */
	
	String projectId;
	String companyId;
	String projectName;
	String description;
	String constructionScheduleFrom;
	String constructionScheduleTo;
	int totalNoOfBuildings;
	int totalBuildUpArea;
	String areaUnit;
	String addedBy;
	String updatedBy;
	int progress;
	boolean isActive;
	boolean isDeleted;
	boolean isArchived;
	int lastUpdated;
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConstructionScheduleFrom() {
		return constructionScheduleFrom;
	}
	public void setConstructionScheduleFrom(String constructionScheduleFrom) {
		this.constructionScheduleFrom = constructionScheduleFrom;
	}
	public String getConstructionScheduleTo() {
		return constructionScheduleTo;
	}
	public void setConstructionScheduleTo(String constructionScheduleTo) {
		this.constructionScheduleTo = constructionScheduleTo;
	}
	public int getTotalNoOfBuildings() {
		return totalNoOfBuildings;
	}
	public void setTotalNoOfBuildings(int totalNoOfBuildings) {
		this.totalNoOfBuildings = totalNoOfBuildings;
	}
	public int getTotalBuildUpArea() {
		return totalBuildUpArea;
	}
	public void setTotalBuildUpArea(int totalBuildUpArea) {
		this.totalBuildUpArea = totalBuildUpArea;
	}
	public String getAreaUnit() {
		return areaUnit;
	}
	public void setAreaUnit(String areaUnit) {
		this.areaUnit = areaUnit;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public boolean isArchived() {
		return isArchived;
	}
	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}
	public int getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(int lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	

}
