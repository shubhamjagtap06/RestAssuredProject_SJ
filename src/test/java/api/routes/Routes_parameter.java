package api.routes;



//Swagger url: http://10.1.0.83:8000/

//ONLY URLs
public class Routes_parameter {
	
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project

	//Parameter module URLs
	
	public static String get_GlobalParameterStatus_url = base_url +"/api/Parameter/GetGlobalParameterStatus/{companyId}";
	public static String get_ProjectParameterStatus_url = base_url +"/api/Parameter/GetProjectParameterStatus/{projectId}";
	public static String get_BuildingParameterStatus_url = base_url +"/api/Parameter/GetBuildingParameterStatus/{BuildingId}";
	public static String get_FloorParameterStatus_url = base_url +"/api/Parameter/GetFloorParameterStatus/{FloorId}";
	public static String get_RegionParameterStatus_url = base_url +"/api/Parameter/GetRegionParameterStatus/{RegionId}";


}
