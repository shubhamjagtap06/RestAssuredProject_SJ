package api.routes;

public class Routes_building {
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Building module URLs
	
	public static String getBuildings_url = base_url+"/api/Building";
	public static String addBuilding_url =base_url+"/api/Building";
	public static String getBuildingsByProjectId_url =base_url+"/api/Building/GetBuildingsByProjectId/{projectId}";
	public static String updateBuildingByBuildingId_url =base_url+"/api/Building/updateBuilding/{buildingId}";
	public static String toggleArchiveBuilding_url =base_url+"/api/Building/ToggleArchive/{buildingId}/{uid}";
	public static String getArchivedBuildingsByProjectId_url =base_url+"/api/Building/GetArchivedBuilding/{projectId}";
	public static String removeToggleArchiveBuilding_url =base_url+"/api/Building/RemoveToggleArchive/{buildingId}";
	
	public static String getBuildingByBuildingId_url =base_url+"/api/Building/GetBuildingByBuildingId/{BuildingId}";
	public static String getArchivedBuildings_url =base_url+"/api/Building/GetArchivedBuildings";
	public static String getBuildingHistory_url =base_url+"/api/Building/GetBuildingHistory/{BuildingId}";
	public static String getAllBuildingHistory_url =base_url+"/api/Building/GetAllBuildingHistory";
	public static String checkIsRevitFileUploaded_url =base_url+"/api/Building/CheckIsRevitFileUploaded/{BuildingId}";

}
