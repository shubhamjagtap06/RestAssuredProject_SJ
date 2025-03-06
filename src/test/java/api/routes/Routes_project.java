package api.routes;



//Swagger url: http://10.1.0.83:8000/

//ONLY URLs
public class Routes_project {
	
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Project module URLs

	public static String post_url = base_url+"/api/Project";
	public static String get_url = base_url+"/api/Project/GetProjectByProjectId/{projectId}";
	public static String update_url = base_url+"/api/Project/UpdateProject/{projectId}";
	public static String delete_url = base_url+"/api/Project/DeleteProject?ProjectId={projectId}&deletedBy=\"Shubham Jagtap\"";
	public static String get_activeProject_url = base_url+"/api/Project/GetActiveProject/{CompanyId}/{UserId}";
	public static String get_archivedProject_url =	base_url+"/api/Project/GetArchivedProject/{CompanyId}/{UserId}";
	public static String get_ProjectDetailsByProjId_url = base_url+"/api/Project/GetProjectDetails/{projectId}";
	public static String get_ProjectTimelineByProjId_url = base_url+"/api/Project/GetTimelineByProjectId/{projectId}";
	public static String get_AreaByProjId_url = base_url+"/api/Project/GetAreaByProjectId/{projectId}";
	public static String get_CompanyDetailsByProjId_url = base_url+"/api/Project/GetCompanyDetailsByProjectId/{projectId}";
	
	public static String get_Projects_url = base_url+"/api/Project/GetProject/{CompanyId}/{UserId}";
	public static String get_CompanyWiseProject_url = base_url+"/api/Project/GetCompanyWiseProject/{CompanyId}";
	public static String get_ProjectByProjectId_url = base_url+"/api/Project/GetProjectByProjectId/{ProjectId}";
	public static String get_AccDetailsByProjectId_url = base_url+"/api/Project/GetAccDetails/{ProjectId}";
	
	public static String get_SiteAddressByProjectId_url = base_url+"/api/Project/GetSiteAddressByProjectId/{ProjectId}";
	public static String get_FinanceByProjectId_url = base_url+"/api/Project/GetFinanceByProjectId/{ProjectId}";
	public static String get_ProjectArchivedDetailsByProjectId_url = base_url+"/api/Project/GetProjectArchivedDetails/{ProjectId}";
	public static String get_ProjectCompanyByProjectId_url = base_url+"/api/Project/GetProjectCompany/{ProjectId}";
	public static String get_ProjectHistoryByProjectId_url = base_url+"/api/Project/GetProjectHistory/{ProjectId}";
	public static String get_AllProjectHistory_url = base_url+"/api/Project/GetAllProjectHistory";
	public static String get_HeatMapValuesByProjectId_url = base_url+"/api/Project/GetHeatMapValues/{ProjectId}";
	public static String get_LineGraphValuesByProjectIdBuildingId_url = base_url+"/api/Project/GetLineGraphValues/{ProjectId}/{BuildingId}";
	public static String get_LineGraphValuesAllBuildingsByProjectId_url = base_url+"/api/Project/GetLineGraphValuesAllBuildings/{ProjectId}";
	public static String get_SingleLineGraphValuesByProjectId_url = base_url+"/api/Project/GetSingleLineGraphValues/{ProjectId}";
	public static String get_ProjectLineGraphValuesByProjectIdBuildingId_url = base_url+"/api/Project/GetProjectLineGraphValuesBuilding/{ProjectId}/{BuildingId}";
	public static String addProjectDataToArchiveServerByProjectId_url = base_url+"/api/Project/AddProjectDataToArchiveServer/{ProjectId}";
	public static String addProjectDataToFileServerByProjectId_url = base_url+"/api/Project/AddProjectDataToFileServer/{ProjectId}";
}
