package api.payload;

public class ScanData {
	
	//for adding scan date
	/*
	 * { 
	 * "scanDate": "2025-02-25T12:04:12.327Z", 
	 * "regionId": "string", 
	 * "uploadedBy":"string" 
	 * }
	 */

	String scanDate;
	String regionId;
	String uploadedBy;
	
	
	//Generate Getters and Setters
	public String getScanDate() {
		return scanDate;
	}
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
	
	
}
