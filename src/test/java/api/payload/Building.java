package api.payload;

//POJO class for Building
public class Building {
	
	//post body
	/*
	 * { 
	 * "projectId": "string", 
	 * "buildingId": "string", 
	 * "buildingName": "string",
	 * "constructionScheduleFrom": "2025-02-20T06:33:23.014Z",
	 * "constructionScheduleTo": "2025-02-20T06:33:23.014Z", 
	 * "totalNoOfFloors": 0,
	 * "totalBuildUpArea": 0, 
	 * "areaUnit": "string", 
	 * "addedBy": "string",
	 * "updatedBy": "string", 
	 * "addedOn": "2025-02-20T06:33:23.014Z", 
	 * "progress": 0,
	 * "recStartDate": "2025-02-20T06:33:23.014Z", 
	 * "recEndDate":"2025-02-20T06:33:23.014Z", 
	 * "isDeleted": true, 
	 * "isArchived": true 
	 * }
	 */
	
	String projectId;
	String buildingId;
	String buildingName;
	String constructionScheduleFrom;
	String constructionScheduleTo;
	int totalNoOfFloors;
	int totalBuildUpArea;
	String areaUnit;
	String addedBy;
	String updatedBy;
	String addedOn;
	int progress;
	String recStartDate;
	String recEndDate;
	boolean isDeleted;
	boolean isArchived;
	
	//Generate Getters and Setters
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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
	public int getTotalNoOfFloors() {
		return totalNoOfFloors;
	}
	public void setTotalNoOfFloors(int totalNoOfFloors) {
		this.totalNoOfFloors = totalNoOfFloors;
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
	public String getAddedOn() {
		return addedOn;
	}
	public void setAddedOn(String addedOn) {
		this.addedOn = addedOn;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getRecStartDate() {
		return recStartDate;
	}
	public void setRecStartDate(String recStartDate) {
		this.recStartDate = recStartDate;
	}
	public String getRecEndDate() {
		return recEndDate;
	}
	public void setRecEndDate(String recEndDate) {
		this.recEndDate = recEndDate;
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
	
	
	

}
