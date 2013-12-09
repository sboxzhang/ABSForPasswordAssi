package com.sbox.absforpwdassi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager 
{
	private DBHelper helper;
	public SQLiteDatabase db;
	
	public DBManager(Context context) 
	{
		helper = new DBHelper(context);
		//��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0, mFactory);
		//����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
		db = helper.getWritableDatabase();
	}
	
	/**
	 * add PwdInfo
	 * @param PwdInfos
	 */
	public void addPwdInfo(List<PwdInfo> pwdinfos) 
	{
        db.beginTransaction();	//��ʼ����
        try 
        {
        	for (PwdInfo person : pwdinfos) 
        	{
        		db.execSQL("INSERT INTO PwdInfo VALUES(null, ?, ?, ?)", new Object[]{person.account, person.pwd, person.info});
        	}
        	db.setTransactionSuccessful();	//��������ɹ����
        } 
        finally 
        {
        	db.endTransaction();	//��������
        }
	}
	
	/**
	 * update pwdinfo's account
	 * @param pwdinfo
	 */
	public void updatePwdInfoAccount(PwdInfo person) 
	{
		ContentValues cv = new ContentValues();
		cv.put("account", person.account);
		db.update("PwdInfo", cv, "account = ?", new String[]{person.account});
		//db.execSQL("");
	}
	
	/**
	 * update pwdinfo's pwd
	 * @param pwdinfo
	 */
	public void updatePwdInfoPwd(PwdInfo person) 
	{
		ContentValues cv = new ContentValues();
		cv.put("pwd", person.pwd);
		db.update("PwdInfo", cv, "pwd = ?", new String[]{person.pwd});
		//db.execSQL("");
	}
	
	/**
	 * delete old person
	 * @param person
	 */
	public void deletePwdInfoByID(PwdInfo person) 
	{
		db.delete("PwdInfo", "_ID >= ?", new String[]{String.valueOf(person._id)});
	}
	
	/**
	 * query all pwdinfo, return list
	 * @return List<PwdInfo>
	 */
	public List<PwdInfo> queryPwdInfo() 
	{
		ArrayList<PwdInfo> persons = new ArrayList<PwdInfo>();
		Cursor c = queryTheCursor();
        while (c.moveToNext()) 
        {
        	PwdInfo person = new PwdInfo();
        	person._id = c.getInt(c.getColumnIndex("_id"));
        	person.account = c.getString(c.getColumnIndex("account"));
        	person.pwd = c.getString(c.getColumnIndex("pwd"));
        	person.info = c.getString(c.getColumnIndex("info"));
        	persons.add(person);
        }
        c.close();
        return persons;
	}
	
	/**
	 * query all pwdinfo, return cursor
	 * @return	Cursor
	 */
	public Cursor queryTheCursor() 
	{
        Cursor c = db.rawQuery("SELECT * FROM PwdInfo", null);
        return c;
	}
	
	public Cursor GetUserInfo(String Type) 
	{
        Cursor c = db.rawQuery("SELECT ACCOUNT,PWD FROM UserInfo WHERE INFO='" + Type + "'", null);
        return c;
	}
	
	public Boolean Register(String account,String pwd)
	{
		db.beginTransaction();	//��ʼ����
        try 
        {
        	db.execSQL("INSERT INTO USERINFO VALUES(null,'" + account + "','" + pwd + "','USER')");
        	db.execSQL("UPDATE USERINFO SET ACCOUNT='Y' WHERE INFO='FLAG_REGISTER'");
        	db.setTransactionSuccessful();	//��������ɹ����
        } 
        finally 
        {
        	db.endTransaction();	//��������
        }
		
		return CheckIsRegister();
	}
	
	public Boolean CheckIsRegister() 
	{
		Boolean flag=false;
		Cursor c = GetUserInfo(UserHelper.FLAG_REGISTER);
        while (c.moveToNext()) 
        {
        	if("Y".equals(c.getString(c.getColumnIndex("account"))))
        	{
        		flag = true;
        		break;
        	}
        	else
        	{
        		flag = false;
        		break;
        	}
        }
        c.close();
        return flag;
	}
	
	public Boolean CheckPassword(String account,String pwd)
	{
		Boolean flag=false;
		Cursor c = GetUserInfo(UserHelper.USER);
        while (c.moveToNext()) 
        {
        	if(account.equals(c.getString(c.getColumnIndex("account"))) && 
        			pwd.equals(c.getString(c.getColumnIndex("pwd"))))
        	{
	        	flag= true;
	        	break;
        	}
        }
        c.close();
        return flag;
	}
	/**
	 * close database
	 */
	public void closeDB() 
	{
		db.close();
	}
}
