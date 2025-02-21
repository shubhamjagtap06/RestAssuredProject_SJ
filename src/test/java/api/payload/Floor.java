package api.payload;

public class Floor {
	
	/*
	 * { 
	 * "floorId": "string", 
	 * "levelId": "string", 
	 * "buildingId":"f1defb0d-41ae-4ef4-83d3-24d48ad6ee27", 
	 * "projectId": "string", 
	 * "floorName":"string", 
	 * "totalBuildUpArea": 0, 
	 * "addedBy": "string", 
	 * "updatedBy": "string",
	 * "addedOn": "2025-02-21T10:09:07.520Z", 
	 * "areaUnit": "string", 
	 * "progress": 0,
	 * "recStartDate": "2025-02-21T10:09:07.520Z", 
	 * "recEndDate":"2025-02-21T10:09:07.520Z", 
	 * "isDeleted": true, 
	 * "isArchived": true 
	 * }
	 */
	
	String floorId;
	String levelId;
	String buildingId;
	String projectId;
	String floorName;
	int totalBuildUpArea;
	String addedBy;
	String updatedBy;
	String addedOn;
	String areaUnit;
	int progress;
	String recStartDate;
	String recEndDate;
	boolean isDeleted;
	boolean isArchived;
	
	//Generate Getters and Setters
	public String getFloorId() {
		return floorId;
	}
	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public int getTotalBuildUpArea() {
		return totalBuildUpArea;
	}
	public void setTotalBuildUpArea(int totalBuildUpArea) {
		this.totalBuildUpArea = totalBuildUpArea;
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
	public String getAreaUnit() {
		return areaUnit;
	}
	public void setAreaUnit(String areaUnit) {
		this.areaUnit = areaUnit;
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
