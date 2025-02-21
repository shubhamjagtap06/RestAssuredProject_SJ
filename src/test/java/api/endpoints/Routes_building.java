package api.endpoints;

public class Routes_building {
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Building module URLs
	
	public static String getBuildings_url = base_url+"/api/Building";
	public static String addBuilding_url =base_url+"/api/Building";
	public static String getBuildingsByProjectId_url =base_url+"/api/Building/GetBuildingsByProjectId/{projectId}";
	public static String updateBuildingByBuildingId_url =base_url+"/api/Building/updateBuilding/{buildingId}";
	

}
