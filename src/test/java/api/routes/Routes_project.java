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
	
	
	
	
	
}
