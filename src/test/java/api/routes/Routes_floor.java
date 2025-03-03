package api.routes;

public class Routes_floor {
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Floor module URLs
	
	public static String getFloors_url = base_url+"/api/Floor";
	public static String addFloor_url = base_url+"/api/Floor";
	public static String getFloorsByBuildingId_url =base_url+"/api/Floor/GetFloorsByBuildingId/{buildingId}";
	public static String updateFloorByFloorId_url =base_url+"/api/Floor/updateFloors/{floorId}";

}
