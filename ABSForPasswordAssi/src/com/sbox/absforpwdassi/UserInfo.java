package com.sbox.absforpwdassi;

public class UserInfo 
{
	public int _id;
	public String account;
	public String pwd;
	public String info;
	
	public UserInfo() 
	{
	}
	
	public UserInfo(String account, String pwd, String info) 
	{
		this.account = account;
		this.pwd = pwd;
		this.info = info;
	}
}
