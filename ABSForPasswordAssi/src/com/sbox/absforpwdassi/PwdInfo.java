package com.sbox.absforpwdassi;

public class PwdInfo 
{
	public int _id;
	public String account;
	public String pwd;
	public String info;
	
	public PwdInfo() 
	{
		
	}
	
	public PwdInfo(String account, String pwd, String info) 
	{
		this.account = account;
		this.pwd = pwd;
		this.info = info;
	}
}

