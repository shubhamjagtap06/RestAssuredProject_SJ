package api.routes;

public class Routes_scandata {
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project

	//Scan Data module URLs
	
	public static String getScanData_url = base_url+"/api/ScanData/GetScanData";
	public static String uploadScanDate_url = base_url+"/api/ScanData/UploadScanDate";
	
	public static String getScanDataByRegionId_url = base_url+"/api/ScanData/GetScanDataByRegionId/{RegionId}";
	public static String getProjectsProgress_url = base_url+"/api/ScanData/GetProjectsProgress/{ProjectId}";
	public static String getBuildingProgress_url = base_url+"/api/ScanData/GetBuildingProgress/{BuildingId}";
	public static String getFloorProgress_url = base_url+"/api/ScanData/GetFloorProgress/{FloorId}";
	public static String getRegionsProgress_url = base_url+"/api/ScanData/GetRegionsProgress/{RegionId}";
	public static String getDiskSpaceByCompanyId_url = base_url+"/api/ScanData/GetDiskspaceByCompanyId/{CompanyId}";
	public static String getAllCustomersDiskSpace_url = base_url+"/api/ScanData/GetAllCustomersDiskSpace";
	public static String getWaitingProcessesList_url = base_url+"/api/ScanData/GetWaitingProcessesList";
	public static String getStackItem_url = base_url+"/api/ScanData/GetStackItem";
	public static String checkJsonFiles_url = base_url+"/api/Synchronizer/CheckJsonFiles";
	
}
