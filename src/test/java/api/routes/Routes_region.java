package api.routes;

public class Routes_region {
	
public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Region module URLs

	public static String getRegions_url = base_url+"/api/Region";
	public static String addRegion_url = base_url+"/api/Region";
	public static String getRegionsByFloorId_url =base_url+"/api/Region/GetRegionByFloorId/{floorId}";
	public static String updateRegionByRegionId_url =base_url+"/api/Region/updateRegions/{regionId}";

	
}
