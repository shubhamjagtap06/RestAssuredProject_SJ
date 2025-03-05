package api.routes;
//Swagger url: http://10.1.0.83:8000/

//ONLY URLs
//License, Load Balancer, Offset

public class Routes_license {
	
	public static String base_url = "http://10.1.0.83:8000";		

	
	//License module URLs
	public static String get_UserLicense_url = base_url +"/api/License/GetUserLicense/{Email}";
	public static String checkLogoForReport_url = base_url +"/api/License/CheckLogoForReport/{CompanyId}";
	public static String getLogoForReport_url = base_url +"/api/License/GetLogoForReport/{CompanyId}";
	
	
	//Load Balancer module URLs
	public static String LoadBalancer_url = base_url +"/api/LoadBalancer/getJobProcessor";
	
	
	//Offset module URLs
	public static String get_Offset_url = base_url +"/api/Offset";





}
