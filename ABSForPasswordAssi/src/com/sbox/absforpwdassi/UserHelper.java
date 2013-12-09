package com.sbox.absforpwdassi;


public class UserHelper 
{
	private static Boolean isExit = false;
	private static Boolean isLogin=false;
	private static String userName="";
	private static String userPwd="";
	public static final String FLAG_REGISTER="FLAG_REGISTER";
	public static final String USER="USER";
	public static final int MAIN_EDIT=1;
	public static final int MAIN_DEL=2;
	public static final int REQUEST_EDIT=100;
	public static final int REQUEST_ADD=200;
	public static Boolean getIsExit() 
	{  
		return isExit;  
	}
	
	public static void setIsExit(Boolean isExit) 
	{  
	    UserHelper.isExit = isExit;  
	}
	
	public static String getUserName() 
	{  
		return userName;  
	}
	
	public static void setUserName(String userName)
	{
		UserHelper.userName=userName;
	}
	
	
	public static String getUserPwd() 
	{  
		return userPwd;  
	}
	
	public static void setUserPwd(String userPwd)
	{
		UserHelper.userPwd=userPwd;
	}
	
	
	public static Boolean getIsLogin() 
	{  
		return isLogin;  
	}
	
	public static void setIsLogin()
	{
		UserHelper.isLogin=true;
	}
}
