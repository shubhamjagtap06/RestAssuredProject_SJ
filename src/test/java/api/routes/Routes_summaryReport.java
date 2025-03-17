package api.routes;

public class Routes_summaryReport {
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project

	//Summary Report module URLs

	public static String getProjectParameterProgress_url = base_url+"/api/SummaryReport/GetProjectParameterProgress/{ProjectId}";
	public static String getRegionParameterProgress_url = base_url+"/api/SummaryReport/GetRegionParameterProgress/{ProjectId}/{FloorId}/{RegionId}";
	public static String getBuildingParameterProgress_url = base_url+"/api/SummaryReport/GetBuildingParameterProgress/{ProjectId}/{BuildingId}";
	public static String getFloorParameterProgress_url = base_url+"/api/SummaryReport/GetFloorParameterProgress/{ProjectId}/{BuildingId}/{FloorId}";
	public static String getAddProgressToFloorParameter_url = base_url+"/api/SummaryReport/AddProgressToFloorParameter/{FloorId}";
	public static String getAddProgressToBuildingParameter_url = base_url+"/api/SummaryReport/AddProgressToBuildingParameter/{BuildingId}";
	public static String getAddProgressToProjectParameter_url = base_url+"/api/SummaryReport/AddProgressToProjectParameter/{ProjectId}";
	
}
