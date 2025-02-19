package api.endpoints;

public class Routes_user {
	
	//Swagger url: http://10.1.0.83:8000/
	public static String base_url = "http://10.1.0.83:8000";		//to access everywhere in project
	
	//User Module URLs
	public static String login_url = base_url+"/api/User/login";
	public static String logout_url = base_url+"/api/User/logout";
	public static String getAllUser_url = base_url+"/api/User/GetAllUser";
	public static String getUserByEmail_url = base_url+"/api/User/GetUserByEmailId/{EmailId}";
	public static String forgotPassword_url = base_url+"/api/User/ForgotPassword/{EmailId}";
	public static String sendOtpToEmail_url = base_url+"/api/User/SendOTPToMail/{EmailId}";
	
	

}
