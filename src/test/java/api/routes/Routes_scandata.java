package api.routes;

public class Routes_scandata {
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project

	//Scan Data module URLs
	
	public static String getScanData_url = base_url+"/api/ScanData/GetScanData";
	public static String uploadScanDate_url = base_url+"/api/ScanData/UploadScanDate";
}
