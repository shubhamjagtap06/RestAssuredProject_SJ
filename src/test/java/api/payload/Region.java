package api.payload;

public class Region {
	
	/*
	 * { 
	 * "regionId": "string", 
	 * "floorId": "string", 
	 * "buildingId": "string",
	 * "projectId": "string", 
	 * "regionName": "string", 
	 * "comment": "string",
	 * "addedBy": "string", 
	 * "updatedBy": "string", 
	 * "addedOn": "2025-02-24T06:20:23.713Z", 
	 * "progress": 0, 
	 * "recentScanDate":"2025-02-24T06:20:23.713Z", 
	 * "recStartDate": "2025-02-24T06:20:23.713Z",
	 * "recEndDate": "2025-02-24T06:20:23.713Z", 
	 * "isDeleted": true 
	 * }
	 */
	
	String regionId;
	String floorId;
	String buildingId;
	String projectId;
	String regionName;
	String comment;
	String addedBy;
	String updatedBy;
	String addedOn;
	int progress;
	String recentScanDate;
	String recStartDate;
	String recEndDate;
	boolean isDeleted;
	
	//Generate Getters and Setters
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getFloorId() {
		return floorId;
	}
	public void setFloorId(String floorId) {
		this.floorId = floorId;
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
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	public String getRecentScanDate() {
		return recentScanDate;
	}
	public void setRecentScanDate(String recentScanDate) {
		this.recentScanDate = recentScanDate;
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

	
}
