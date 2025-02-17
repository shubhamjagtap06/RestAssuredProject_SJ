package api.endpoints;



//Swagger url: http://10.1.0.83:8000/

//Project
//Create Project (POST): http://10.1.0.83:8000/api/Project
//Get Project (GET): http://10.1.0.83:8000/api/Project/GetProjectByProjectId/{projectId}
//Update Project (POST): http://10.1.0.83:8000/api/Project/UpdateProject/{projectId}
//Delete Project (POST) : http://10.1.0.83:8000/api/Project/DeleteProject?ProjectId={projectId}&deletedBy="Shubham Jagtap"

//Get project by company id (GET): http://10.1.0.83:8000/api/Project/GetProject/{companyId}		//C0001
//Get Active Project by company id (GET): http://10.1.0.83:8000/api/Project/GetActiveProject/{companyId}		//C0001
//Get Archive Project by company id (GET): http://10.1.0.83:8000/api/Project/GetArchivedProject/{companyId}		//C0001
//Get all project history (GET): http://10.1.0.83:8000/api/Project/GetAllProjectHistory


//ONLY URLs
public class Routes {
	
	
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//Project module URLs
	
	//public static String post_url = "http://10.1.0.83:8000/api/Project";	
	public static String post_url = base_url+"/api/Project";
	public static String get_url = base_url+"/api/Project/GetProjectByProjectId/{projectId}";
	public static String update_url = base_url+"/api/Project/UpdateProject/{projectId}";
	public static String delete_url = base_url+"/api/Project/DeleteProject?ProjectId={projectId}&deletedBy=\"Shubham Jagtap\"";
	public static String get_activeProject_url = base_url+"/api/Project/GetActiveProject/{CompanyId}";
	public static String get_archivedProject_url =	base_url+"/api/Project/GetArchivedProject/{CompanyId}";
	//public static String post_url = base_url+"/api/Project";
	//public static String post_url = base_url+"/api/Project";
	//public static String post_url = base_url+"/api/Project";
	//public static String post_url = base_url+"/api/Project";
	
	
	
	//Here, you will create another module URLs
	
	
	
	
	
	
}
